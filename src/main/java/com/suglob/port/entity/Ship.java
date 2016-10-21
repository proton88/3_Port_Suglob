package com.suglob.port.entity;

import com.suglob.port.state.LoadingShip;
import com.suglob.port.state.TargetShip;
import com.suglob.port.state.UnloadingAndLoadingShip;
import com.suglob.port.state.UnloadingShip;
import org.apache.log4j.Logger;

import java.util.Random;

public class Ship extends Thread{
    static Logger logger = Logger.getLogger(Ship.class);
    private static long count=1;

    private final long shipId=count++;
    private int shipCapacity;
    private int currentCapacity;
    private TargetShip targetShip;
    private Port port;

    public Ship(int shipCapacity, int currentCapacity, TargetShip targetShip, Port port) {
        this.shipCapacity = shipCapacity;
        this.currentCapacity = currentCapacity;
        this.targetShip = targetShip;
        this.port=port;
        logger.info("Ship: " + shipId+", was created. Max capacity: "+shipCapacity+", current capacity: "+
                currentCapacity+", target: "+targetShip+"!");
    }

    public long getShipId() {
        return shipId;
    }

    public int getShipCapacity() {
        return shipCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public TargetShip getTargetShip() {
        return targetShip;
    }

    public void setTargetShip(TargetShip targetShip) {
        this.targetShip = targetShip;
    }

    private void executeTarget(){
        targetShip.executeTarget(this, port);
        changeTarget();
    }

    private void changeTarget(){
        if (targetShip.getClass()==new UnloadingShip().getClass()){
            targetShip=new LoadingShip();
        }else{
            int i=new Random().nextInt(2);
            targetShip=i==0?new UnloadingShip():new UnloadingAndLoadingShip();
        }
        logger.info("Ship "+ shipId+" change target on: "+targetShip+". Max capacity: "+shipCapacity+
                ", current capacity: "+ currentCapacity);
    }

    @Override
    public void run(){
        executeTarget();
    }


}
