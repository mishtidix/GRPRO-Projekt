package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;


public class Rabbit extends Animal implements Actor, DynamicDisplayInformationProvider
{
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
        /*
        if(world.isNight()){
        if(burrow == null){
        setBurrow();
        }
        moveGoal(burrow.getLocation());
        }else if(!isFull && !world.isNight()) {
        if(grass == null){
        setGrass();
        }
        if(grass != null){
        moveGoal(grass.getLocation());
        }else{
        move(world);
        }
        }else{
        move(world);
        }*/
        move(world);

    }

    public void setBurrow(){
        Target findBurrow = new Target(this.world, this.current, this);
        //this.burrow = (Burrow)
        findBurrow.getBestTarget(burrow);
    }

    public void setGrass(){
        Target findGrass = new Target(this.world, this.current, this);
        //this.grass = (Grass)
        findGrass.getBestTarget(grass);
    }

    public void eat(){
        super.eat(world);
        if(this.current == grass.getLocation()){
            isFull = true;
        }

    }

    @Override
    public DisplayInformation getInformation(){
        if(age < 10){
            return new DisplayInformation(Color.GRAY, "rabbit-small");
        }
        return new DisplayInformation(Color.GRAY, "rabbit-large");
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
        if(world.isNight() && this.getLocation().equals(burrow.getLocation())){
            this.sleeping = true;
            world.remove(this);
        }
    }

    public void exitBurrow(){
        if(world.getLocation(this)==null&& world.isDay()){
            if(!this.world.isTileEmpty(burrow.getLocation())){
                Set<Location> neighbours = world.getEmptySurroundingTiles(this.current);
                ArrayList<Location> list = new ArrayList<>(neighbours);
                Location l = list.get((int)(Math.random() * list.size()));
                world.setTile(l, this);
            }else{
                world.setTile(burrow.getLocation(), this);
            }
        }
    }
}
