package com.zxm.rpc.registry;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @Author
 * @Description
 * @Date Create in 上午 10:29 2019/1/31 0031
 */
public class JedisTemplate {
    protected static final String REGISTRY_PREFIX = "rpc-registry:";
    protected Jedis jedis;

    public JedisTemplate(String host, int port) {
        this.jedis = JedisClientFactory.newInstance(host,port);
    }

    public String getValue(String key){
        return jedis.get(key);
    }

    public String setValue(String key, String value){
        return jedis.set(key, value);
    }

    public void delValue(String key){
        jedis.del(key);
    }

    /**
     * 根据serverName获取host
     *
     * @param serverName
     * @return 127.0.0.1:8080
     */
    public String getUrls(String serverName) {
        Collection<String> keys = jedis.keys(REGISTRY_PREFIX + serverName + ":*");

        if (keys.isEmpty()) {
            throw new RuntimeException("service provider is not found!");
        }

        List<String> keyList = new ArrayList<>(keys);
        Random random = new Random();
        String key = keyList.get(random.nextInt(keyList.size()));

        return key.replace(REGISTRY_PREFIX + serverName + ":","");
    }

    public String joinKey(String serverName, String host, Integer port) {
        return new StringBuilder().append(REGISTRY_PREFIX).append(serverName).append(":").append(host).append(":").append(port).toString();
    }
}
