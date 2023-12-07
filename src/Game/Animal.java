package Game;
import itumulator.world.*;
import itumulator.executable.*;
import itumulator.simulator.*;
import java.awt.*;
import java.util.*;
public class Animal extends Entity implements Actor,DynamicDisplayInformationProvider {
    public int age;
    public int count;
    public boolean isFull;
    public boolean canReproduce;
    public boolean sleeping;

    public World world;

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
        //reproduce(world);
        eat(world);

        System.out.println(age);
    }

    public void move(World world){
        this.location=world.getLocation(this);

        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        try{
            Location l = list.get((int)(Math.random() * list.size())); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
            this.location = l;
            world.move(this,l);
        } catch(Exception e){

        }
    }

    public void moveGoal(Location goal){
        Path path = new Path(goal, this.location);
        Location best= path.getPath(world);
        this.location = best;
        world.move(this,best);
    }

    public void aging(){
        age++;
        if(this.age % 20 == 0){
            this.canReproduce = true;
        }

    }

    void reproduce(World world){
        Random r = new Random();

        if(this.canReproduce){
            int x = r.nextInt(5);
            int y = r.nextInt(5);
            Location l = new Location(x,y);

            while(!world.isTileEmpty(l)) {
                x = r.nextInt(5);
                y = r.nextInt(5);
                l = new Location(x,y);
            }

            world.setTile(l, new Animal(this.world));
            this.canReproduce = false;
        }
    }

    public void getHungry() {
        // 
    }

    void eat(World world){
        try{
            if(world.containsNonBlocking(this.location)){
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
        if(this.age >= 100){
            world.delete(this);
        }
        if(this.count== 20){
            world.delete(this);
        }
    }

    public int getAge(){
        return this.age;
    }

    @Override
    public DisplayInformation getInformation(){
        return  new DisplayInformation(Color.cyan);
    }

}
