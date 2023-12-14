package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;


public class Rabbit extends Animal implements Actor, DynamicDisplayInformationProvider {
    Burrow burrow;
    int i = 0;
    Grass grass;
    int burrowProb;
    public Rabbit(World world){
        super(world);
        this.burrowProb = 25;
        maxCount = 25;
        MaxHp = 35;
    }

    @Override
    public void act(World world) {
        super.act(world);
        i++;
        System.out.println(i);
        if (world.isDay() && sleeping){
            exitBurrow();
            System.out.println(this.location);
        }
        if(!(location==null)){
            eat(world);
        }

        if (world.isNight() && !sleeping) {
            enterBurrow();
            if (!sleeping) {
                if(burrow !=null) {
                    moveGoal(burrow.getLocation());
                } else {
                    move(world);
                }
            }
        } else if (!isFull && !sleeping && location!=null) {
            if (grass==null){
                setLocation(world.getLocation(this));
                setGrass();
            }
            if (grass!= null) {
                moveGoal(grass.getLocation());
            }else {
                move(world);
            }
        } else{
            if (world.isDay()) {
                move(world);

            }
        }

        die(world, 50);
    }

    public int getHealth() {
        return MaxHp;
    }


    public void setBurrow(){
        digBurrow();
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

    public Animal createChild(){ return new Rabbit(world);}


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
            Location burrowLocation = burrow.getLocation();
            if (this.world.isNight() && this.location.getX() == burrowLocation.getX() && this.location.getY() == burrowLocation.getY()) {
                this.sleeping = true;
                this.location = null;
                this.world.remove(this);
            }
        }
        }


    public void exitBurrow(){
        if(this.world.isTileEmpty(burrow.getLocation())) {
            world.setTile(burrow.getLocation(), this);
            this.sleeping=false;
            this.location = burrow.getLocation();
        }else if(!this.world.isTileEmpty(burrow.getLocation())){
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
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            }

    }

    public void setMaxHP () {
        MaxHp = 0;
    }


}
