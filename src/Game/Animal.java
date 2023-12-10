package Game;
import itumulator.world.*;
import itumulator.executable.*;
import itumulator.simulator.*;
import java.awt.*;
import java.util.*;
public abstract class Animal extends Entity implements Actor,DynamicDisplayInformationProvider, Cloneable {
    public int age;
    public int count;
    public boolean isFull;
    public boolean canReproduce;
    public boolean sleeping;



    public Animal(World world){
        super(world);
        this.age=0;
        this.count = 0;
        this.isFull = false;
        this.canReproduce = false;
        this.sleeping = false;
    }

    @Override
    public void act(World world) {
        aging();
        if (!sleeping) {
            reproduce(world);
        }
        System.out.println(age);
    }

    public void move(World world){
        try{
        this.location = world.getLocation(this);
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        Location l = list.get((int)(Math.random() * list.size()));

            this.location = l;
            System.out.println(l);
            world.move(this,l);
        }catch(Exception e){

        }
    }
    public void moveGoal(Location goal){
        if (goal != null) {
            Path path = new Path(goal, this.location);
            Location best = path.getPath(this.world);
            this.location = best;
            world.move(this, best);
        }else {
            this.move(world);
        }
    }
    public void aging(){
        age++;
        if(this.age % 20 == 0){
            this.canReproduce = true;
        }

    }



    protected abstract void eat(World world);

    protected void reproduce(World world) {
        if (!sleeping) {
            if (this.canReproduce) {
                world.setCurrentLocation(this.getLocation());
                Set<Location> neighbours = world.getEmptySurroundingTiles();
                ArrayList<Location> list = new ArrayList<>(neighbours);
                if (!list.isEmpty()) {
                    Location l = list.get((int) (Math.random() * list.size()));


                    while (!world.isTileEmpty(l)) {
                        l = list.get((int) (Math.random() * list.size()));
                    }
                Animal child = createChild();
                world.setTile(l,child);


                    this.canReproduce = false;
                }
            }
        }
    }
    public abstract Animal createChild();

    void die(World world, int Maxhp) {
        if (!sleeping && location != null) {
            Location here = location;
            if (this.age >= 50) {
                world.remove(this);
                world.setTile(here, new Carcass(world, Maxhp));
                world.delete(this);
            }
            if (this.count >= 30) {
                world.remove(this);
                world.setTile(here, new Carcass(world, Maxhp));
                world.delete(this);
            }
        }
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public DisplayInformation getInformation(){
        return  new DisplayInformation(Color.cyan);
    }

    @Override
    public Animal clone() {
        try {
            Animal clone = (Animal) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
