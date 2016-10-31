package com.suglob.port.state;

import com.suglob.port.entity.Port;
import com.suglob.port.entity.Ship;
import com.suglob.port.utils.Generator;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class UnloadingAndLoadingShip implements TargetShip {
    static Logger logger = Logger.getLogger(UnloadingAndLoadingShip.class);
    private static final int SPEED_UNLOADING=15000;
    private static final int SPEED_LOADING=10000;
    @Override
    public void executeTarget(Ship ship, Port port) {

        int unloadedWeight= Generator.generateRandomInt(ship.getCurrentCapacity());
        int timeUnloading=(int)(unloadedWeight/SPEED_UNLOADING*Math.random());
        port.checkAndFixUnloading(unloadedWeight);
        try {
            TimeUnit.MILLISECONDS.sleep(timeUnloading);
        } catch (InterruptedException e) {
            logger.error("Unplanned interruption of Thread. Target of ship "+ship.getSHIP_ID()+" not reached",e);
        }
        ship.setCurrentCapacity(ship.getCurrentCapacity()-unloadedWeight);
        port.addCurrentCapacity(unloadedWeight);

        int loadedWeight=Generator.generateRandomInt(ship.getShipCapacity()-ship.getCurrentCapacity());
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
        return "UnloadingAndLoadingShip";
    }
}
