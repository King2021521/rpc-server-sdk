package com.zxm.rpc.proxy;

import com.zxm.rpc.config.RegistryConfig;
import com.zxm.rpc.registry.RegistryHandler;
import com.zxm.rpc.remote.NettySocketServer;
import com.zxm.rpc.utils.RpcProtocol;

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
        init(registryConfig);
    }

    private void init(RegistryConfig registryConfig){
        Thread thread = null;
        try {
            thread = new Thread(new NettySocketServer(registryConfig.getPort(),this));
        } catch (Exception e) {
        }
        thread.start();
    }

    public Object invoke(RpcProtocol rpcProtocol){
        String implementClassName = registryHandler.getImplementClass(rpcProtocol.getInterfaceName());
        Class implementClass ;
        Class clazz;
        try {
            implementClass = Class.forName(implementClassName);
            clazz = Class.forName(rpcProtocol.getInterfaceName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("api "+rpcProtocol.getInterfaceName()+" is not found");
        }

        Method method;
        try {
            method = clazz.getMethod(rpcProtocol.getMethodName(),rpcProtocol.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("method "+rpcProtocol.getMethodName()+" is not found");
        }

        try {
            return method.invoke(implementClass.newInstance(),rpcProtocol.getArgs());
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }
}
