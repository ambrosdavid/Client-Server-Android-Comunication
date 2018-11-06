package com.example.david.tcpclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends ListActivity implements FunctionalEdit {

    private static final int port = 6666;
    private static final String ip = "151.49.25.162";
    private TextView Prezzo;


    private Button invio;
    private Button prevew;
    private Button bibite;

    public ExecutorService operazioniNetwork = Executors.newFixedThreadPool(4);
    public Handler handler = new Handler();
    private GestionePizze gestionePizze;
    private ListView listview;


    private ArrayList<String> pizze = new ArrayList<>();
    private ArrayList<String> drinks = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bibite=findViewById(R.id.Bibite);

        invio= findViewById(R.id.invio);
        prevew = findViewById(R.id.Prevew);
        Prezzo=findViewById(R.id.Prezzo);


        listview = getListView();
        listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
        listview.setTextFilterEnabled(true);


        prevew.setAlpha(0.5f);
        prevew.setActivated(false);
        bibite.setAlpha(0.5f);
        bibite.setActivated(false);


        Handler h=new Handler();//handler che si occuperà dei cambiamenti grafici
        Server server = new Server(port, ip, this, pizze, drinks, operazioniNetwork);//Oggetto che si occupa della lettura e scrittura
        //da parte del server, stabilendo una connessione

        new Thread(server).start();





        invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                h.post(()->{
                    setListAdapter(new ArrayAdapter<String>(v.getContext(),
                            android.R.layout.simple_list_item_checked, pizze));

                    invio.setAlpha(0.5f);
                    invio.setActivated(false);
                    prevew.setAlpha(1);
                    prevew.setActivated(true);
                     bibite.setAlpha(1);
                     bibite.setActivated(true);

                    gestionePizze= new GestionePizze(PizzeSelezionate, MainActivity.this, Prezzo);
                    //classe che si occupa del calcolo del prezzo mano a mano che vengono selezionate pizze e bibite

                });



            }
        });
        prevew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, FinalActivity.class);
                myIntent.putExtra("Importo", gestionePizze.getScontrino()+"\n"+gestionePizze.getRis());
                //viene passato al intent finale la stringa con lo scontrino, generata da gestionePizze
                MainActivity.this.startActivity(myIntent);

            }
        });

        bibite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /**invia la richiesta dei drinks al server*/
                    operazioniNetwork.execute(server.getPrinter().requestDrinks());
                    //lo do in pasto al executor in quanto lo deve fare appena possibile, infatti printer
                    //è un task

                new Thread(()->{
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int j=0; j<drinks.size(); j++){
                        System.err.println(drinks.get(j));
                    }
                    //viene utilizzato un thread per usufruire del metodo sleep,
                    //in quanto la risposta del server non è immediata, serve tempo
                    // a ricevere i drinks, dopo averli ricchiesti con "request drinks";

                    Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);//activity dei drinks
                    myIntent.putExtra("Drinks", drinks); //passo al intent l'ArrayList di Drinks
                    startActivityForResult(myIntent, 1);//ritorna il prezzo delle bibite selezionate


                }).start();

                }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
           String s=((data.getExtras().getString("TADA") ));
           gestionePizze.setRisBibiteRis(s);
           //aggiunge il risultato del intent al prezzo delle pizze gia selezionate
            editView(()->{
                Prezzo.setText
                        (gestionePizze.getRis());

            });

        }
    }


    @Override
    public void editView(Runnable edit) {
        handler.post(edit);
    } //handler che si occupa dell interfaccia grafica

    private ArrayList<String> PizzeSelezionate = new ArrayList<>();
    public void onListItemClick(ListView parent, View v, int position, long id) {
        CheckedTextView item = (CheckedTextView) v;


        //aggiunge le pizze selezionate o le rimuove dall arrayList
        if(item.isChecked()){
            PizzeSelezionate.add(pizze.get(position));

        }else{
            PizzeSelezionate.remove(pizze.get(position));

        }


        //ogni volta che una pizza viene aggiunta o rimossa viene ricalcolato il prezzo
        //di prevew, mostrandolo a video in tempo reale, usando il handler
        editView(()->{
            Prezzo.setText
                    (gestionePizze.getRis());

            });

    }

}