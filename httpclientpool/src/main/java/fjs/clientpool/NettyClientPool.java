package fjs.clientpool;

import fjs.client.Client;
import fjs.client.NettyClient;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * Created by fjs on 14-6-20.
 */
public class NettyClientPool extends AbstractClientPool {
    private NioEventLoopGroup nioEventLoopGroup;


    public NettyClientPool(int size, String remoteHost) {
        super(size, remoteHost);
        this.nioEventLoopGroup = new NioEventLoopGroup(2);
    }
    /**
     * 用于构建具体的client
     *
     * @return
     */
    @Override
    public Client newClient() {
        NettyClient client = new NettyClient(this);
        client.setGroup(this.nioEventLoopGroup);
        return client;
    }

    /**
     * 延迟执行一个task，单位微秒
     *
     * @param task
     * @param time
     */
    @Override
    public void schuild(Runnable task, int time) {
        this.nioEventLoopGroup.schedule(task, time, TimeUnit.MILLISECONDS);
    }

    public NioEventLoopGroup getNioEventLoopGroup() {
        return nioEventLoopGroup;
    }

    /**
     * 停止组件
     */
    @Override
    public void stop() {
        super.stop();
        this.nioEventLoopGroup.shutdownGracefully();
    }
}
