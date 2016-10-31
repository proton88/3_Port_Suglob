package com.suglob.port.entity;

import com.suglob.port.state.LoadingShip;
import com.suglob.port.state.TargetShip;
import com.suglob.port.state.UnloadingAndLoadingShip;
import com.suglob.port.state.UnloadingShip;
import com.suglob.port.utils.Generator;
import org.apache.log4j.Logger;

public class Ship extends Thread{
    static Logger logger = Logger.getLogger(Ship.class);

    private final long SHIP_ID = Generator.generateNextLong();
    private int shipCapacity;
    private int currentCapacity;
    private TargetShip targetShip;
    private Port port;

    public Ship(int shipCapacity, int currentCapacity, TargetShip targetShip, Port port) {
        this.shipCapacity = shipCapacity;
        this.currentCapacity = currentCapacity;
        this.targetShip = targetShip;
        this.port=port;
        logger.info("Ship: " + SHIP_ID +", was created. Max capacity: "+shipCapacity+", current capacity: "+
                currentCapacity+", target: "+targetShip+"!");
    }

    public long getSHIP_ID() {
        return SHIP_ID;
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


    @Override
    public void run(){
        targetShip.executeTarget(this, port);


        if (targetShip.getClass()==new UnloadingShip().getClass()){
            targetShip=new LoadingShip();
        }else{
            int i=Generator.generateRandomInt(2);
            targetShip=i==0?new UnloadingShip():new UnloadingAndLoadingShip();
        }
        logger.info("Ship "+ SHIP_ID +" change target on: "+targetShip+". Max capacity: "+shipCapacity+
                ", current capacity: "+ currentCapacity);
    }


}
