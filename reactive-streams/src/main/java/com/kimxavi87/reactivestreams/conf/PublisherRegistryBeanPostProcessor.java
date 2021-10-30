package com.kimxavi87.reactivestreams.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
public class PublisherRegistryBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware, InitializingBean,
        DisposableBean {
    private Set<Disposable> disposableSet = new HashSet<>();
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        }
    }

    @Override
    public void destroy() throws Exception {
        for (Disposable disposable : disposableSet) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Flux) {
            System.out.println("Flux bean found");
            Flux flux = (Flux) bean;
            Disposable disposable = flux.subscribe();
            disposableSet.add(disposable);
        } else if (bean instanceof Mono) {
            System.out.println("Mono bean found");
            Mono mono = (Mono) bean;
            Disposable disposable = mono.subscribe();
            disposableSet.add(disposable);
        }

        return bean;
    }
}
