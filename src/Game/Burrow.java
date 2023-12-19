package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;

public class Burrow extends Entity implements NonBlocking, Actor, DynamicDisplayInformationProvider {

    public Burrow(World world) {

        super(world);

    }

    public Location getLocation(Location location) {
        return location;
    }



    @Override
    public void act(World world){

    }


    @Override
    public DisplayInformation getInformation(){

        return new DisplayInformation(Color.GRAY, "hole-small");
    }

}