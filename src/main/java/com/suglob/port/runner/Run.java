package com.suglob.port.runner;

import com.suglob.port.entity.Port;
import com.suglob.port.entity.Ship;
import com.suglob.port.exception.handler.ShipUncaughtExceptionHandler;
import com.suglob.port.state.LoadingShip;
import com.suglob.port.state.UnloadingShip;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Run {
    private static final String PROPERTY_FILE_LOG4J="src/main/resources/property/log4j.properties";
    static {
        PropertyConfigurator.configure(PROPERTY_FILE_LOG4J);
    }
    static Logger logger = Logger.getLogger(Run.class);
    private static final int COUNT_DOCK=5;
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new ShipUncaughtExceptionHandler());
        ExecutorService dockManager = Executors.newFixedThreadPool(COUNT_DOCK);
        Port port=new Port("Minsk", 2000000, 1900000);
        for (int i=0; i<1000;i++) {
            Ship ship = new Ship(10000, 7000, new UnloadingShip(), port);
            dockManager.execute(ship);
        }
        dockManager.shutdown();

    }
}
