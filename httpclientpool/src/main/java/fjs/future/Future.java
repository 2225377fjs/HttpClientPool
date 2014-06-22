package fjs.future;

/**
 * Created by fjs on 14-6-20.
 * 提交请求之后返回的，用于注册回调，获取执行结果啥的
 */
public interface Future {
    /**
     * 定义的事件类型，Complete表示处理完成，Exception就是处理的时候出现了异常
     */
    static enum EventType{
        Complete, Exception
    }

    /**
     * 当前Future的类型
     */
    static enum FutureStatus {
        Pendding, Running, Canceled, Error, Timeout, Success
    }
    static public interface Listener {
        /**
         * 当处理完成了之后要调用的方法
         * @param arg : 处理完之后的结果
         */
        public void complete(Object arg);

        /**
         * 当处理的时候有问题发生调用的方法
         * @param t
         */
        public void exception(Throwable t);
    }

    /**
     * 获取当前future的状态
     * @return
     */
    public FutureStatus getStatus();

    /**
     * 设置当前future的状态
     * @param status
     */
    public void setStatus(FutureStatus status);


    /**
     * 取消当前任务的执行
     */
    public void cancel();



    /**
     * 添加回调，用于处理结果或者异常啥的
     * @param listener
     */
    public void addListener(Listener listener);

    /**
     * 当有进展的时候将会调用这个方法
     * @param type  : 类型，有可能是处理完了，也有可能是有异常
     * @param arg  : 传递的参数
     */
    public void fireEvent(EventType type, Object arg);


    /**
     * 当处理成功之后会调用的方法
     * @param arg
     */
    public void success(Object arg);

    /**
     * 当超时的时候会调用的方法
     */
    public void timeOut();


    /**
     * 当执行过程中出现异常调用的方法
     * @param t
     */
    public void exception(Throwable t);

}
