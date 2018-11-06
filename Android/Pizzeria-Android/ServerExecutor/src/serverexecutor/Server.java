/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverexecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Server implements Runnable {

    private final int porta;
    private boolean isStoped;
    private Socket connessione;
    private ServerSocket server;
    private ArrayList<Clients> clientConnessi = new ArrayList<>();
    private ExecutorService executor;

    public Server(int porta) {
        this.executor = Executors.newCachedThreadPool();
        this.porta = porta;
    }

    @Override
    public void run() {
        isStoped = false;
        accendiServer();

        new Thread(() -> {
            while (true) {
                try { 
                    
                    accettaClient();
                   for (int i = 0; i < clientConnessi.size(); i++) {
                        if(!(clientConnessi.get(i).getConnessione().isConnected())){
                            clientConnessi.remove(i);
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("errore accettaClient");
                }
            }
        }).start();

       new Thread(()->{
           while(!isStoped){
            for (int i = 0; i < clientConnessi.size(); i++) {
                
                BufferedReader bf = null;
                try {
                    bf = new BufferedReader(new InputStreamReader(clientConnessi.get(i).getConnessione().getInputStream()));
                } catch (IOException ex) {
                    System.err.println("Errore in");
                }
                try {
                    if (bf.ready()) {
                        executor.submit(new Pizzeria(bf, clientConnessi, i));
                       
                    }
                } catch (IOException ex) {
                    System.out.println("Errore out");
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
       }).start();
        
    }

    private void accendiServer() {
        try {
            this.server = new ServerSocket(this.porta);
            System.out.println("Server avviato alla porta: " + this.porta);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port", e);
        }
    }

    private void accettaClient() throws IOException {

        System.out.println("Client connesso " + connessione);
        this.connessione = server.accept();
        clientConnessi.add(new Clients(this.connessione));
       

    }

    public void close() throws IOException {
        isStoped = true;
        server.close();

    }

     private void trasmettiAgliAltri(int indice, String s) {
       // new Thread(() -> {
            for (int j = 0; j < clientConnessi.size(); j++) {
                PrintWriter print;
                if (j != indice) {
                    try {

                        print = new PrintWriter(clientConnessi.get(j).getConnessione().getOutputStream(), true);
                        print.println(s);
                       

                    } catch (IOException ex) {
                        System.err.println("Errore out");
                    }
                }
            }
       // }).start();
    }
   

}
