package com.zxm.rpc;
import com.zxm.rpc.config.ReferenceConfig;

import java.lang.reflect.Proxy;
import java.net.InetAddress;

/**
 * @Author
 * @Description
 * @Date Create in 上午 9:31 2018/12/21 0021
 */
public class Runner {
    public static void main(String[] args) throws Exception{
        /*Runner m = new Runner();
        IPayInterface iPayInterface = (IPayInterface) m.rpc(IPayInterface.class);
        boolean result = iPayInterface.pay(100);
        System.out.println(result);*/

        /*ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setReference("com.zxm.rpc.IPayInterface")
        .init();*/
    }

    public Object rpc(Class clazz){
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> method.invoke(new IPayImpl(),args));
    }
}
