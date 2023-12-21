package Game;

import itumulator.executable.*;
import itumulator.simulator.*;
import itumulator.world.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.awt.*;
import java.util.*;
import java.util.Random;


public class Fox extends Animal implements Actor, DynamicDisplayInformationProvider {
    private static final int MAX_ENERGY = 50;
    private static final int REPRODUCE_ENERGY_THRESHOLD = 40;
    private int energy;
    int hulProb;
    Burrow burrow;

    public Fox(World world) {
        super(world);
        this.energy = MAX_ENERGY;
    maxCount=20;
    MaxHp=40;
    }

    @Override
    public void act(World world) {
        super.act(world);
        move(world);
        reproduce(world);
        hunt(world);
        die(world,45);
    }

    @Override
    protected void eat(World world) {
    }

    private void hunt(World world) {
        Location preyLocation = findPreyLocation(world);
        if (preyLocation != null) {
            Set<Object> entities = world.getEntities().keySet();
            for (Object object : entities) {
                if (object instanceof Rabbit && count>15) {
                    energy += 10; // gain 10 energy points
                    world.delete(object);
                    break; // assuming there is only 1 rabbit at preyLocation
                }
            }
        }
    }

    private Location findPreyLocation(World world) {
        Set<Location> neighboringLocations = world.getEmptySurroundingTiles(getLocation());
        return neighboringLocations.isEmpty() ? null : neighboringLocations.iterator().next();
    }

        public void setBurrow () {
            digBurrow();
            Target findBurrow = new Target(this.world, this.location, this);
            this.burrow = (Burrow) findBurrow.getBestTarget(Burrow.class);

        }


        protected void reproduce (World world){
            Random random = new Random();
            if (canReproduce && energy >= REPRODUCE_ENERGY_THRESHOLD) {
                Set<Location> emptyLocations = world.getEmptySurroundingTiles(getLocation());

                if (!emptyLocations.isEmpty()) {
                    Location randomEmptyLocation = new ArrayList<>(emptyLocations).get(random.nextInt(emptyLocations.size()));
                    world.setTile(randomEmptyLocation, new Game.Fox(world));
                    canReproduce = false;
                }
            }
        }

        @Override
        public Animal createChild () {
            return new Fox(world);
        }

        public void digBurrow () {
            if (hulProb <= 0 && !world.containsNonBlocking(this.getLocation())) {
                Burrow burrow1 = new Burrow(this.world);
                this.world.setTile(this.getLocation(), burrow1);
                burrow1.setLocation(this.getLocation());
                hulProb = 25;
            } else {
                rnd = new Random();
                hulProb = hulProb - rnd.nextInt(5);
            }
        }

        public void enterBurrow () {
            if (burrow == null) {
                setBurrow();
            }
            if (burrow != null) {
                Location burrowLocation = burrow.getLocation();
                if (this.world.isNight() && this.location.getX() == burrowLocation.getX() && this.location.getY() == burrowLocation.getY()) {
                    this.sleeping = true;
                    this.location = null;
                    this.world.remove(this);
                }
            }
        }


        public void exitBurrow () {
            if (this.world.isTileEmpty(burrow.getLocation())) {
                world.setTile(burrow.getLocation(), this);
                this.sleeping = false;
                this.location = burrow.getLocation();
            } else if (!this.world.isTileEmpty(burrow.getLocation())) {
                try {
                    Set<Location> neighbours = world.getEmptySurroundingTiles(this.location);
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
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }

        }


        @Override
        public DisplayInformation getInformation () {
            if (age < 10) {
                return new DisplayInformation(Color.ORANGE, "fox-small");
            }
            return new DisplayInformation(Color.ORANGE, "fox");
        }

    }



