package com.kimxavi87.spring.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;

@Configuration
public class LeaderInitConfiguration {

    @Value("${config.leader.zookeeper.host}")
    private String zookeeperHost;

    @Value("${config.leader.zookeeper.path}")
    private String gtmSchedulerZookeeperPath;

    @Value("${config.leader.zookeeper.role:follower}")
    private String gtmSchedulerZookeeperRole;

    @Bean
    public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
        return new CuratorFrameworkFactoryBean(zookeeperHost);
    }

    @Bean
    public LeaderInitiatorFactoryBean leaderInitiatorFactoryBean(CuratorFramework client) {
        return new LeaderInitiatorFactoryBean()
                .setClient(client)
                .setPath(gtmSchedulerZookeeperPath)
                .setRole(gtmSchedulerZookeeperRole);
    }
}