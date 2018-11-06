/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverexecutor;

import java.util.HashMap;

/**
 *
 * @author David
 */
class Database {

    static HashMap<String, String> clienti = new HashMap<>();



    public Database() {

    }

    public void aggiungiCliente(String nome, String password) {
        System.out.println(nome + " " + password);
        clienti.put(nome, password);

    }

    public String ricavaPassword(String nome) {

        return clienti.get(nome);
    }

    public boolean controllaNome(String nome) {

        return clienti.containsKey(nome);
    }
}
