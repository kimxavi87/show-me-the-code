package com.kimxavi87.spring.zookeeper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LeaderInitAspect {
    private final LeaderInitiator leaderInitiator;

    @Around("@annotation(com.kimxavi87.spring.zookeeper.Leader)")
    public Object leaderAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("[DBG] check leader follower");
        if (leaderInitiator.getContext().isLeader()) {
            System.out.println("[DBG] leader " + leaderInitiator.getContext().getRole());
            return joinPoint.proceed();
        }

        System.out.println("[DBG] follower " + leaderInitiator.getContext().getRole());

        return null;
    }
}
