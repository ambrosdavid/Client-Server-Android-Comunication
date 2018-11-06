package com.example.david.tcpclient;

import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Print implements Communicable {
    private PrintWriter outToServer = null;
    private Socket connessione;
    private FunctionalEdit main;




    public Print(Socket connessione, FunctionalEdit main){
        this.connessione=connessione;
        this.main=main;

        try {
            outToServer = new PrintWriter(this.connessione.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Runnable requestPizze() {

        return ()->{
            outToServer.println("request");
        };
    }

    @Override
    public Runnable requestDrinks() {
        return ()->{
            outToServer.println("request drinks...");
        };
    }
}


interface Communicable {
    public Runnable requestPizze();
    public Runnable requestDrinks();

}
