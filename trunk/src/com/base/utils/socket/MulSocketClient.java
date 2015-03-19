package com.base.utils.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MulSocketClient {
    public static String main(String[] args) {
        String aceptmsg="";
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        String serverIP = "127.0.0.1"; // 服务器端IP地址
        int port = 10000; // 服务器端端口号
        String data[] = { "First", "Second", "Third" }; // 发送内容

        try {
            socket = new Socket(serverIP, port); // 建立连接
            os = socket.getOutputStream(); // 初始化流
            is = socket.getInputStream();
            byte[] b = new byte[1024];
            for (int i = 0; i < data.length; i++) {
                os.write(data[i].getBytes()); // 发送数据
                int n = is.read(b); // 接收数据
                //System.out.println("服务器反馈：" + new String(b, 0, n)); // 输出反馈数据
                aceptmsg= new String(b, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                socket.close();
            } catch (Exception e2) {
            }
        }
        return aceptmsg;
    }
}