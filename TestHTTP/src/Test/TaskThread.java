package Test;

import java.io.*;
import java.net.Socket;

/**
 * Created by 63263 on 2017/3/3.
 */
public class TaskThread extends Thread{
    private Socket s;
    BufferedReader reader;
        PrintStream writer;
    String uri = null;
    public TaskThread(Socket s) {
        this.s = s;
    }
    @Override
    public void run(){
        try {
            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String firstLineOfRequest;
            String response;
            OutputStream os;
            os = s.getOutputStream();
            writer = new PrintStream(s.getOutputStream());//建立流

            firstLineOfRequest = reader.readLine();//读取头文件信息
            System.out.println(firstLineOfRequest);
            while(true){
                if((response = reader.readLine()).equals(""))break;
                System.out.println(response);
            }//将客户端发送的信息打印到控制台

            uri = firstLineOfRequest.split(" ")[1];
            writer.println("HTTP/1.1 200 OK");//返回应答消息，并结束应答
            writer.flush();

            //处理客户端信息
            if(uri.endsWith(".html")){
                writer.println("Content-Type:text/html");
            }else if(uri.endsWith(".jpg")){
                writer.println("Content-Type:image/jpeg");
            }else if(uri.endsWith(".wma")){
                writer.println("Content-Type:audio/x-ms-wma");
            }else if(uri.endsWith(".java")){
                writer.println("Content-Type:java/*");
            }else if(uri.endsWith(".mp3")){
                writer.println("Content-Type:audio/mp3");
            }else if(uri.endsWith(".mp4")){
                writer.println("Content-Type:video/mpeg4");
            }else{
                writer.println("Content-Tpye:application/octet-stream");
            }
            writer.flush();

            try{
            InputStream inputStream = new FileInputStream("D:/TOEFL"+uri);
            //发送响应头
                writer.println("Content-Length:"+inputStream.available());//返回内容字节数
            writer.println();//根据HTTP协议，空行将结束头信息
            writer.flush();
                //发送相应数据
                byte[] b = new byte[1024];
                while (inputStream.read(b) != -1) {
                    os.write(b);
                }
                os.flush();
            os.close();
            }catch (Exception e){

                //当资源不存在的情况下，发送纯文本响应信息
                //响应头
                writer.println("HTTP/1.1 404 Not Found");
                writer.println("Content-Type:text/plain");
                writer.println("Content-Length:7");
                writer.println();
                //响应体
                writer.print("404 Not Found");
                writer.flush();
            }

            writer.close();
            s.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            System.out.println("File Not Found!");
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }
}

