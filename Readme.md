基于netty开发的支持并发的http客户端。。。
后台将会维护一个tcp的长连接池。。。。


闲来无事，将以前自己写的一个库放出来吧。。
有的时候会有这样子的需求：
（1）服务器A通过HTTP协议来访问服务器B
（2）服务器A可能会并发的像B发送很多HTTP请求

类似于上述的需求，可能并不常见。。。因为在业务中确实遇到了这样子的场景，所以就自己动手开发了一个库。。。

实现原理：
（1）底层IO通过netty搞
（2）维护一个tcp的长连接池，这样子就不用每次发送请求还要建立一个tcp连接了。。。

下面直接来看怎么用吧：
（1）最常规的用法，向www.baidu.com发送100次get请求：
ClientPool pool = new NettyClientPool(2, "www.baidu.com");
pool.start();
for (int i = 0; i < 100; i++) {
    Request re = new Request("/", Request.RequestMethod.GET);
    pool.request(re).addListener(new Future.Listener() {
        @Override
        public void complete(Object arg) {
            Response res = (Response)arg;
            System.out.println(res.getBody().toString(Charset.forName("utf-8")));
        }

        @Override
        public void exception(Throwable t) {

        }
    });
}

这里可以看到，其实这里是异步的提交的，所以需要注册一些回调。。。

（2）使用同步的方式发送请求，这里就弄成Post请求吧：
        ClientPool pool = new NettyClientPool(2, "www.baidu.com");
        pool.start();
        Request re = new Request("/", Request.RequestMethod.POST);
        re.getBody().writeBytes("aaaa".getBytes(Charset.forName("utf-8")));
        try {
            Response response = pool.requestWithTimeOut(re, 2000).sync();
            System.out.println(response.getBody().toString(Charset.forName("utf-8")));
        } catch (Throwable throwable) {
            System.out.println(throwable);
        }
        pool.stop();

这里可以看到，提交请求的时候还带有超时的额，也就是2000毫秒都还没有搞完，那么就不搞了。。

