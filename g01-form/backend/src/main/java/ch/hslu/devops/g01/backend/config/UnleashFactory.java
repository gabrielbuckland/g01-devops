package ch.hslu.devops.g01.backend.config;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class UnleashFactory {

    @Singleton
    UnleashConfig unleashConfig(UnleashConfigurationProperties props) {
        return UnleashConfig.builder()
                .appName(props.getAppName())
                .environment(props.getEnvironment())
                .instanceId(props.getInstanceId())
                .unleashAPI(props.getUrl())
                .fetchTogglesInterval(props.getFetchInterval())
                .disableMetrics()
                .build();
    }

    @Singleton
    Unleash unleash(UnleashConfig config) {
        return new DefaultUnleash(config);
    }
}
