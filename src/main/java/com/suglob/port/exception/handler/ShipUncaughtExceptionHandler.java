package com.suglob.port.exception.handler;

import com.suglob.port.entity.Ship;
import org.apache.log4j.Logger;

/**
 * Created by User on 21.10.2016.
 */
public class ShipUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    static Logger logger = Logger.getLogger(ShipUncaughtExceptionHandler.class);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("Something wrong with ship "+t.getId()+" ."+e);
    }
}
