package com.zxm.rpc.registry;

import com.zxm.rpc.config.RegistryConfig;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 4:48 2019/1/22 0022
 */
public class RegistryHandler {
    private RegistryConfig registryConfig;

    private final RegistryClient registryClient;

    public RegistryHandler(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
        this.registryClient = new RegistryClient(this.registryConfig);
    }

    public boolean registry() {
        return registryClient.registry();
    }

    public String getUrls(String serverName) {
        return registryClient.getUrls(serverName);
    }

    public String getImplementClass(String apiName) {
        return registryClient.getImplementClass(apiName);
    }
}
