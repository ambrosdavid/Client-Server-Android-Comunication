package com.example.david.tcpclient;

import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Server implements Runnable {
    private int port;
    private String ip;
    private Socket connessione;
    private FunctionalEdit main;
    private EditText testoInvio;
    private ArrayList<String> pizze;
    private ArrayList<String> Drinks;
    private  ExecutorService operazioniNetwork;

    public Print getPrinter() {
        return printer;
    }

    private Print printer;
    private Read reader;


    public Server(int port, String ip, FunctionalEdit main, ArrayList<String> Drinks, ArrayList<String> pizze, ExecutorService operazioniNetwork) {
        this.pizze=pizze;
        this.port=port;
        this.ip=ip;
        this.main=main;
        this.Drinks=Drinks;
        this.operazioniNetwork=operazioniNetwork;
    }

    @Override
    public void run() {


        try {
            connessione=new Socket(this.ip, this.port);//connessione al server
        } catch (IOException e) {
            e.printStackTrace();
        }

       /*  main.editView(()->{
             testo.setText("Connesso con successo al server");
         });
         */

        printer=new Print(connessione, main);//classe per inviare

        operazioniNetwork.execute(printer.requestPizze());

        reader= new Read(connessione, main, pizze, Drinks);//classe per ricevere dal server


        new Thread(reader).start();

    }
}
