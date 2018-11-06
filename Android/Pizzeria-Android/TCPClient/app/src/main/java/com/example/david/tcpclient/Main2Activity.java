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

public class Main2Activity extends ListActivity implements FunctionalEdit {


    private ArrayList<String> bibite2 = new ArrayList<>();
    private Button indietro;
    private Intent i;
    private String Importo="";
    public Handler handler = new Handler();
    private TextView PrezzoBibite;
    private GestionePizze gestioneBibite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        indietro=findViewById(R.id.back);//tasto indietro
         i= getIntent();
        bibite2= i.getStringArrayListExtra("Drinks");//prendo l' arraylist di bibite
        bibite2.remove(0);//lo azzero in quanto si creerebero duplicati ogni volta che avvio l'intent
        PrezzoBibite =findViewById(R.id.PrezzoBibite);
        gestioneBibite= new GestionePizze(BibiteSelezionate);


        //creazione lista
        ListView listview = getListView();
        listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
        listview.setTextFilterEnabled(true);



            editView(()->{
                setListAdapter(new ArrayAdapter<String>(this.getApplicationContext(),
                        android.R.layout.simple_list_item_checked, bibite2));

            });






        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//pulsante indietro
                //viene settato come entra il prezzo delle bibite selezionte
                getIntent().putExtra("TADA", gestioneBibite.getRisNumber());
                setResult(RESULT_OK, getIntent());
                finish();


            }
        });
    }


    @Override
    public void editView(Runnable edit) {
        handler.post(edit);
    }


    private ArrayList<String> BibiteSelezionate = new ArrayList<>();
    public void onListItemClick(ListView parent, View v, int position, long id){

        CheckedTextView item = (CheckedTextView) v;
        if(item.isChecked()){
            BibiteSelezionate.add(bibite2.get(position));

        }else{
            BibiteSelezionate.remove(bibite2.get(position));

        }
        editView(()->{

            PrezzoBibite.setText(gestioneBibite.getRis());

        });
    }

}
