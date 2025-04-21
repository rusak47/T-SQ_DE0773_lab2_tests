package org.rusak.rtu.ditef.ai.tsq.hw2.models;

public interface DelayIntf {
    public default void waittoappear(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while sleeping: "+e.getLocalizedMessage());
            //Thread.currentThread().interrupt();
            //throw new RuntimeException("Thread was interrupted", e);
        }
    }
}
