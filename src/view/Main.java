package view;

import java.util.concurrent.Semaphore;
import controller.TriatloThread;


public class Main {
    
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(5);
        
        for (int id = 1; id <= 25; id++) {
            TriatloThread tra = new TriatloThread(semaforo, id);
            tra.start();
        }
    }
}