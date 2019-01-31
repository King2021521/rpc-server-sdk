#### 简介
一款集服务发现、rpc通信于一体的框架，通信层基于netty实现，底层原理基于反射、动态代理实现。
纯基于Javaconfig方式配置，无xml。配置中心基于redis。  
#### 特性
* 传输方式：TCP长连接，NIO异步通信
* 序列化：json
* 适用场景：服务间高性能、低延迟调用
#### 注意事项
* 注册中心目前采用redis实现，注册中心可用性暂不保证
#### 待完善模块
* 心跳检测
* 负载均衡
* 链路监控
* 熔断  
  
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
    public IUserServiceApi referenceConfig() {
        ReferenceConfig<IUserServiceApi> referenceConfig = ReferenceConfig
                .instance()
                .apiName("com.zxm.rpc.api.IUserServiceApi")
                .providerName("spring-boot-demo")
                .registryHost("10.13.1.210")
                .registryPort(6379)
                .timeout(5000)
                .build();
        return referenceConfig.register();
    }
}
```
