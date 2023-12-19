package Game;

import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;

public class Bear extends Animal implements Actor, DynamicDisplayInformationProvider
{

    Location territory;
    public Bear(World world, Location current){

        super(world);

        this.territory = current;
    }
    public Bear(World world){ super(world); maxCount = 10; MaxHp = 50;}

    public void act(World world){
        super.act(world);
if (territory== null){
    territory = world.getLocation(this);
}
    }

    @Override
    public void move(World world){
        Path path = new Path(territory, location);
        Set<Location> neighbours = path.getPathAround(world);
        ArrayList<Location> list = new ArrayList<>(neighbours);
        Location l = list.get((int)(Math.random() * list.size()));
        this.location = l;
        world.move(this,l);
    }

    @Override
    protected void eat(World world) {

    }

    @Override
    public Animal createChild() {
        Bear babyBear = new Bear(world);
        world.add(babyBear);
        return babyBear;
    }


    public void eat(){
        if(!this.isFull){
            Set<Location> neighbours = world.getEmptySurroundingTiles(this.location);
            ArrayList<Location> list = new ArrayList<>(neighbours);
            ArrayList<Entity> prey = new ArrayList<>();
            for(Location l : list){
                if(world.getTile(l) instanceof Animal){

                }
            }
        }
    }

    public  int getCounter() {
        return maxCount;
    }

    public int getHealth() {
        return MaxHp;
    }

private boolean sleeping;

    @Override
    public boolean isSleeping (){
        return sleeping;
}

public void bearSleeps (World world) {
        if (world.isNight()) {
            sleeping = true;
        }      else {
            sleeping = false;
        }
}


    @Override
    public DisplayInformation getInformation(){
        if(age < 15){
            return new DisplayInformation(Color.GRAY, "bear-small");
        }
        return new DisplayInformation(Color.GRAY, "bear-large");
    }
}