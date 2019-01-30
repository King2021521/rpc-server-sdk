#### 简介
一款集服务发现、rpc通信于一体的框架，通信层基于netty实现，底层原理基于反射、动态代理实现。
纯基于Javaconfig方式配置，无xml。配置中心基于redis。  
关键词: netty、动态代理、javaconfig、redis  
#### 服务提供端配置：
```
 @Configuration  
 public class RpcConfig {  
     @Bean
     public RegistryConfig registryConfig(){
         RegistryConfig registryConfig = new RegistryConfig();
         registryConfig.setRegistryHost("10.13.1.210");
         registryConfig.setRegistryPort(6379);
         registryConfig.setPort(3333);
         registryConfig.setServerName("spring-boot-demo");
         registryConfig.setProviderConfigs(providerConfig());
         return registryConfig;
     }
 
     private Set<ProviderConfig> providerConfig(){
         Set<ProviderConfig> providerConfigs = new HashSet<>();
 
         ProviderConfig<IUserServiceApiImpl> providerConfig = new ProviderConfig<>();
         providerConfig.setApiName("com.zxm.rpc.api.IUserServiceApi");
         providerConfig.setImplementsClass(IUserServiceApiImpl.class);
 
         providerConfigs.add(providerConfig);
         return providerConfigs;
     }
 
     @Bean
     public RegistryHandler registryHandler() throws UnknownHostException{
         RegistryHandler registryHandler = new RegistryHandler(registryConfig());
         registryHandler.registry();
         return registryHandler;
     }
 
     @Bean
     public ProviderProxyInvoker providerProxyInvoker()throws UnknownHostException{
         return new ProviderProxyInvoker(registryConfig(),registryHandler());
     }
 }  
``` 
#### 服务消费端配置：
```
@Configuration
public class RefConfig {

    @Bean
    public ReferenceConfig referenceConfig(){
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setRemoteHost("10.13.1.210");
        referenceConfig.setRemotePort(3333);
        referenceConfig.setTimeout(5000);
        return referenceConfig;
    }

    @Bean
    public ReferenceProxyRegister proxyRegisterFactory(){
        ReferenceProxyRegister referenceProxyRegister = new ReferenceProxyRegister(referenceConfig());
        return referenceProxyRegister;
    }

    @Bean
    public IUserServiceApi iUserServiceApi() throws Exception{
        return proxyRegisterFactory().register("com.zxm.rpc.api.IUserServiceApi");
    }
}
```
