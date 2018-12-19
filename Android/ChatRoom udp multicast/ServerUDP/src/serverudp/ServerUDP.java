/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.net.SocketException;

/**
 *
 * @author David
 */
public class ServerUDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        Thread server = new Thread(new Server());
        server.start();
    }
    
}
