package com.zxm.rpc.proxy;

import com.zxm.rpc.config.ReferenceConfig;
import com.zxm.rpc.remote.RpcProtocol;
import com.zxm.rpc.remote.RpcResult;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 3:56 2019/1/23 0023
 */
public class ProxyRegisterFactory {
    private ReferenceConfig referenceConfig;

    public ProxyRegisterFactory(ReferenceConfig referenceConfig) {
        this.referenceConfig = referenceConfig;
    }

    public <T> T register(String interfaceName) throws ClassNotFoundException {
        Class clazz = Class.forName(interfaceName);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (Object proxy, Method method, Object[] args) -> {

            RpcProtocol protocol = new RpcProtocol();
            protocol.setId(UUID.randomUUID().toString().replace("-", ""));
            protocol.setInterfaceName(clazz.getName());
            protocol.setMethodName(method.getName());
            protocol.setArgs(args);
            referenceConfig.getSocketChannel().writeAndFlush(protocol);

            long pollTime = System.currentTimeMillis();
            while (true) {
                if ((System.currentTimeMillis() - pollTime) > referenceConfig.getTimeout()) {
                    throw new RuntimeException("rpc request remote timeout");
                }

                RpcResult rpcResult = referenceConfig.getRpcResult(protocol.getId());
                if (rpcResult != null) {
                    referenceConfig.removeRpcResult(protocol.getId());
                    return rpcResult.getValue();
                }
            }
        });
    }
}
