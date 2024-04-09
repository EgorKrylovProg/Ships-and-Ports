package Interfaces;

import Ports.*;
import Containers.*;

public interface IShip {

    boolean sailTo(Port port);
    void reFuel(double newFuel);
    boolean load(Container container);
    boolean unLoad(Container container);


}
