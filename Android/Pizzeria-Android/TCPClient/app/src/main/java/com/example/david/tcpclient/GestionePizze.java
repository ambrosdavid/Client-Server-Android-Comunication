package com.example.david.tcpclient;

import android.widget.TextView;

import java.util.ArrayList;

public class GestionePizze {

    private ArrayList<String> pizzeSelezionate;
    private MainActivity main;
    private TextView prezzo;

    public String getScontrino() {
        String temp;
        if(risBibite==null){
            temp="Nessuna Bibita Selezionata";
        }else{
            temp=risBibite;
        }
        String ris="Pizze selezionate: "+"\n"+ scontrino +"\n"+"Bibite: "+ temp
                +"\n" +"\n";
        return ris;
    }

    private String scontrino;

    public String getRis() {
        riassunmiOrdine();
        return ris;
    }


    public String getRisNumber() {
        riassunmiOrdine();
        return ris2;
    }


    private String ris;
    private String ris2;


    public GestionePizze(ArrayList<String> pizzeSelezionate, MainActivity mainActivity, TextView prezzo) {
        this.pizzeSelezionate=pizzeSelezionate;
        this.main=mainActivity;
        this.prezzo=prezzo;
        riassunmiOrdine();

    }

    public GestionePizze(ArrayList<String> pizzeSelezionate) {
        this.pizzeSelezionate=pizzeSelezionate;

        riassunmiOrdine();

    }



    private void riassunmiOrdine() {
        double risultato= 0;
        scontrino = "";
        for (int i=0; i<this.pizzeSelezionate.size(); i++){
            scontrino+=this.pizzeSelezionate.get(i)+"\n";
            risultato+= Double.valueOf(scomponiPrezzo(this.pizzeSelezionate.get(i))).doubleValue();

        }
        if(risBibite!=null) {
            risultato += Double.valueOf(scomponiPrezzo(risBibite)).doubleValue();
        }
        System.out.println("Scontrino "+scontrino);
        System.out.println("RISULTATOOOOO= "+ risultato);
        ris= "Importo totale: "+"\n"+risultato+" EUR";
        ris2=" /"+risultato;
    }

    private String scomponiPrezzo(String s) {
        int offset= s.indexOf("/");
        String ris= s.substring(offset+1);

        return  ris;
    }



    private String risBibite;



    public void setRisBibiteRis(String importo) {
        risBibite=importo;
    }
}
