package com.suglob.port.state;

import com.suglob.port.entity.Port;
import com.suglob.port.entity.Ship;

/**
 * Created by User on 18.10.2016.
 */
public interface TargetShip {
    void executeTarget(Ship ship, Port port);
}
