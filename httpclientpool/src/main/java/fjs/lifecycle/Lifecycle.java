package fjs.lifecycle;

/**
 * Created by fjs on 14-6-20.
 * 用于管理组件的生命周期
 */
public interface Lifecycle {
    /**
     * 启动组件
     */
    public void start();

    /**
     * 停止组件
     */
    public void stop();
}
