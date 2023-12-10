package Game;
import itumulator.simulator.Actor;
import itumulator.world.*;
import itumulator.executable.*;
import itumulator.simulator.*;
import java.util.*;
import java.awt.*;
public class Den extends Entity implements NonBlocking, Actor, DynamicDisplayInformationProvider {

    public Den(World world) {
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
        return new DisplayInformation(Color.DARK_GRAY, "hole");
    }
}
