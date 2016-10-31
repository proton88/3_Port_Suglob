package com.suglob.port.state;

import com.suglob.port.entity.Port;
import com.suglob.port.entity.Ship;
import com.suglob.port.utils.Generator;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class LoadingShip implements TargetShip {
    static Logger logger = Logger.getLogger(LoadingShip.class);
    private static final int SPEED_LOADING=10000;
    @Override
    public void executeTarget(Ship ship, Port port) {
        int loadedWeight= Generator.generateRandomInt(ship.getShipCapacity()-ship.getCurrentCapacity());
        //int loadedWeight=50000;
        int timeLoading=(int)(loadedWeight/SPEED_LOADING*Math.random());
        port.checkAndFixLoading(loadedWeight);
        try {
            TimeUnit.MILLISECONDS.sleep(timeLoading);
        } catch (InterruptedException e) {
            logger.error("Unplanned interruption of Thread. Target of ship "+ship.getSHIP_ID()+" not reached",e);
        }
        ship.setCurrentCapacity(ship.getCurrentCapacity()+loadedWeight);
        port.addCurrentCapacity(-loadedWeight);
    }

    @Override
    public String toString() {
        return "LoadingShip";
    }
}
