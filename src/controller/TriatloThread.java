package controller;

import java.util.concurrent.Semaphore;

class Atleta {
    int id;
    int pontos;

    Atleta(int id) {
        this.id = id;
        this.pontos = 0;
    }

    void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }
}

public class TriatloThread extends Thread {
    
    private static Atleta[] atletas = new Atleta[26];
    private Semaphore semaforo;
    private int id;
    private static int cont = 0;

    public TriatloThread(Semaphore semaforo, int id) {
        this.semaforo = semaforo;
        this.id = id;
        atletas[id] = new Atleta(id);
    }

    public void run() {
        corrida();
        tiros();
        ciclismo();
        chegada();
        ranking();
    }

    public void corrida() {
        int distanciatotalcorrida = 3000;
        int distancia = (int) ((Math.random() * 6) + 20);
        int distanciapercorrida = 0;
        int km = 0;
        while (distanciapercorrida < distanciatotalcorrida) {
            distanciapercorrida = distanciapercorrida + distancia;

            if (distanciapercorrida >= km) {
                System.out.println("O Atleta " + id + " percorreu " + distanciapercorrida + " metros da corrida");
                km = km + 500;
            }

            int tempo = 30;
            try {
                sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ciclismo() {
        int distanciatotalciclismo = 5000;
        int distancia = (int) ((Math.random() * 11) + 30);
        int distanciapercorrida = 0;
        int km = 0;
        while (distanciapercorrida < distanciatotalciclismo) {
            distanciapercorrida = distanciapercorrida + distancia;
            if (distanciapercorrida >= km) {
                System.out.println("O Atleta " + id + " percorreu " + distanciapercorrida + " metros no ciclismo");
                km = km + 500;
            }
            int tempo = 40;
            try {
                sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tiros() {
        int tiros = 0;
        int totaltiros = 0;
        try {
            semaforo.acquire();
            int tempo = (int) ((Math.random() * 2500) + 500);
            System.out.println("O atleta " + id + " está na fase de Tiro ao alvo");

            for (int i = 1; i <= 3; i++) {
                tiros = (int) ((Math.random() * 11));
                System.out.println("O atleta " + id + " no seu " + i + "° tiro fez " + tiros + " pontos");
                totaltiros += tiros;
                try {
                    sleep(tempo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
        atletas[id].adicionarPontos(totaltiros);
    }

    public void chegada() {
        int pontos = 250 - (cont * 10);
        atletas[id].adicionarPontos(pontos);
        cont++;
    }

    public void ranking() {
        Atleta[] ranking = new Atleta[26];
        for (int i = 1; i <= 25; i++) {
            ranking[i] = atletas[i];
        }

        for (int i = 1; i <= 25; i++) {
            for (int j = 1; j <= 25 - 1; j++) {
                if (ranking[j].pontos < ranking[j + 1].pontos) {
                    Atleta temp = ranking[j];
                    ranking[j] = ranking[j + 1];
                    ranking[j + 1] = temp;
                }
            }
        }

        System.out.println("-- Ranking de Pontuação --");
        for (int i = 1; i <= 25; i++) {
            System.out.println("Atleta: " + ranking[i].id + " Pontos: " + ranking[i].pontos);
        }
    }
}

