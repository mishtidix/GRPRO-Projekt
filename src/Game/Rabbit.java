package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;


public class Rabbit extends Animal implements Actor, DynamicDisplayInformationProvider {
    Burrow burrow;
    Grass grass;
    int burrowProb;
    public Rabbit(World world){
        super(world);
        this.burrowProb = 25;
    }

    @Override
    public void act(World world) {
        super.act(world);
        if (world.isDay() && this.sleeping){
            exitBurrow();
        }
        if(!(location==null)){
            eat(world);
        }

        if (world.isNight() && !sleeping) {
            enterBurrow();
            if (!sleeping) {
    moveGoal(burrow.getLocation());
}
        } else if (!isFull && !sleeping) {
            if (grass==null){
                setLocation(world.getLocation(this));
                setGrass();
            }
                moveGoal(grass.getLocation());

                } else{
            if (world.isDay()) {
                this.sleeping =false;
                move(world);

            }
        }

        die(world, 50);
    }

    public void setBurrow(){
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
Location grassLocation=grass.getLocation();
        if(this.location.getX() == grassLocation.getX() && this.location.getY() == grassLocation.getY()){
            isFull = true;
            this.count = 0;
        }
        if(count>10){
            isFull = false;
        }
System.out.println("count" +count);
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
        if(burrowProb <= 0){
            this.world.setTile(this.getLocation(),new Burrow(this.world));
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
            Location burrowLocation = burrow.getLocation();
            if (this.world.isNight() && this.location.getX() == burrowLocation.getX() && this.location.getY() == burrowLocation.getY()) {
                this.sleeping = true;
                this.world.remove(this);
            }
        }


    public void exitBurrow(){
        if(this.world.isTileEmpty(burrow.getLocation())) {
            world.setTile(burrow.getLocation(), this);
        }else if(!this.world.isTileEmpty(burrow.getLocation())){
                Set<Location> neighbours = world.getEmptySurroundingTiles(this.location);
                if (!neighbours.isEmpty()) {
                    ArrayList<Location> list = new ArrayList<>(neighbours);
                    Location l = list.get((int) (Math.random() * list.size()));
                    world.setTile(l, this);
                }
            }else{

            }

    }


}
