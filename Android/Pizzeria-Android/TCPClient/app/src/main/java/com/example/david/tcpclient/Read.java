package com.example.david.tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Read implements Runnable {

    private BufferedReader inFromSerber = null;

    private Socket connessione;
    private FunctionalEdit main;
    private ArrayList<String> pizze;
    private ArrayList<String> Drinks;


    public Read(Socket connessione, FunctionalEdit main, ArrayList<String> Drinks, ArrayList<String> pizze) {
        this.connessione = connessione;
        this.main = main;
        this.pizze = pizze;
        this.Drinks = Drinks;

        try {
            inFromSerber = new BufferedReader(new InputStreamReader(this.connessione.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private boolean drinks = false;
    @Override
    public void run() {
        int i = 0;

        while (true) {

            try {
                String readLine = inFromSerber.readLine();
                if (readLine.equals("Drinks")) {
                    Drinks.clear();
                    drinks = true;
                }
                if (!drinks) {
                    pizze.add(readLine);
                    System.out.println("pizze in Read:" + pizze.get(i));
                    i++;
                }else{
                    Drinks.add(readLine);
                }


                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
