package com.zxm.rpc.registry;

import com.alibaba.fastjson.JSONObject;
import com.zxm.rpc.ServerNotFoundException;
import com.zxm.rpc.config.ProviderConfig;
import com.zxm.rpc.config.RegistryConfig;
import redis.clients.jedis.Jedis;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 4:51 2019/1/22 0022
 */
public class RegistryConnector {
    private static final String REGISTRY_PREFIX = "rpc-registry:";

    private RegistryConfig registryConfig;

    private Jedis jedis;

    public RegistryConnector(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
        jedis = new Jedis(registryConfig.getRegistryHost(), registryConfig.getRegistryPort());
    }

    public boolean registry(){
        String key = joinKey(registryConfig.getServerName(), registryConfig.getLocalHostAddrss(), registryConfig.getPort());
        String result = jedis.set(key, buildRegistryValue(registryConfig.getProviderConfigs()));

        if (null != result) {
            return true;
        }
        return false;
    }

    private String joinKey(String serverName, String host, Integer port) {
        return REGISTRY_PREFIX + serverName + ":" + host + ":" + port;
    }

    private String buildRegistryValue(Set<ProviderConfig> providerConfigs) {
        JSONObject value = new JSONObject();
        providerConfigs.forEach(providerConfig -> value.put(providerConfig.getApiName(), providerConfig.getImplementsClass()));
        return value.toJSONString();
    }

    /**
     * 根据serverName获取host
     *
     * @param serverName
     * @return 127.0.0.1:8080
     */
    public String getUrls(String serverName) {
        Collection<String> keys = jedis.keys(REGISTRY_PREFIX + serverName + ":");

        if (keys.isEmpty()) {
            throw new RuntimeException("service provider is not found!");
        }

        List<String> keyList = new ArrayList<>(keys);
        Random random = new Random();
        String key = keyList.get(random.nextInt(keyList.size()));

        return key.replace(REGISTRY_PREFIX + serverName + ":","");
    }

    /**
     * 根据apiName获取实现类
     * @param apiName
     * @return
     * @throws UnknownHostException
     */
    public String getImplementClass(String apiName){
        String key = joinKey(registryConfig.getServerName(), registryConfig.getLocalHostAddrss(), registryConfig.getPort());
        String result = jedis.get(key);
        return JSONObject.parseObject(result).get(apiName).toString();
    }
}
