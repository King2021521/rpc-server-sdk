package com.zxm.rpc.registry;

import com.zxm.rpc.config.RegistryConfig;

import java.net.UnknownHostException;

/**
 * @Author
 * @Description
 * @Date Create in 下午 4:48 2019/1/22 0022
 */
public class RegistryHandler {
    private RegistryConfig registryConfig;

    private RegistryConnector registryConnector;

    public RegistryHandler(RegistryConfig registryConfig){
        this.registryConfig = registryConfig;
        this.registryConnector = new RegistryConnector(registryConfig);
    }

    public boolean registry() throws UnknownHostException{
        return registryConnector.registry();
    }
}
