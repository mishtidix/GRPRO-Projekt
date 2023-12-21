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
    /**
     * first calls digBurrow, then uses target to find an existing burrow in the world
     */

    public void setBurrow(){
        digBurrow();
        this.location=world.getLocation(this);
        Target findBurrow = new Target(this.world, this.location, this);
        this.burrow = (Burrow)findBurrow.getBestTarget(Burrow.class);

    }
    /**
     * Using the Target class, this method finds and set grass to a grass that exist in the world.
     */

    public void setGrass(){
        Target findGrass = new Target(this.world, this.location, this);
        this.grass = (Grass)findGrass.getBestTarget(Grass.class);
    }
    /**
     * When this method is called, first the Rabbit checks to see if grass is null, if it is then it calls the method setGrass
     * afterward, count is counted upward, and then if grass is no longer null and then if the location of grass and the location of Rabbit is the same
     * then isFull becomes true beenEaten from grass is called and count is reduced to zero.
     * @param world
     */

    public void eat(World world){
        if (grass == null){
            setGrass();
        }
        count++;
        if (grass != null) {
            Location grassLocation = grass.getLocation();
            if (this.location.getX() == grassLocation.getX() && this.location.getY() == grassLocation.getY()) {
                isFull = true;
                this.grass.beenEaten(world);
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
    /**
     * returns a new Rabbit when reproduce in Animal calls createChild
     * @return
     */

    public Animal createChild(){
        return new Rabbit(world);

    }
    /**
     * makes a new burrow if the burrowProb is equal or below 0, if not then it reduce burrowProb by a random number.
     * when making a new burrow, it takes the location of the Rabbit and using world.setTile then sets the new burrow to said the location.
     */
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
    /**
     * The enterBurrow method, makes a Rabbit remove it self from the map and set sleeping to true if it's location to null
     * if it doesnt have a burrow to begin with then it calls the method setburrow to find a burrow
     */

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

    /**
     * This method is called when a Rabbit needs to exit its burrow
     * its uses the getEmptySurroundingTiles method from world to get the empty tiles around the burrow
     * then set the Rabbits location to one of those empty tiles
     */


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
    public void setCountToZero () {
        maxCount = 0;
    }

    public void setCountTo25() {
        count = 25;
    }

    public void setBurrowProbToZero() {
        burrowProb = 0;
    }
    public int getCounter() {
        return count;
    }

    public int getMaxCounter () {
        return maxCount;
    }

    public int getHealth() {
        return MaxHp;
    }
}



