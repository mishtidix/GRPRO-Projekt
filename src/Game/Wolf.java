package Game;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.executable.*;
import java.util.*;
import java.util.Random;
public class Wolf extends Animal implements Actor, DynamicDisplayInformationProvider {
    private static final int MAX_ENERGY = 50;
    private int energy;
    private boolean Alpha;
    private Location Den;
    Den den;

    public Wolf(World world) {
        super(world);
        this.energy = MAX_ENERGY;
        this.Alpha = false;
        this.Den = null;
    }

    @Override
    public void act(World world) {
        super.act(world);
        move(world);
        hunt(world);
        reproduce(world);

    }

    public void setDen() {
        Target findDen = new Target(this.world, this.current, this);
        //this.den = (Den)
        findDen.getBestTarget(den);
    }

    private void hunt(World world) {
        // logic for hunting rabbits
        Location preyLocation = findPreyLocation(world);
        if (preyLocation != null) {
            Set<Entity> entities = world.getEntities(preyLocation);
            for (Entity entity : entities) {
                if (entity instanceof Rabbit) {
                    //wolf eats the rabbit
                    energy += 10; //gain 10 energy points
                    world.delete(entity);
                    break; //assuming there is only 1 rabbit @preyLocation
                }
            }

        }
    }

    private Location findPreyLocation(World world) {
        Set<Location> neighbouringLocations = world.getSurroundingTiles(getLocation());
        for (Location location : neighbouringLocations) {
            Set<Entity> entities = world.getEntities(location);
            for (Entity entity : entities) {
                if (entity instanceof Rabbit) {
                    // found a rabbit nearby
                    return location;
                }
            }
        }
    }
}




