package com.zxm.rpc.proxy;

import com.zxm.rpc.config.RegistryConfig;
import com.zxm.rpc.registry.RegistryHandler;
import com.zxm.rpc.remote.NettySocketServer;
import com.zxm.rpc.utils.RpcProtocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author zxm
 * @Description zxm
 * @Date Create in 下午 2:53 2019/1/28 0028
 */
public class ProviderProxyInvoker {
    private RegistryConfig registryConfig;

    private RegistryHandler registryHandler;

    public ProviderProxyInvoker(RegistryConfig registryConfig, RegistryHandler registryHandler) {
        if (null == registryConfig) {
            throw new RuntimeException("registryConfig is not null");
        }
        this.registryConfig = registryConfig;
        this.registryHandler = registryHandler;
        init(registryConfig);
    }

    /**
     * 异步初始化nettyServer
     * @param registryConfig
     */
    private void init(RegistryConfig registryConfig) {
        try {
            new Thread(new NettySocketServer(registryConfig.getPort(), this)).start();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 反射调用实现类
     * @param rpcProtocol
     * @return
     */
    public Object invoke(RpcProtocol rpcProtocol) {
        try {
            Class implementClass = Class.forName(registryHandler.getImplementClass(rpcProtocol.getInterfaceName()));
            Method method = Class.forName(rpcProtocol.getInterfaceName())
                    .getMethod(rpcProtocol.getMethodName(), rpcProtocol.getParameterTypes());
            return method.invoke(implementClass.newInstance(), rpcProtocol.getArgs());
        } catch (ClassNotFoundException ex1) {
            throw new RuntimeException("api " + rpcProtocol.getInterfaceName() + " is not found, msg is:" + ex1.getMessage());
        } catch (NoSuchMethodException ex2) {
            throw new RuntimeException("method " + rpcProtocol.getMethodName() + " is not found");
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }
}
