package DirectAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import FormUI.ServerUI;
import ThreadPacek.ListenerClient;
/*这个类是服务器端的等待客户端连接*/
public class Server extends Thread {
    ServerUI ui;
    ServerSocket ss;
    BufferedReader reader;
    PrintWriter writer;

    public Server(ServerUI ui) {
        this.ui = ui;//获取UI传来的UI对象
        this.start();//开始线程中的 接收信息
    }

    public void run() {
        try {

            ss = new ServerSocket(9654);
            ui.clients=new ArrayList<Socket>();
            println("启动服务器成功：端口9654");
            
            /**
             * 一直运行，持续接收客户端发送来的连接请求
             * 每得到一个连接请求，就调用accept函数返回一个 socket
             * 将 socket 变量存储到 ur.clients中去
             */
            while (true) {
                println("等待客户端");
                try{
                Socket client = ss.accept();
                //我可以在这里创建一个 包含 socket和
                ui.clients.add(client);
                println("连接成功" + client.toString());
                /*
                 * 每次得到一个 socket 创建一个 侦听该客户端的ListenerClient
                 * ListenerClient 中可以时时接收 该socket所代表的客户端 发来的信息
                 * 并且时时地将信息转发到每个 socket所代表的客户端 
                 */
                new ListenerClient(ui, client);
                }
                catch(java.net.SocketException e){
                	System.out.println(e.toString());
                }
            }
        } catch (IOException e) {
            println("启动服务器失败：端口1228");
            println(e.toString());
            e.printStackTrace();
        }

    }

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

    public void closeServer() {
        try {
        	this.stop();
            if (ss != null)
                ss.close();
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
