package com.example.david.chatroomudp;

import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

class MessageReceiver implements Runnable{


    private Handler handler;
    private ArrayList<String> messaggi;
    private ListView listview;
    private ArrayAdapter<String> adapter;




    public MessageReceiver(Handler h, ArrayList<String> messaggi, ListView listview,  ArrayAdapter<String> adapter) {
    this.handler=h;
    this.messaggi=messaggi;
    this.listview=listview;
        this.adapter=adapter;

    }


    public void receiveUDPMessage(String ip, int port) throws
            IOException {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(6666);
        InetAddress group = InetAddress.getByName("230.0.0.0");
        socket.joinGroup(group);
        while (true) {
            System.out.println("Waiting for multicast message...");
            DatagramPacket packet = new DatagramPacket(buffer,
                    buffer.length);

            socket.receive(packet);

            String msg = new String(packet.getData(),
                    packet.getOffset(), packet.getLength());
            System.out.println("[Multicast UDP message received]>> " + msg);


            modificaAreaMessaggi(msg);

            if ("OK".equals(msg)) {
                System.out.println("No more message. Exiting : " + msg);
                break;
            }
        }
        socket.leaveGroup(group);
        socket.close();
    }

    private void modificaAreaMessaggi(String msg) {
        handler.post(()->{


            messaggi.add(msg);
            adapter.notifyDataSetChanged();



        });

    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("partito");
        try {
            receiveUDPMessage("230.0.0.0", 6666);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
