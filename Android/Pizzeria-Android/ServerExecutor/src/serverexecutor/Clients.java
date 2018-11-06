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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Clients {

    private Socket connessione;

    public Clients(Socket connessione) {
        this.connessione = connessione;
    }

    public Socket getConnessione() {
        return connessione;
    }
  
    private String nomeClient;

    public String getNomeClient() {
        return nomeClient;
    }


}
