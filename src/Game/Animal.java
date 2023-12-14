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
    public void aging(){
        age++;
        if(this.age % 20 == 0 && this.age<=30){
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
                child.setLocation(l);


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

    protected  void kill(World world, Animal prey){
        world.delete(prey);
        this.count =0;
        this.isFull= true;
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


}

