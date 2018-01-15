package Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 63263 on 2017/3/3.
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        try{
            ss = new ServerSocket(8888);//建立套接字链接

        }catch (IOException e){
            e.printStackTrace();
        }
        while(true){
            try{
                Socket s = ss.accept();//链接成功
                TaskThread t = new TaskThread(s);
                t.start();//新建线程处理客户端响应
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
