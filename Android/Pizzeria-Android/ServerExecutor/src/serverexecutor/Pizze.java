/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverexecutor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author David
 */
public class Pizze {
    
    HashMap<String, Double> pizze=new HashMap<String, Double>();
    
    public Pizze(){
        
    }

    public HashMap<String, Double> getPizze() {
        return pizze;
    }
    
    
    
    public void aggiungiPizza(String nome, Double costo){
        pizze.put(nome, costo);
    }
    
    public double ricavaCosto(String nome){
        
        return pizze.get(nome);
    }

    public String[] printPizze() {
       Set<String> it= pizze.keySet();
       Iterator<String> iterator=it.iterator();
       String[] ris=new String[it.size()] ;
       int i=0;
       while(iterator.hasNext()){
           
           String nome = iterator.next();
          // System.out.println(nome+"; "+pizze.get(nome)+";");
           String ris1=nome.toString();
           String ris2=" ";
           String ris3=" ";
           int diferenza=30-nome.length();
           for (int j = 0; j < diferenza; j++) {
               ris2+=ris3;
           }
           
           ris[i]=ris1+ris2+"/"+pizze.get(nome);
           i++;
       }
       
       return ris;
    }

    
    
}
