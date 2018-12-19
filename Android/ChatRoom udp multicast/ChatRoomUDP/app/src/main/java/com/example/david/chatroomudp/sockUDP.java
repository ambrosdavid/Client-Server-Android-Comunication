package com.example.david.chatroomudp;

import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class sockUDP implements Runnable {
    private MessageSender s;
    private ArrayList<String> messaggi;
    private  ArrayAdapter<String> adapter;
    private Handler h;

    private String nickname;
    private ListView listview;
    private ExecutorService ex= Executors.newFixedThreadPool(1);


    public sockUDP(Handler h, ArrayList<String> messaggi, ListView listview, ArrayAdapter<String> adapter) {
        this.h=h;
        this.messaggi=messaggi;
        this.listview=listview;
        this.adapter=adapter;

    }

    public  void setName(String nickname) {
        this.nickname=nickname;
    }

    @Override
    public void run() {
        String host = "192.168.1.10";

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(6669);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
            System.out.println("S******Crashatpoo");
        }

        MessageReceiver r = new MessageReceiver(h, messaggi, listview, adapter);
        s = new MessageSender(socket, host, nickname);
        Thread receiverThread = new Thread(r);

       receiverThread.start();

    }

    public MessageSender getMessageSender(){
        return this.s;
    }


}
