package Game;
import itumulator.world.*;
import itumulator.executable.*;
import itumulator.simulator.*;
import java.awt.*;
import java.util.*;
public class Animal implements Actor {
    public int age;
    public int count;
    public boolean isFull;
    public boolean canReproduce;
    public boolean sleeping;
    public Location current;
    public World world;

    Animal(){
        this.age=0;
        this.count = 0;
        this.isFull = false;
        this.canReproduce = false;
        this.sleeping = false;
    }

    @Override
    public void act(World world) {
        aging();
        reproduce(world);
        eat(world);
        move(world);
        die(world);
        System.out.println(age);
    }

    public void move(World world){
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        try{
            Location l = list.get((int)(Math.random() * list.size())); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
            this.current = l;
            world.move(this,l);
        }catch(Exception e){

        }
    }

    public void aging(){
        age++;
        if(this.age % 20 == 0){
            this.canReproduce = true;
        }

    }

    void reproduce(World world){
        Random r = new Random();

        if(this.canReproduce == true){
            int x = r.nextInt(5);
            int y = r.nextInt(5);
            Location l = new Location(x,y);

            while(!world.isTileEmpty(l)) {
                x = r.nextInt(5);
                y = r.nextInt(5);
                l = new Location(x,y);
            }

            world.setTile(l, new Animal());
            this.canReproduce = false;
        }
    }

    void eat(World world){
        try{
            if(world.containsNonBlocking(this.current)){
                isFull = true;
                this.count = 0;
                System.out.println("isfull");
            } else{
                count++;
                System.out.println("count:" + count);
            }
        }catch (Exception e){

        }
    }

    void die(World world){
        if(this.age >= 25){
            world.delete(this);
        }
        if(this.count== 20){
            world.delete(this);
        }
    }

    public int getAge(){
        return this.age;
    }
}
