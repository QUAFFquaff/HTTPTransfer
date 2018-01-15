package Test;

import java.io.*;
import java.net.Socket;

/**
 * Created by 63263 on 2017/3/3.
 */
public class TestClient {
//    Socket socket;
    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost",8888);//建立本地连接
//            URL url = new URL("http://www.neu.edu.cn/");
//            int port = url.getDefaultPort();
//            Socket socket = new Socket(url.getHost(),port);//与学校服务器建立连接

            //发送请求头
            PrintStream writer = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStream inputStream = socket.getInputStream();

            String filename = "2.mp3";//请求的文件名

            writer.println("GET /"+filename+" HTTP/1.1");
            writer.println("Host:localhost");
            writer.println("connection:keep-alive");
            writer.println();
            writer.flush();

            //发送请求体


            //接受响应状态
//            String firstLineOfResponse = reader.readLine();//HTTP/1.1 200 OK
//            String secondLineOfResponse = reader.readLine();//Content-Type:text/html
//            String threeLineOfResponse = reader.readLine();//Content-Length:
//            String fourLineOfResponse = reader.readLine();//blank line
//            System.out.println(firstLineOfResponse+"\n"+secondLineOfResponse+"\n"+threeLineOfResponse+"\n"+fourLineOfResponse);

            String response;
            while(true){
                if((response = reader.readLine()).equals(""))break;
                System.out.println(response);
            }//打印服务器发送的信息


            try {
                //读取响应数据，保存文件
                //success
                String savelocation = "D:\\传说中的d盘-----工作\\作业\\大二下学期\\网络课程实践";//目标地址

                byte[] b = new byte[1024];
            OutputStream out = new FileOutputStream(savelocation + "/" + filename);
//                RandomAccessFile file = new RandomAccessFile(savelocation+"/"+filename,"rw");
                int len = inputStream.read(b);
            while(len!=-1){
                out.write(b);
                len = inputStream.read(b);
            }//复制文件
//                int length;
//                while ((length = inputStream.read(b) ) != -1) {
//                    file.write(b,0,length);
//                    System.out.println("s");
//                }
                inputStream.close();
//                file.close();
                writer.close();
//                out.close();
                reader.close();
                socket.close();
            } catch (IOException e) {
                //响应失败（状态码404）-将响应信息打印在控制台上
                //output error message
                StringBuffer result = new StringBuffer();
                String line;
                while ((line = reader.readLine())!=null){
                    result.append(line);
                }

                reader.close();
                System.out.println(result);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
