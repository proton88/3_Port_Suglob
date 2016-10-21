package com.suglob.port.entity;

import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    static Logger logger = Logger.getLogger(Port.class);

    private static final String DEFAULT_NAME="Unknown";
    private static final int DEFAULT_MAX_CAPACITY=5000_000;
    private static final int DEFAULT_CURRENT_CAPACITY=2500_000;
    private static final int DEFAULT_SAFETY_CAPACITY=500_000;


    private String name;
    private int maxCapacity;
    private AtomicInteger currentCapacity;
    private Lock lock=new ReentrantLock();

    public Port() {
        name=DEFAULT_NAME;
        maxCapacity=DEFAULT_MAX_CAPACITY;
        currentCapacity.set(DEFAULT_CURRENT_CAPACITY);

    }

    public Port(String name, int maxCapacity, int currentCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = new AtomicInteger(currentCapacity);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity.set(currentCapacity);
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity.get();
    }

    public void addCurrentCapacity(int loadedWeight){
        currentCapacity.addAndGet(loadedWeight);
    }


    private void loadPort(){
        currentCapacity.addAndGet(DEFAULT_SAFETY_CAPACITY);
    }

    private void unloadPort(){
        currentCapacity.addAndGet(-DEFAULT_SAFETY_CAPACITY);
    }

    public void checkAndFixLoading(int requiredWeight){
        try {
            lock.lock();
            if (requiredWeight > currentCapacity.get()) {
                loadPort();
                TimeUnit.MILLISECONDS.sleep(2);
                logger.warn("The storage of port was refill. Current capacity: "+currentCapacity);
            }
        }catch (InterruptedException e){
            logger.error("Something interrupted the refill of the storage",e);
        }finally {
            lock.unlock();
        }

    }

    public void checkAndFixUnloading(int requiredWeight){
        try {
            lock.lock();
            if (requiredWeight>maxCapacity-currentCapacity.get()){
                unloadPort();
                TimeUnit.MILLISECONDS.sleep(2);
                logger.warn("The storage of port was unload. Current capacity: "+currentCapacity);
            }
        }catch (InterruptedException e){
            logger.error("Something interrupted the unloading of the storage",e);
        }finally {
            lock.unlock();
        }

    }
}
