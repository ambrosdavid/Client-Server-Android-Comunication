/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverexecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class Pizzeria implements Runnable {

    private BufferedReader bf;
    private ArrayList<Clients> clientConnessi;
    private int indice;
    private String ris;
    private  PrintWriter print;
   


    Pizzeria(BufferedReader bf, ArrayList<Clients> clientConnessi, int i) throws IOException {
        this.clientConnessi = clientConnessi;
        this.bf = bf;
        this.indice = i;
        print = new PrintWriter(clientConnessi.get(indice).getConnessione().getOutputStream(), true);
        creaPizze();
        creaBibite();
    }

    
    
    @Override
    public void run() {

        try {
            Thread.sleep(20);
            avviaProtocollo();
           
            
        } catch (Exception ex) {
           
        }
    }
    
    
    private boolean giaChiesto=false;
    private void avviaProtocollo() throws IOException {
       
       // print.println("Le pizze disponibbili dalla pizzeria da BrothersKebab sono...");
       String string=bf.readLine();
       if(string.equals("request")){
       System.out.println(string);//request
   
       String[] pizze= HashPizze.printPizze();
        for (int i = 0; i <pizze.length; i++) {
            print.println(pizze[i]);
        }
       }
       if(string.equals("request drinks...")&&giaChiesto==false){
           
           giaChiesto=true;
        System.out.println(string);//ricchiesta dal client di drinks
        
        print.println("Drinks");
        String[] BibiteArray= Bibite.printPizze();
        for (int i = 0; i <BibiteArray.length; i++) {
            print.println(BibiteArray[i]);
        }
       }

    }
    
    private Pizze HashPizze = new Pizze();
    private void creaPizze() {
        
        HashPizze.aggiungiPizza("margherita", 5.00);
        HashPizze.aggiungiPizza("diavola", 6.50);
        HashPizze.aggiungiPizza("viennese", 6.00);
        HashPizze.aggiungiPizza("prosciutto", 7.00);
        HashPizze.aggiungiPizza("pizza-kebab", 8.00);
        HashPizze.aggiungiPizza("funghi", 7.50);
        HashPizze.aggiungiPizza("capriciosa", 8.50);
        HashPizze.aggiungiPizza("4stagioni", 9.00);
        HashPizze.aggiungiPizza("matriciana", 9.00);
        HashPizze.aggiungiPizza("napoletana", 9.00);
        HashPizze.aggiungiPizza("spinaci", 9.00);
        HashPizze.aggiungiPizza("radici", 9.00);
        HashPizze.aggiungiPizza("tonno cipolla", 9.00);
        HashPizze.aggiungiPizza("prosciutto crudo", 9.00);
        HashPizze.aggiungiPizza("contadina", 9.00);
        
        
        
        
       
    }

    private Pizze Bibite = new Pizze();
    private void creaBibite() {
        Bibite.aggiungiPizza("Cocacola", 3.00);
        Bibite.aggiungiPizza("Fanta", 3.00);
        Bibite.aggiungiPizza("JackDaniels", 3.00);
        
    }

  

}
