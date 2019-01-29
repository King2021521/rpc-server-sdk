package com.zxm.rpc.proxy;

import com.alibaba.fastjson.JSONObject;
import com.zxm.rpc.config.ReferenceConfig;
import com.zxm.rpc.remote.NettySocketClientFactory;
import com.zxm.rpc.utils.RpcProtocol;
import com.zxm.rpc.utils.RpcResult;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 3:56 2019/1/23 0023
 */
public class ReferenceProxyRegister {
    private ReferenceConfig referenceConfig;

    public ReferenceProxyRegister(ReferenceConfig referenceConfig) {
        this.referenceConfig = referenceConfig;
    }

    public <T> T register(String interfaceName) throws ClassNotFoundException {
        Class clazz = Class.forName(interfaceName);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (Object proxy, Method method, Object[] args) -> {

            RpcProtocol protocol = new RpcProtocol();
            protocol.setId(UUID.randomUUID().toString().replace("-", ""));
            protocol.setInterfaceName(clazz.getName());
            protocol.setMethodName(method.getName());
            protocol.setParameterTypes(method.getParameterTypes());
            protocol.setArgs(args);

            NettySocketClientFactory.newInstance(referenceConfig.getRemotePort(),referenceConfig.getRemoteHost())
            .getSocketChannel().writeAndFlush(JSONObject.toJSONString(protocol));

            long pollTime = System.currentTimeMillis();
            while (true) {
                if ((System.currentTimeMillis() - pollTime) > referenceConfig.getTimeout()) {
                    throw new RuntimeException("rpc request remote timeout");
                }

                RpcResult rpcResult = NettySocketClientFactory.newInstance(referenceConfig.getRemotePort(),referenceConfig.getRemoteHost())
                .getRpcResult(protocol.getId());
                if (rpcResult != null) {
                    NettySocketClientFactory.newInstance(referenceConfig.getRemotePort(),referenceConfig.getRemoteHost())
                    .removeRpcResult(protocol.getId());
                    return rpcResult.getValue();
                }
            }
        });
    }
}
