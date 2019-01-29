package com.zxm.rpc.utils;

import java.io.Serializable;

/**
 * @Author
 * @Description
 * @Date Create in 下午 4:08 2019/1/23 0023
 */
public class RpcProtocol implements Serializable{
    private static final long serialVersionUID = -8478844905397248186L;

    private String id;

    private String interfaceName;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
