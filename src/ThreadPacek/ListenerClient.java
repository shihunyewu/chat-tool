package ThreadPacek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import FormUI.ServerUI;

/**
 * 服务器监听消息
 * @author bupt632
 *
 */
public class ListenerClient extends Thread {
	
	//每个监听客户端信息的 线程类，都有自己的 reader， wirter，client，要接收 ServerUI
    BufferedReader reader;
    PrintWriter writer;
    ServerUI ui;
    Socket client;
    public ListenerClient(ServerUI ui, Socket client) {
        this.ui = ui;
        this.client=client;
        this.start();
    }
    //为每一个客户端创建线程等待接收信息，然后把信息广播出去
    public void run() {
        String msg = "";
        while (true) {
            try {
                reader = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream(), true);
                msg = reader.readLine();
                
                /*
                 * 得到信息msg，之后，调用群发函数
                 */
                sendMsg(msg);
                
            } catch (IOException e) {
                println(e.toString());
                // e.printStackTrace();
                break;
            }
            if (msg != null && msg.trim() != "") {
                println(">>" + msg);
            }
        }
    }
    //把信息广播到所有用户
    
    /*
     * 作用：将信息广播到所有用户
     * 作者：苏光远
     * 想法：就是在这里进行处理，分析 信息的头部（头部含有要发给哪个用户或者是群聊这样的信息）
     * 这样就实现了 单聊 和 群发
     */
    public synchronized void sendMsg(String msg) {
        try {
            for (int i = 0; i < ui.clients.size(); i++) {
                Socket client = ui.clients.get(i);
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.println(msg);
            }

        } catch (Exception e) {
            println(e.toString());
        }
    }

    
    public void println(String s) {
        if (s != null) {
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");
            System.out.println(s + "\n");
        }
    }
}
