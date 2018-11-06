# Pizzeria o Chat

Vengono proposte le seguenti consegne.

## Client e Server per una pizzeria

Il servizio offerto è un risponditore automatico per poter ordinare alla pizzera Gennarino le pizze ed eventuali vivande. La tecnologia da usare è quella dei Socket TCP in Java. 
- Una classe che realizzi la **macchina a stati finiti**, in pratica un protocollo testuale tipo http, cui corrisponde il sorgente `Pizzeria.java`.
- Una classe su `Server.java` che fa uso del suddetto procollo che lavora in modo concorrente usando gli esecutori Java.
- Il client su di un file `Client.java`.
- Alla fine dell'ordine viene dato un report (cosa hai ordinato) e il prezzo da pagare.
- Facoltativo: i dati e le ordinazioni possono essere mantenuti un un DB: SQLite o MySQL.
- Sulla documentazione aggiungere il file UML per la macchina a stati finiti.

## Client Android e Server per una pizzeria

Tutto come sopra solo che il client è un app Android usando la programmazione concorrente (`Thread`, `Handler` ecc.).

## Chat Android

Ove le app sono sia client che server... si lavori sempre in modo concorrente dedicando un thread alla parte client ed uno alla parte server.

## Valutazione

Oltre alla pulizia del codice, alla documentazione ed alle scelte operate il progetto deve essere consegnato in una cartella `PizzeriaGennarino` o `Chat` che contenga il file `README.md` in cui si dà breve descrizione del progetto con attenzione alle scelte operate. Le valutazioni eccellenti (8, 9, 10) sono riservate alle ai progetti Android e deriveranno da un consistente apporto personale e creativo.

## .. per chi consegna in Android

Per coloro i quali consegnano l'app Android viene dato un tempo extra: 06/11/2018.
