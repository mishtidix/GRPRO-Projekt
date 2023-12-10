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
    protected int maxCount;
    protected int MaxHp;



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

    }

    public void move(World world) {

        this.location = world.getLocation(this);
        Set<Location> neighbours = world.getEmptySurroundingTiles(location);
        ArrayList<Location> list = new ArrayList<>(neighbours);
        if (!list.isEmpty()) {
            Location l = list.get((int) (Math.random() * list.size()));
            while (!world.isTileEmpty(l)) {
                l = list.get((int) (Math.random() * list.size()));
            }
            this.location = l;

            world.move(this, l);

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

    void die(World world, int Carcasshp) {
        if (!sleeping && location != null) {
            Location here = location;
            if (this.age >= MaxHp) {
                System.out.println(here);
                world.remove(this);
                world.setTile(here, new Carcass(world, Carcasshp));
                world.delete(this);
            }else if (this.count >= maxCount) {
                System.out.println(here);
                world.remove(this);
                world.setTile(here, new Carcass(world, Carcasshp));
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

