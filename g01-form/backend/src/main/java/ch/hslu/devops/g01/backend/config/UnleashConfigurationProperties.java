package ch.hslu.devops.g01.backend.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("unleash")
public class UnleashConfigurationProperties {
    private String url;
    private String instanceId;
    private String appName;
    private String environment;
    private long fetchInterval;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public long getFetchInterval() {
        return fetchInterval;
    }

    public void setFetchInterval(long fetchInterval) {
        this.fetchInterval = fetchInterval;
    }
}
