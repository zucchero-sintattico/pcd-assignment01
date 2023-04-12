In questo progetto si vuole realizzare un programma concorrente che, data una direc-
tory D presente sul file system locale contenente un insieme di sorgenti in Java (con-
siderando anche qualsiasi sottodirectory, ricorsivamente), provveda a determinare e
visualizzare le seguenti statistiche:
• il numero di sorgenti Java presenti all’interno della directory D
• gli N sorgenti con il numero maggiore di linee di codice
• la distribuzione complessiva relativa a quanti sorgenti hanno un determinato
numero di linee di codice che ricade in un certo intervallo, considerando un
certo numero di intervalli NI e un numero massimo MAXL di linee di codice
per delimitare l’estremo sinistro dell’ultimo intervallo.

Il programma dovrà fornire anche una GUI, la quale oltre al visualizzare le statisti-
che, metterà a disposizione:
• Un Input frame per specificare i parametri (PATH, N, NI, MAXL)
• Dei pulsanti start/stop per avviare/fermare l’elaborazione
• Una sezione ove visualizzare interattivamente l’output aggiornato, mediante 2 frame:
– un frame relativo ai file con il maggior numero di linee di codice
– un frame relativo alla distribuzione
