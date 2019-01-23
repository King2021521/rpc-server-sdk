package com.zxm.rpc.registry;

import com.zxm.rpc.ServerNotFoundException;
import com.zxm.rpc.config.RegistryConfig;
import redis.clients.jedis.Jedis;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @Author zxm
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

    public Collection<String> getAllUrls() {
        return jedis.keys("*");
    }

    public String getUrl(String host, Integer port) {
        String url = joinKey(host, port);
        boolean result = jedis.exists(url);
        if (result) {
            return url;
        }

        throw new ServerNotFoundException("server is not found");
    }
}
