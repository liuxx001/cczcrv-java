package com.bird.core.trace.configuration;

import com.bird.core.trace.TraceableAspect;
import com.bird.core.trace.dispatch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuxx
 * @since 2020/10/9
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "bird.trace.enable", havingValue = "true", matchIfMissing = true)
public class TraceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({ITraceLogStore.class, ITraceLogDispatcher.class})
    public ITraceLogStore traceLogStore() {
        return logs -> log.warn("未注入ITraceLogStore实例，丢弃跟踪日志信息");
    }

    @Bean
    @ConditionalOnMissingBean(ITraceLogDispatcher.class)
    @ConfigurationProperties(prefix = "bird.trace.dispatcher")
    public DefaultTraceDispatcherProperties defaultTraceDispatcherProperties() {
        return new DefaultTraceDispatcherProperties();
    }

    @Bean
    @ConditionalOnMissingBean(ITraceLogDispatcher.class)
    public ITraceLogDispatcher traceLogDispatcher(ITraceLogStore traceLogStore, DefaultTraceDispatcherProperties defaultTraceDispatcherProperties) {
        DefaultTraceLogDispatcher defaultTraceLogDispatcher = new DefaultTraceLogDispatcher(traceLogStore, defaultTraceDispatcherProperties);
        defaultTraceLogDispatcher.init();
        return defaultTraceLogDispatcher;
    }

    @Bean
    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Pointcut")
    public TraceableAspect traceableAspect() {
        return new TraceableAspect();
    }
}