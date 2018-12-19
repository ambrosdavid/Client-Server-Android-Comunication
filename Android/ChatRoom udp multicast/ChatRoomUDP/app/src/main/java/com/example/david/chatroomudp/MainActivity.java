package com.example.david.chatroomudp;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends ListActivity{

    private ExecutorService exec = Executors.newFixedThreadPool(10);
    private EditText areaTesto;
    private Button invioTesto;

    private sockUDP socket;
    private ListView listview;


    ArrayAdapter<String> adapter;

    private ArrayList<String> messaggi = new ArrayList<>();

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertFormElements();

        h=new Handler();


        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                messaggi);
        setListAdapter(adapter);

        socket = new sockUDP(h, messaggi, listview, adapter);

        listview = getListView();
        listview.setTextFilterEnabled(true);

        areaTesto= findViewById(R.id.areaTesto);
        invioTesto=findViewById(R.id.invioTesto);

        invioTesto.setOnClickListener(v ->
                exec.execute(socket.getMessageSender().scrivi(areaTesto.getText().toString())));
    }

    public void alertFormElements() {

        /*
         * Inflate the XML view. activity_main is in
         * res/layout/form_elements.xml
         */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.allert,
                null, false);

        final EditText nameEditText = formElementsView.findViewById(R.id.nameEditText);
        System.out.println(nameEditText.toString());
        // the alert dialog
        new AlertDialog.Builder(MainActivity.this).setView(formElementsView)
                .setTitle("Form Elements")
                .setPositiveButton("OK", (dialog, id) -> {




                    socket.setName(nameEditText.getText().toString());
                    Thread sThread = new Thread(socket);
                    sThread.start();

                    dialog.cancel();
                }).show();
    }


}
