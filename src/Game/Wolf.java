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
    private int count = 0; // Added count initialization
    private int energy;
    private boolean isFull;
    private boolean Alpha;
    private final Location Den; // Renamed Den to den for consistency
    private static final int MAX_ATTEMPTS = 10; // Added maximum attempts for the exitDen loop
    int i = 0;
    Rabbit rabbit;
    Den den;
    int denProb;


    public Wolf(World world) {
        super(world);
        this.energy = MAX_ENERGY;
        this.Alpha = false;
        this.Den = null;
    }

    @Override
    public void act(World world) {
        super.act(world);
        i++;
        System.out.println(maxCount);
        if (world.isDay() && sleeping) {
            exitDen();
            System.out.println(this.location);
        }
        if (!(location==null)) {
            eat(world);
        }

        if (world.isNight() && !sleeping) {
            if (!sleeping) {
                if (den != null) {
                    moveGoal(den.getLocation());
                }else {
                    move(world);
                }
            }
        } else if (!isFull && !sleeping && location!=null) {
            if (rabbit==null){
                setLocation(world.getLocation(this));
                setRabbit();
            }
            if (rabbit!=null) {
                moveGoal(rabbit.getLocation());
            }else {
                move(world);
            }
        }else {
            if (world.isDay()) {
                move(world);
            }
        }
        die(world, 50);

    }

    @Override
    public void eat(World world) {
        if (rabbit == null) {
            setRabbit();
        }
        count++;

        if (rabbit != null) {
            Location preyLocation = rabbit.getLocation();
            if (this.location.getX() == preyLocation.getX() && this.location.getY() == preyLocation.getY()) {
                isFull = true;
                this.count = 0;
            }
        }
        if (count>10) {
            isFull = false;
        }
    }

    private void setRabbit() {
        Location preyLocation = findPreyLocation(world);
        if (preyLocation != null) {
            Set<Entity> entities = (Set<Entity>) world.getEntities();
            for (Entity entity : entities) {
                if (entity instanceof Rabbit) {
                    this.rabbit = (Rabbit) entity; // Assuming rabbit is a field in your Wolf class
                    break; // Assuming there is only 1 rabbit at preyLocation
                }
            }
        }
    }

    private void hunt(World world) {
        Location preyLocation = findPreyLocation(world);
        if (preyLocation != null) {
            Set<Entity> entities = (Set<Entity>) world.getEntities();
            for (Entity entity : entities) {
                if (entity instanceof Rabbit) {
                    energy += 10; // gain 10 energy points
                    world.delete(entity);
                    setRabbit(); // Set the rabbit variable after a successful hunt
                    break; // assuming there is only 1 rabbit at preyLocation
                }
            }
        }
    }

    private Location findPreyLocation(World world) {
        Set<Location> neighboringLocations = world.getEmptySurroundingTiles(getLocation());
        return neighboringLocations.isEmpty() ? null : neighboringLocations.iterator().next();
    }

    public void makeDen(){
        if(denProb <= 0 && !world.containsNonBlocking(this.getLocation())) {
            Den den1 = new Den(this.world);
            this.world.setTile(this.getLocation(),den1);
            den1.setLocation(this.getLocation());
            denProb = 25;
        } else {
            rnd = new Random();
            denProb = denProb - rnd.nextInt(5);
        }
    }

    public void setDen() {
        Target findDen = new Target(this.world, this.location, this);
        //this.den = (Den)
        findDen.getBestTarget(den.getClass());
    }

    public void enterDen(){
        if (den == null) {
            setDen();
        }
        if (den != null) {
            Location denLocation = den.getLocation();
            if (this.world.isNight() && this.location.getX() == denLocation.getX() && this.location.getY() == denLocation.getY()) {
                this.sleeping = true;
                this.location = null;
                this.world.remove(this);
            }
        }
    }

    public void exitDen() {
        if (this.world.isTileEmpty(den.getLocation())) {
            world.setTile(den.getLocation(), this);
            this.sleeping = false;
            this.location = den.getLocation();
        }else if(!this.world.isTileEmpty(den.getLocation())) {
            try {
                Set<Location> neighbours = world.getEmptySurroundingTiles(this.location);
                if (!neighbours.isEmpty()) {
                    ArrayList<Location> list = new ArrayList<>(neighbours);
                    Location l = list.get((int) (Math.random() * list.size()));
                      while (!world.isTileEmpty(l)) {
                    }
                      this.sleeping = false;
                      world.setTile(l, this);
                      this.location = l;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
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

    @Override
    public Animal createChild() {
        return new Wolf(world);
    }

    public boolean Alpha() {
        return Alpha;
    }

    public void setAlpha(boolean Alpha) {
        this.Alpha = Alpha;
    }

    @Override
    public DisplayInformation getInformation() {
        if (age < 10) {
            return new DisplayInformation(Color.GRAY, "wolf-small");
        }
        return new DisplayInformation(Color.GRAY, "wolf");
    }

    public void setCount () { maxCount = 0;}

    public void setDenProb() { denProb = 0;}

}




