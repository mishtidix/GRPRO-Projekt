package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;

public class Burrow extends Entity implements NonBlocking, Actor, DynamicDisplayInformationProvider {
    private Location location;
    private Random rnd;
    private Animal animal;
    private Burrow burrow;
    private boolean readyToDig;
    private int probabilityToDig;

    public Burrow(World world) {
        super(world);
        //probabilityToDig = 0;
    }

    public Location getLocation() {
        return location;
    }

    public void enterBurrow(Animal animal) {
        if (world.isNight() && world.getLocation(this.animal).equals(world.getLocation(this.burrow))) {
            world.remove(this.animal);
        }

    }

    public void exitBurrow(Animal animal) {
        rnd = new Random();

        if (world.isDay()) {

        }
    }

    public void digBurrow(Animal animal) {
        rnd = new Random();

        if (world.getDayDuration() % 5 == 0) {
            probabilityToDig = probabilityToDig + rnd.nextInt(5);
        }
        if (probabilityToDig > 20) {
            //Location new_burrow = new Location(world.getLocation(animal),5);


            //world.setTile(world.getLocation(this.animal), new Burrow(, world));
        }
    }

    public void Home(Animal animal) {
        //if (

    }

    @Override
    public void act(World world){

    }


    @Override
    public DisplayInformation getInformation(){
        return new DisplayInformation(Color.GRAY, "hole-small");
    }

}