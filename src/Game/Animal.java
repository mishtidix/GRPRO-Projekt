package Game;
import itumulator.world.*;
import itumulator.executable.*;
import itumulator.simulator.*;
import java.awt.*;
import java.util.*;
public class Animal extends Entity implements Actor,DynamicDisplayInformationProvider, Cloneable {
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
        Path path = new Path(goal, this.location);
        Location best= path.getPath(this.world);
        this.location = best;
        world.move(this,best);
    }
    public void aging(){
        age++;
        if(this.age % 20 == 0){
            this.canReproduce = true;
        }

    }



    void eat(World world){
        try{

        }catch (Exception e){

        }
    }

    void die(World world){
        if(this.age >= 25){
            world.delete(this);
        }
        if(this.count>= 20){
            world.delete(this);
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
