package com.zxm.rpc.registry;

import com.alibaba.fastjson.JSONObject;
import com.zxm.rpc.config.ProviderConfig;
import com.zxm.rpc.config.RegistryConfig;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 4:51 2019/1/22 0022
 */
public class RegistryClient {
    private RegistryConfig registryConfig;

    private JedisTemplate template;

    public RegistryClient(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
        template = JedisTemplateFactory.newInstance(registryConfig.getRegistryHost(), registryConfig.getRegistryPort());
    }

    public boolean registry(){
        String key = template.joinKey(registryConfig.getServerName(), registryConfig.getLocalHostAddrss(), registryConfig.getPort());
        String result = template.setValue(key, buildRegistryValue(registryConfig.getProviderConfigs()));

        if (null != result) {
            return true;
        }
        return false;
    }

    private String buildRegistryValue(Set<ProviderConfig> providerConfigs) {
        JSONObject value = new JSONObject();
        providerConfigs.forEach(providerConfig -> value.put(providerConfig.getApiName(), providerConfig.getImplementsClass()));
        return value.toJSONString();
    }

    /**
     * 根据apiName获取实现类
     * @param apiName
     * @return
     * @throws UnknownHostException
     */
    public String getImplementClass(String apiName){
        String key = template.joinKey(registryConfig.getServerName(), registryConfig.getLocalHostAddrss(), registryConfig.getPort());
        String result = template.getValue(key);
        return JSONObject.parseObject(result).get(apiName).toString();
    }
}
