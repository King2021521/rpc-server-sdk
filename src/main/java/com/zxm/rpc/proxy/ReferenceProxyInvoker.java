package com.zxm.rpc.proxy;

import com.zxm.rpc.config.ReferenceConfig;
import com.zxm.rpc.registry.jedis.JedisTemplate;
import com.zxm.rpc.registry.jedis.JedisTemplateFactory;
import com.zxm.rpc.remote.NettyClientBootstrap;
import com.zxm.rpc.remote.NettySocketClientFactory;
import com.zxm.rpc.utils.RpcProtocol;
import com.zxm.rpc.utils.RpcResult;
import com.zxm.rpc.utils.RpcSerializer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 3:56 2019/1/23 0023
 */
public class ReferenceProxyInvoker {
    private ReferenceConfig referenceConfig;

    private JedisTemplate template;

    private NettyClientBootstrap nettyClientBootstrap;

    public ReferenceProxyInvoker(ReferenceConfig referenceConfig) {
        this.referenceConfig = referenceConfig;
        template = JedisTemplateFactory.newInstance(referenceConfig.getRegistryHost(), referenceConfig.getRegistryPort());
        initNettyBootstrap();
    }

    private void initNettyBootstrap() {
        String url = template.getUrls(referenceConfig.getProviderName());
        String[] urlArgs = url.split(":");
        this.nettyClientBootstrap = NettySocketClientFactory.newInstance(Integer.valueOf(urlArgs[1]), urlArgs[0]);
    }

    public <T> T register() throws ClassNotFoundException {
        Class clazz = Class.forName(referenceConfig.getApiName());
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (Object proxy, Method method, Object[] args) -> invoke(clazz, method, args));
    }

    private Object invoke(Class clazz, Method method, Object[] args) {
        RpcProtocol protocol = rpcProtocolWrapper(clazz, method, args);
        this.nettyClientBootstrap.getSocketChannel().writeAndFlush(RpcSerializer.serialize(protocol));
        return poll(protocol);
    }

    private Object poll(RpcProtocol protocol) {
        long pollTime = System.currentTimeMillis();
        while (true) {
            if ((System.currentTimeMillis() - pollTime) > referenceConfig.getTimeout()) {
                throw new RuntimeException("rpc request remote timeout");
            }

            RpcResult rpcResult = this.nettyClientBootstrap.getRpcResult(protocol.getId());
            if (rpcResult != null) {
                this.nettyClientBootstrap.removeRpcResult(protocol.getId());
                return rpcResult.getValue();
            }
        }
    }

    private RpcProtocol rpcProtocolWrapper(Class clazz, Method method, Object[] args) {
        RpcProtocol protocol = new RpcProtocol();
        protocol.setId(UUID.randomUUID().toString().replace("-", ""));
        protocol.setInterfaceName(clazz.getName());
        protocol.setMethodName(method.getName());
        protocol.setParameterTypes(method.getParameterTypes());
        protocol.setArgs(args);
        return protocol;
    }
}
