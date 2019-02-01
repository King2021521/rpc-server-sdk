package com.zxm.rpc.config;

import com.zxm.rpc.proxy.ReferenceProxyInvoker;

/**
 * @Author
 * @Description
 * @Date Create in 上午 10:47 2019/1/23 0023
 */
public class ReferenceConfig<T> {
    private ReferenceProxyInvoker referenceProxyInvoker;

    //ms
    private long timeout = 5000;

    /**
     * 接口全名
     */
    private String apiName;

    /**
     * 服务提供者名称
     */
    private String providerName;

    /**
     * 注册中心host
     */
    private String registryHost;

    /**
     * 注册中心端口
     */
    private Integer registryPort = 6379;

    public long getTimeout() {
        return timeout;
    }

    public ReferenceConfig timeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public ReferenceConfig apiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getProviderName() {
        return providerName;
    }

    public ReferenceConfig providerName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public String getRegistryHost() {
        return registryHost;
    }

    public ReferenceConfig registryHost(String registryHost) {
        this.registryHost = registryHost;
        return this;
    }

    public Integer getRegistryPort() {
        return registryPort;
    }

    public ReferenceConfig registryPort(Integer registryPort) {
        this.registryPort = registryPort;
        return this;
    }

    public static ReferenceConfig instance(){
        return new ReferenceConfig();
    }

    public ReferenceConfig build(){
        this.referenceProxyInvoker = new ReferenceProxyInvoker(this);
        return this;
    }

    public T register() {
        try {
            return this.referenceProxyInvoker.register();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("register fail, msg is :" + e.getMessage());
        }
    }
}
