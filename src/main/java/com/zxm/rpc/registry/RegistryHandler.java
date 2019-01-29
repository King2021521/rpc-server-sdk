package com.zxm.rpc.registry;

import com.zxm.rpc.config.RegistryConfig;
import com.zxm.rpc.remote.NettySocketServer;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 4:48 2019/1/22 0022
 */
public class RegistryHandler {
    private RegistryConfig registryConfig;

    private RegistryConnector registryConnector;

    public RegistryHandler(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
        this.registryConnector = new RegistryConnector(registryConfig);
    }

    public boolean registry() throws UnknownHostException {
        return registryConnector.registry();
    }

    public String getUrls(String serverName) {
        return registryConnector.getUrls(serverName);
    }

    public String getImplementClass(String apiName){
        return registryConnector.getImplementClass(apiName);
    }

}
