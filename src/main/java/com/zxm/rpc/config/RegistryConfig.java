package com.zxm.rpc.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 4:33 2019/1/22 0022
 */
public class RegistryConfig {
    /**
     * 注册中心host
     */
    private String registryHost;

    /**
     * 注册中心端口
     */
    private Integer registryPort;

    /**
     * 应用名称
     */
    private String serverName;

    /**
     * 服务提供方端口
     */
    private Integer port;

    /**
     * 服务提供接口集合
     */
    private Set<ProviderConfig> providerConfigs;

    public String getRegistryHost() {
        return registryHost;
    }

    public void setRegistryHost(String registryHost) {
        this.registryHost = registryHost;
    }

    public Integer getRegistryPort() {
        return registryPort;
    }

    public void setRegistryPort(Integer registryPort) {
        this.registryPort = registryPort;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Set<ProviderConfig> getProviderConfigs() {
        return providerConfigs;
    }

    public void setProviderConfigs(Set<ProviderConfig> providerConfigs) {
        this.providerConfigs = providerConfigs;
    }

    public String getLocalHostAddrss(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
