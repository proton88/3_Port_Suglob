package com.suglob.port.state;

import com.suglob.port.entity.Port;
import com.suglob.port.entity.Ship;
import com.suglob.port.utils.Generator;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UnloadingShip implements TargetShip {
    static Logger logger = Logger.getLogger(UnloadingShip.class);
    private static final int SPEED_UNLOADING=15000;
    @Override
    public void executeTarget(Ship ship, Port port) {

        int unloadedWeight=Generator.generateRandomInt(ship.getCurrentCapacity());
        int timeUnloading=(int)(unloadedWeight/SPEED_UNLOADING*Math.random());
        port.checkAndFixUnloading(unloadedWeight);
        try {
            TimeUnit.MILLISECONDS.sleep(timeUnloading);
        } catch (InterruptedException e) {
            logger.error("Unplanned interruption of Thread. Target of ship "+ship.getShipId()+" not reached",e);
        }
        ship.setCurrentCapacity(ship.getCurrentCapacity()-unloadedWeight);
        port.addCurrentCapacity(unloadedWeight);
    }

    @Override
    public String toString() {
        return "UnloadingShip";
    }
}
