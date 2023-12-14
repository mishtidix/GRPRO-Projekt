package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;


public class Rabbit extends Animal implements Actor, DynamicDisplayInformationProvider {
    private Burrow burrow;
    private Grass grass;
    private int burrowProb;
    private boolean theEnd;
    public Rabbit(World world){
        super(world);
        this.burrowProb = 25;
        maxCount = 20;
        MaxHp = 45;
        Predators.add(Wolf.class);
        Predators.add(Bear.class);

    }

    @Override
    public void act(World world) {
        if (world.getEntities().containsKey(this)) {
            super.act(world);

            if (world.isDay() && sleeping) {
                exitBurrow();

            }
            if (!(location == null)) {
                eat(world);
            }

            if (world.isNight() && !sleeping) {
                enterBurrow();
                if (!sleeping) {
                    if (burrow != null) {
                        moveGoal(burrow.getLocation());
                    } else {
                        move(world);
                    }
                }
            } else if (!isFull && !sleeping && location != null) {
                if (grass == null) {
                    setLocation(world.getLocation(this));
                    setGrass();
                }
                if (grass != null) {
                    moveGoal(grass.getLocation());
                } else {
                    move(world);
                }
            } else {
                if (world.isDay() && location != null) {
                    move(world);

                }
            }

            die(world, 50);
        }
    }

    public void setBurrow(){
        digBurrow();
        this.location=world.getLocation(this);
        Target findBurrow = new Target(this.world, this.location, this);
        this.burrow = (Burrow)findBurrow.getBestTarget(Burrow.class);

    }

    public void setGrass(){
        Target findGrass = new Target(this.world, this.location, this);
        this.grass = (Grass)findGrass.getBestTarget(Grass.class);
    }

    public void eat(World world){
        if (grass == null){
            setGrass();
        }
        count++;
        if (grass != null) {
            Location grassLocation = grass.getLocation();
            if (this.location.getX() == grassLocation.getX() && this.location.getY() == grassLocation.getY()) {
                isFull = true;
                this.grass.beenEaten();
                this.count = 0;
            }
        }
        if(count>10){
            isFull = false;
        }

    }

    @Override
    public DisplayInformation getInformation(){
        if(age < 10){
            return new DisplayInformation(Color.GRAY, "rabbit-small");
        }
        return new DisplayInformation(Color.GRAY, "rabbit-large");
    }

    public Animal createChild(){
        return new Rabbit(world);

    }

    public void digBurrow(){
        if(burrowProb <= 0 && !world.containsNonBlocking(this.getLocation())){
            Burrow burrow1 =new Burrow(this.world);
            this.world.setTile(this.getLocation(),burrow1);
            burrow1.setLocation(this.getLocation());
            burrowProb = 25;
        } else{
            rnd = new Random();
            burrowProb = burrowProb - rnd.nextInt(5);
        }
    }

    public void enterBurrow(){
        if(burrow == null) {
            setBurrow();
        }
        if (burrow != null) {
            digBurrow();
            Location burrowLocation = burrow.getLocation();
            if (this.world.isNight() && this.location.getX() == burrowLocation.getX() && this.location.getY() == burrowLocation.getY()) {
                this.sleeping = true;
                this.location = null;
                this.world.remove(this);
            }
        }
    }



    public void exitBurrow(){

        Set<Location> neighbours = world.getEmptySurroundingTiles(this.burrow.getLocation());
        if (!neighbours.isEmpty()) {
            ArrayList<Location> list = new ArrayList<>(neighbours);
            Location l = list.get((int) (Math.random() * list.size()));
            while (!world.isTileEmpty(l)) {
                l = list.get((int) (Math.random() * list.size()));
            }
            world.setTile(l, this);
            this.location = l;
            this.sleeping = false;
        }

    }

}



