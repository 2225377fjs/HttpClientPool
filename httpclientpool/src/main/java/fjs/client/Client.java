package fjs.client;

import fjs.clientpool.ClientPool;
import fjs.http.Request;
import fjs.http.RequestFuture;
import fjs.lifecycle.Lifecycle;

/**
 * Created by fjs on 14-6-20.
 */
public interface Client extends Lifecycle{
    public static enum ClientStatus {
        Startting, Ready, Working, Stopped
    }

    /**
     * 获取所属的pool
     * @return
     */
    public ClientPool getClientPool();

    /**
     * 获取远程地址
     * @return
     */
    public String getRemoteHost();


    /**
     * 处理请求
     * @param request
     * @return
     */
    public void request(Request request);

    /**
     * 取消正在运行的request
     */
    public void cancel();

    /**
     * 设置正在运行的request
     * @param request
     */
    public void setRequest(Request request);

    /**
     * 返回正在运行的request
     * @return
     */
    public Request getRequest();

    /**
     * 用于返回当前client的状态
     * @return
     */
    public ClientStatus getStatus();


    /**
     *  用于设置当前client的状态
     * @param status
     */
    public void setStatus(ClientStatus status);


    /**
     * 当当前client可以用的时候调用的方法，将这个client加入到pool啥的
     */
    public void ready();

}
