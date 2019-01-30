package com.zxm.rpc.remote;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zxm
 * @Description netty 服务端调度线程池
 * @Date Create in 下午 3:26 2019/1/30 0030
 */
public class NettyServerDispatcher {
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE, new ThreadFactoryBuilder().setDaemon(true).build());
}
