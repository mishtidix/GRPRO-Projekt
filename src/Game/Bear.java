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
        super(world, curr);
        this.territory = current;
    }

    public void act(World world){
        super.act(world);

    }

    @Override
    public void move(World world){
        Path path = new Path(territory, current);
        Set<Location> neighbours = path.getPathAround(world);
        ArrayList<Location> list = new ArrayList<>(neighbours);
        Location l = list.get((int)(Math.random() * list.size()));
        this.current = l;
        world.move(this,l);
    }

    @Override
    public void eat(){
        super.eat();
        if(!this.isFull){
            Set<Location> neighbours = world.getEmptySurroundingTiles(this.current);
            ArrayList<Location> list = new ArrayList<>(neighbours);
            ArrayList<> prey = new ArrayList<>();
            for(Location l : list){
                if(world.getTile(l) instanceof Animal){

                }
            }
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
