package com.kimxavi87.normal.zookeeper;

import com.google.common.collect.Lists;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.EnsembleTracker;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.compatibility.CuratorTestBase;
import org.apache.curator.test.compatibility.Timing2;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.server.quorum.QuorumPeer;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.apache.zookeeper.server.quorum.flexible.QuorumMaj;
import org.apache.zookeeper.server.quorum.flexible.QuorumVerifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CuratorTests extends CuratorTestBase {

    private final Timing2 timing = new Timing2();
    private TestingCluster cluster;
    private EnsembleProvider ensembleProvider;

    private static final String superUserPasswordDigest = "curator-test:zghsj3JfJqK7DbWf0RQ1BgbJH9w=";  // ran from DigestAuthenticationProvider.generateDigest(superUserPassword);
    private static final String superUserPassword = "curator-test";

    @BeforeEach
    @Override
    public void setup() throws Exception {
        super.setup();

        QuorumPeerConfig.setReconfigEnabled(true);
        System.setProperty("zookeeper.DigestAuthenticationProvider.superDigest", superUserPasswordDigest);

        CloseableUtils.closeQuietly(server);
        cluster = createAndStartCluster(3);
    }

    @AfterEach
    @Override
    public void teardown() throws Exception {
        CloseableUtils.closeQuietly(cluster);
        ensembleProvider = null;
        System.clearProperty("zookeeper.DigestAuthenticationProvider.superDigest");

        super.teardown();
    }

    @Test
    public void testBasicGetConfig() throws Exception {
        try ( CuratorFramework client = newClient()) {
            client.start();
            byte[] configData = client.getConfig().forEnsemble();
            QuorumVerifier quorumVerifier = toQuorumVerifier(configData);
            System.out.println(quorumVerifier);
            assertConfig(quorumVerifier, cluster.getInstances());
            assertEquals(EnsembleTracker.configToConnectionString(quorumVerifier), ensembleProvider.getConnectionString());
        }
    }

    @Override
    protected void createServer() throws Exception {
        // NOP
    }

    private CuratorFramework newClient() {
        return newClient(cluster.getConnectString(), true);
    }

    private CuratorFramework newClient(String connectionString) {
        return newClient(connectionString, true);
    }

    private CuratorFramework newClient(String connectionString, boolean withEnsembleProvider) {
        final AtomicReference<String> connectString = new AtomicReference<>(connectionString);
        ensembleProvider = new EnsembleProvider() {
            @Override
            public void start() throws Exception {
            }

            @Override
            public boolean updateServerListEnabled() {
                return false;
            }

            @Override
            public String getConnectionString() {
                return connectString.get();
            }

            @Override
            public void close() throws IOException {
            }

            @Override
            public void setConnectionString(String connectionString) {
                connectString.set(connectionString);
            }
        };
        return CuratorFrameworkFactory.builder()
                .ensembleProvider(ensembleProvider)
                .ensembleTracker(withEnsembleProvider)
                .sessionTimeoutMs(timing.session())
                .connectionTimeoutMs(timing.connection())
                .authorization("digest", superUserPassword.getBytes())
                .retryPolicy(new ExponentialBackoffRetry(timing.forSleepingABit().milliseconds(), 3))
                .build();
    }

    private CountDownLatch setChangeWaiter(CuratorFramework client) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if ( event.getType() == Event.EventType.NodeDataChanged ) {
                    latch.countDown();
                }
            }
        };
        client.getConfig().usingWatcher(watcher).forEnsemble();
        return latch;
    }

    private void assertConfig(QuorumVerifier config, Collection<InstanceSpec> instances) {
        for ( InstanceSpec instance : instances ) {
            QuorumPeer.QuorumServer quorumServer = config.getAllMembers().get((long)instance.getServerId());
            assertNotNull(quorumServer, String.format("Looking for %s - found %s", instance.getServerId(), config.getAllMembers()));
            assertEquals(quorumServer.clientAddr.getPort(), instance.getPort());
        }
    }

    private List<String> toReconfigSpec(Collection<InstanceSpec> instances) throws Exception {
        String localhost = new InetSocketAddress((InetAddress)null, 0).getAddress().getHostAddress();
        List<String> specs = Lists.newArrayList();
        for ( InstanceSpec instance : instances ) {
            specs.add("server." + instance.getServerId() + "=" + localhost + ":" + instance.getElectionPort() + ":" + instance.getQuorumPort() + ";" + instance.getPort());
        }
        return specs;
    }

    private static QuorumVerifier toQuorumVerifier(byte[] bytes) throws Exception {
        assertNotNull(bytes);
        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(bytes));
        return new QuorumMaj(properties);
    }
}
