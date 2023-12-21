package Game;
import itumulator.world.*;
import itumulator.executable.*;
import itumulator.simulator.*;
import java.awt.*;
import java.util.*;
public abstract class Animal extends Entity implements Actor,DynamicDisplayInformationProvider {
    public int age;
    public int count;
    public boolean isFull;
    public boolean canReproduce;
    public boolean sleeping;
    protected int maxCount;
    protected int MaxHp;
    protected ArrayList<Class> Predators;
    protected ArrayList<Class> Prey;



    public Animal(World world){
        super(world);
        this.age=0;
        this.count = 0;
        this.isFull = false;
        this.canReproduce = false;
        this.sleeping = false;
        Predators = new ArrayList<>();
        Prey=new ArrayList<>();
    }

    @Override
    public void act(World world) {
        aging();
        if (!sleeping) {
            reproduce(world);
        }

    }

    public void move(World world) {
        if( world.getEntities().containsKey(this)){
            this.location = world.getLocation(this);
            Set<Location> neighbours = world.getEmptySurroundingTiles(location);
            ArrayList<Location> list = new ArrayList<>(neighbours);
            if (!list.isEmpty()) {
                Location l = list.get((int) (Math.random() * list.size()));
                while (!world.isTileEmpty(l) && !safeForPredators(world, l)) {
                    l = list.get((int) (Math.random() * list.size()));
                }
                this.location = l;

                world.move(this, l);
            }
        }
    }
    /**
     * moveGoal is like move, in that the point is to move the animal, this just uses our homemade class Path to find the best way to move to specific location
     * @param goal
     */

    public void moveGoal(Location goal){
        if (world.getEntities().containsKey(this)) {
            if (goal != null) {
                Path path = new Path(goal, this.location);
                Location best = path.getPath(this.world);
                this.location = best;
                world.move(this, best);
            } else {
                this.move(world);
            }
        }
    }
    /**
     * This method simply increase the animals age, and makes it possible for it to reproduce
     */

    public void aging(){
        age++;
        if(this.age % 20 == 0 && this.age<=30){
            this.canReproduce = true;
        }

    }



    protected abstract void eat(World world);
    /**
     * This method makes another animal if reproduce is true while sleeping is not true then using some of the same methods as Animals move,
     * we find an empty space and put a new instance of animal there using the abstract method createChild on that empty space.
     * @param world
     */

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
                    child.setLocation(l);


                    this.canReproduce = false;
                }
            }
        }
    }
    /**
     * an abstract method where each subclass of animal can insert a different return value
     * @return
     */

    public abstract Animal createChild();

    /**
     *This method simply deletes the current animal and sets in its place a carcass that is aproproiate to the animal
     * @param world
     * @param Carcasshp
     */

    void die(World world, int Carcasshp) {
        if (!sleeping && location != null) {
            Location here = location;
            if (this.age >= MaxHp) {
                world.remove(this);
                world.setTile(here, new Carcass(world, Carcasshp));
                world.delete(this);
            }else if (this.count >= maxCount) {
                world.remove(this);
                world.setTile(here, new Carcass(world, Carcasshp));
                world.delete(this);
            }
        }
    }



    private boolean safeForPredators(World world, Location tile){
        Set<Location> set = world.getSurroundingTiles(tile);
        ArrayList<Location> list = new ArrayList<>(set);
        ArrayList<Location> checkedList = new ArrayList<>();
        for (int i=0 ; i<list.size(); i++){
            if (!world.isTileEmpty(list.get(i))){
                checkedList.add(list.get(i));
            }
        }
        for (int i = 0; i<checkedList.size(); i++){
            Object object = world.getTile(checkedList.get(i));
            for (Class o : Predators) {
                if (object.getClass()==o){
                    return false;
                }
            }
        }
        return  true;
    }



    public boolean isSleeping() {
        return sleeping;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCanReproduce (boolean canReproduce) {
        this.canReproduce = true;
    }


    @Override
    public DisplayInformation getInformation(){
        return  new DisplayInformation(Color.cyan);
    }


}

