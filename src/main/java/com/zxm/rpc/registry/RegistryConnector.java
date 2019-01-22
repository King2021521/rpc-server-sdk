package com.zxm.rpc.registry;

import com.zxm.rpc.ServerNotFoundException;
import com.zxm.rpc.config.RegistryConfig;
import redis.clients.jedis.Jedis;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @Author
 * @Description
 * @Date Create in 下午 4:51 2019/1/22 0022
 */
public class RegistryConnector {
    private RegistryConfig registryConfig;

    private Jedis jedis;

    public RegistryConnector(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
        jedis = new Jedis(registryConfig.getHost(), registryConfig.getPort());
    }

    public boolean registry() throws UnknownHostException{
        String result = jedis.set(joinKey(registryConfig.getLocalHostAddrss(), 5022), registryConfig.getServerName());
        if (null != result) {
            return true;
        }
        return false;
    }

    private String joinKey(String host, Integer port) {
        return host + ":" + port;
    }

    private Collection<String> getAllUrls() {
        return jedis.keys("*");
    }

    private String getUrl() {
        boolean result = jedis.exists(joinKey(registryConfig.getHost(), registryConfig.getPort()));
        if (result) {
            return joinKey(registryConfig.getHost(), registryConfig.getPort());
        }

        throw new ServerNotFoundException("server is not found");
    }
}
