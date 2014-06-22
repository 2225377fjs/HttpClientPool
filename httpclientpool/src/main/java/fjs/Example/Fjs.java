package fjs.Example;

import fjs.clientpool.ClientPool;
import fjs.clientpool.NettyClientPool;
import fjs.future.Future;
import fjs.http.Request;
import fjs.http.Response;

import java.nio.charset.Charset;

/**
 * Created by fjs on 14-6-22.
 */
public class Fjs {
    public static void doGet() {
        final ClientPool pool = new NettyClientPool(2, "www.baidu.com");
        pool.start();
        Request request = new Request("/", Request.RequestMethod.GET);
        pool.request(request).addListener(new Future.Listener() {
            @Override
            public void complete(Object arg) {
                Response res = (Response)arg;
                System.out.println(res.getBody().toString(Charset.forName("utf-8")));
                pool.stop();
            }

            @Override
            public void exception(Throwable t) {

            }
        });
    }

    public static void doPost() {
        final ClientPool pool = new NettyClientPool(2, "www.baidu.com");
        pool.start();
        Request request = new Request("/", Request.RequestMethod.POST);
        request.getBody().writeBytes("aa".getBytes(Charset.forName("utf-8")));
        pool.request(request).addListener(new Future.Listener() {
            @Override
            public void complete(Object arg) {
                Response res = (Response)arg;
                System.out.println(res.getBody().toString(Charset.forName("utf-8")));
                pool.stop();
            }

            @Override
            public void exception(Throwable t) {

            }
        });
    }

    public static void doGetSync() throws Throwable {
        final ClientPool pool = new NettyClientPool(2, "www.baidu.com");
        pool.start();
        Request request = new Request("/", Request.RequestMethod.POST);
        request.getBody().writeBytes("aa".getBytes(Charset.forName("utf-8")));
        Response response = pool.requestWithTimeOut(request, 2000).sync();
        pool.stop();
        System.out.println(response.getBody().toString(Charset.forName("utf-8")));
    }

    public static void main(String args[]) throws Throwable {
        //Fjs.doGet();
        //Fjs.doPost();
        Fjs.doGetSync();
    }
}
