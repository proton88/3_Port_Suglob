package com.suglob.port.state;

import com.suglob.port.entity.Port;
import com.suglob.port.entity.Ship;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoadingShip implements TargetShip {
    static Logger logger = Logger.getLogger(LoadingShip.class);
    private static final int SPEED_LOADING=10000;
    @Override
    public void executeTarget(Ship ship, Port port) {
        Random random = new Random();
        int loadedWeight=random.nextInt(ship.getShipCapacity()-ship.getCurrentCapacity());
        //int loadedWeight=50000;
        int timeLoading=(int)(loadedWeight/SPEED_LOADING*Math.random());
        port.checkAndFixLoading(loadedWeight);
        try {
            TimeUnit.MILLISECONDS.sleep(timeLoading);
        } catch (InterruptedException e) {
            logger.error("Unplanned interruption of Thread. Target of ship "+ship.getShipId()+" not reached",e);
        }
        ship.setCurrentCapacity(ship.getCurrentCapacity()+loadedWeight);
        port.addCurrentCapacity(-loadedWeight);
    }

    @Override
    public String toString() {
        return "LoadingShip";
    }
}
