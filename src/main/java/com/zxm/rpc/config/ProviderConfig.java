package com.zxm.rpc.config;

/**
 * @Author zxm
 * @Description
 * @Date Create in 上午 10:47 2019/1/23 0023
 */
public class ProviderConfig<T>{
    /**
     * 接口全称
     */
    private String apiName;

    /**
     * 接口实现类
     */
    private Class<T> implementsClass;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Class<T> getImplementsClass() {
        return implementsClass;
    }

    public void setImplementsClass(Class<T> implementsClass) {
        this.implementsClass = implementsClass;
    }
}
