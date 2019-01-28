package com.zxm.rpc.proxy;

import com.zxm.rpc.config.RegistryConfig;
import com.zxm.rpc.registry.RegistryHandler;
import com.zxm.rpc.remote.NettySocketServerFactory;
import com.zxm.rpc.remote.RpcProtocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author
 * @Description
 * @Date Create in 下午 2:53 2019/1/28 0028
 */
public class ProviderProxyInvoker {
    private RegistryConfig registryConfig;

    private RegistryHandler registryHandler;

    public ProviderProxyInvoker(RegistryConfig registryConfig, RegistryHandler registryHandler){
        if(null==registryConfig){
            throw new RuntimeException("registryConfig is not null");
        }
        this.registryConfig = registryConfig;
        this.registryHandler = registryHandler;
    }

    private void init(RegistryConfig registryConfig){
        NettySocketServerFactory.newInstance(registryConfig.getPort(),this);
    }

    public Object invoke(RpcProtocol rpcProtocol){
        Class implementClass = registryHandler.getImplementClass(rpcProtocol.getInterfaceName());
        Class clazz;
        try {
            clazz = Class.forName(rpcProtocol.getInterfaceName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("api "+rpcProtocol.getInterfaceName()+" is not found");
        }

        Method method;
        try {
            method = clazz.getMethod(rpcProtocol.getMethodName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("method "+rpcProtocol.getMethodName()+" is not found");
        }

        try {
            return method.invoke(implementClass,rpcProtocol.getArgs());
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }
}
