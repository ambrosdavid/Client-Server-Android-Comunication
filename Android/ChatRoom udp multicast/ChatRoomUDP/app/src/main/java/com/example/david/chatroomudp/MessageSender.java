package com.example.david.chatroomudp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MessageSender {
    public final static int PORT = 6669;
    private DatagramSocket sock;
    private String hostname;
    private String nickname;


    MessageSender(DatagramSocket s, String h, String nickname) {
        this.sock = s;
        this.hostname = h;
        this.nickname=nickname;
    }

    private void sendMessage(String s) throws Exception {
        System.out.println("STRINGA = "+ s);
        byte buf[] = s.getBytes();
        InetAddress address = InetAddress.getByName(hostname);

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);

        sock.send(packet);

    }


    public Runnable scrivi(String st) {
        String s=st;
        return ()->{

            try {
                sendMessage(nickname+": "+s);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }


}
