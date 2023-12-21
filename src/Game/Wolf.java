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
    private Rabbit rabbit;
    private Den den;
    private int denProb;


    public Wolf(World world) {
        super(world);
        this.energy = MAX_ENERGY;
        this.Alpha = false;
        this.Den = null;
        this.maxCount=20;
        this.MaxHp = 45;
        this.denProb = 25;

    }

    private Set<Entity> getEntitiesAsSet(World world) {
        // Assuming entities is a Map<Object, Location> in your World class
        Map<Object, Location> entitiesMap = world.getEntities();

        // Extract the values (locations) from the map and return as a Set
        Set<Entity> entitiesSet = new HashSet<>();
        for (Location location : entitiesMap.values()) {
            // Assuming Entity is a class that contains location information
            Entity entity = new Entity(world) {
                @Override
                public DisplayInformation getInformation() {
                    if (age < 10) {
                        return new DisplayInformation(Color.GRAY, "wolf-small");
                    }
                    return new DisplayInformation(Color.GRAY, "wolf");
                }

                @Override
                public void act(World world) {
                    // Implement the act method for the concrete entity
                }
            };
            entitiesSet.add(entity);
        }
        return entitiesSet;
    }


    @Override
    public void act(World world) {
        super.act(world);

        if (world.isDay() && sleeping) {
            exitDen();
        }
        if (!(location == null)) {
            eat(world);
        }

        if (world.isNight() && !sleeping) {
            enterDen();
            if (!sleeping) {

                if (den != null) {
                    moveGoal(den.getLocation());
                } else {
                    setDen();
                    move(world);
                }
            }
        } else if (!isFull && !sleeping && location != null) {
            if (rabbit == null) {
                setLocation(world.getLocation(this));
                setRabbit(world); // Pass the World parameter here
            }
            if (rabbit != null) {
                moveGoal(rabbit.getLocation());
            } else {
                move(world);
            }
        } else {
            if (world.isDay() && location!=null) {
                move(world);
            }
        }
        die(world, 70);
    }

    @Override
    public void eat(World world) {
        if (rabbit == null) {
            setRabbit(world);
        }
        count++;

        if (rabbit != null) {
            Location preyLocation = rabbit.getLocation();
            if (preyLocation!=null){
                if (this.location.getX() == preyLocation.getX() && this.location.getY() == preyLocation.getY()) {
                    isFull = true;
                    this.count = 0;
                }
            }
        }
        if (count > 10) {
            isFull = false;
        }
    }

    private void setRabbit(World world) {
        Target findRabbit = new Target(world, location, this);
        this.rabbit = (Rabbit)findRabbit.getBestTarget(Rabbit.class);
    }


    private void hunt(World world) {
        Location preyLocation = findPreyLocation(world);
        if (preyLocation != null) {
            Set<Entity> entities = getEntitiesAsSet(world);
            for (Entity entity : entities) {
                if (entity.getLocation().equals(preyLocation) && entity instanceof Rabbit) {
                    energy += 10;
                    world.delete(entity);
                    setRabbit(world);
                    break;
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
        makeDen();
        this.location=world.getLocation(this);
        Target findDen = new Target(this.world, this.location, this);
        if (findDen.getBestTarget(Game.Den.class)!=null) {
            this.den = (Den) findDen.getBestTarget(Game.Den.class);
        }
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
        if (den != null && this.world.isTileEmpty(den.getLocation())) {
            world.setTile(den.getLocation(), this);
            this.sleeping = false;
            this.location = den.getLocation();
        }else if(!this.world.isTileEmpty(den.getLocation())) {
            try {
                Set<Location> neighbours = world.getEmptySurroundingTiles(den.getLocation());
                if (!neighbours.isEmpty()) {
                    ArrayList<Location> list = new ArrayList<>(neighbours);
                    Location l = list.get((int) (Math.random() * list.size()));
                    while (!world.isTileEmpty(l)) {
                        l = list.get((int) (Math.random() * list.size()));
                    }
                    this.sleeping = false;
                    world.setTile(l, this);
                    this.location = l;
                }
            }catch (Exception e){
                e.printStackTrace();
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




