package Game;

import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.executable.*;

import java.awt.*;
import java.util.*;
import java.util.Random;


public class Wolf extends Animal implements Actor, DynamicDisplayInformationProvider {
    private static final int MAX_ENERGY = 50;
    private static final int REPRODUCE_ENERGY_THRESHOLD = 40;
    private Den den;
    private int energy;
    private boolean Alpha;
    private Wolf packAlpha;


    public Wolf(World world) {
        super(world);
        this.energy = MAX_ENERGY;
        findAlpha();
        Predators.add(Bear.class);
    }

    @Override
    public void act(World world) {
        super.act(world);
        move(world);
        hunt(world);
        reproduce(world);

    }

    @Override
    protected void eat(World world) {

    }

    public void setDen() {
        Target findDen = new Target(this.world, this.location, this);
        //this.den = (Den)
        findDen.getBestTarget(den.getClass());
    }

    private void hunt(World world) {
        Location preyLocation = findPreyLocation(world);
        if (preyLocation != null) {
            Set<Entity> entities = (Set<Entity>) world.getEntities();
            for (Entity entity : entities) {
                if (entity instanceof Rabbit) {
                    energy += 10; // gain 10 energy points
                    world.delete(entity);
                    break; // assuming there is only 1 rabbit at preyLocation
                }
            }
        }
    }

    private Location findPreyLocation(World world) {
        Set<Location> neighboringLocations = world.getEmptySurroundingTiles(getLocation());
        return neighboringLocations.isEmpty() ? null : neighboringLocations.iterator().next();
    }


    protected void reproduce(World world) {
        Random random = new Random();
        if (canReproduce && energy >= REPRODUCE_ENERGY_THRESHOLD) {
            Set<Location> emptyLocations = world.getEmptySurroundingTiles(getLocation());

            if (!emptyLocations.isEmpty()) {
                Location randomEmptyLocation = new ArrayList<>(emptyLocations).get(random.nextInt(emptyLocations.size()));
                world.setTile(randomEmptyLocation, new Wolf(world));
                canReproduce = false;
            }
        }
    }
    private void findAlpha(){
        if (!Alpha){
            ArrayList<Object> list = new ArrayList<>(world.getEntities().keySet());
            for (Object object : list){
                if (object.getClass()!= Wolf.class){
                    list.remove(object);
                }
            }
            if (list.isEmpty()){
                this.Alpha=true;
            } else{
                for (Object object : list){
                    Wolf w = (Wolf) object;
                    if (w.Alpha){
                        this.packAlpha = w;
                    }
                }
            }
        }
    }
    private void makeDen(World world){
        if(den == null){
            if (this.Alpha){
                this.den = new Den(world);
                world.setTile(this.getLocation(), den);
            }else {
                this.den = packAlpha.den;
            }
        }
    }

    @Override
    public Animal createChild() {
        return new Wolf(world);
    }

    public boolean Alpha() {
        return Alpha;
    }

    public void setAlpha(boolean Alpha) {this.Alpha = Alpha;}

    @Override
    public DisplayInformation getInformation() {
        if (age < 10) {
            return new DisplayInformation(Color.GRAY, "wolf-small");
        }
        return new DisplayInformation(Color.GRAY, "wolf");
    }

}




