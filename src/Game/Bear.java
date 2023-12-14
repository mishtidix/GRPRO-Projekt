package Game;

import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;

public class Bear extends Animal implements Actor, DynamicDisplayInformationProvider
{
    public Location territory;
    public Entity food;
    public ArrayList<Class> foods;

    public Animal prey;
    public Bear(World world, Location current){

        super(world);
        maxCount= 70;
        MaxHp= 100;
        foods = new ArrayList<>();
        Prey.add(Rabbit.class);
        Prey.add(Wolf.class);
        foods.add(Berry.class);
        foods.add(Carcass.class);
        this.territory = current;
    }
    public Bear(World world){
        super(world);
        maxCount= 70;
        MaxHp= 100;
        foods = new ArrayList<>();
        Prey.add(Rabbit.class);
        Prey.add(Wolf.class);
        foods.add(Berry.class);
        foods.add(Carcass.class);
    }

    public void act(World world){
        super.act(world);
if (territory== null){
    territory = world.getLocation(this);
}
eat(world);
if (count>=50){
    Hunt();
} else if(count<50 && count>= 15){
    if (food== null){
        findFood();
    }
    moveGoal(food.getLocation());
}
    }

    @Override
    public void move(World world){
            Path path = new Path(territory, location);
            Set<Location> neighbours = path.getPathAround(world);
            ArrayList<Location> list = new ArrayList<>(neighbours);
            Location l = list.get((int) (Math.random() * list.size()));
            this.location = l;
            world.move(this, l);

    }
public void Hunt(){
        if (prey == null){
        Target findPrey = new Target(world,location, this);
prey =(Animal) findPrey.getBestTarget(Prey);
        }
        moveGoal(prey.getLocation());
    Set<Location> neighbours = world.getSurroundingTiles();
    ArrayList<Location> list = new ArrayList<>(neighbours);
    for (Location l : list){
        if (world.getTile(l) == prey){
            kill(world,prey);
        }
    }
}
public void findFood(){
        Target findFood = new Target(world,location,this);
        food = (Entity) findFood.getBestTarget(foods);
}
    @Override
    protected void eat(World world) {
Set<Location> neighbours = world.getSurroundingTiles();
ArrayList<Location> list = new ArrayList<>(neighbours);
for (Location l : list){
    if (world.getTile(l) == food){
        if (food.getClass()== Berry.class){
            count -= 15;
            if (count<= 35){
                isFull= false;
            }
        } else {
            count=0;
            isFull = false;
        }
    }
}
    }

    @Override
    public Animal createChild() {
        return new Bear(world);
    }



    @Override
    public DisplayInformation getInformation(){
        if(age < 15){
            return new DisplayInformation(Color.GRAY, "bear-small");
        }
        return new DisplayInformation(Color.GRAY, "bear-large");
    }
}