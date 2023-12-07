package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;


public class Rabbit extends Animal implements Actor, DynamicDisplayInformationProvider {
    Burrow burrow;
    Grass grass;
    int burrowProb;
    public Rabbit(World world){
        super(world);
        this.burrowProb = 25;
    }

    @Override
    public void act(World world) {
        super.act(world);
        if (world.isDay() && this.sleeping){
            exitBurrow();
        }
        if(!(location==null)){
            eat(world);
        }

System.out.println("count:"+count);
        if (world.isNight() && !sleeping) {
            enterBurrow();
            if (!sleeping) {
    moveGoal(burrow.getLocation());
}
        } else if (!isFull && !sleeping) {
                moveGoal(grass.getLocation());
                } else{
            if (world.isDay()) {
                this.sleeping =false;
                move(world);

            }
        }

        super.die(world);
    }

    public void setBurrow(Burrow burrow){
        this.burrow = burrow;
        //Target findBurrow = new Target(this.world, this.location, this);
        //this.burrow = (Burrow)
        //findBurrow.getBestTarget(burrow);*/
    }

    public void setGrass(Grass g){
        this.grass = g;

        //Target findGrass = new Target(this.world, this.location, this);
        //this.grass = (Grass)
        //findGrass.getBestTarget(grass);
    }

    public void eat(World world){
count++;
Location grassLocation=grass.getLocation();
        if(this.location.getX() == grassLocation.getX() && this.location.getY() == grassLocation.getY()){
            isFull = true;
            this.count = 0;
        }
        if(count>10){
            isFull = false;
        }

    }

    @Override
    public DisplayInformation getInformation(){
        if(age < 10){
            return new DisplayInformation(Color.GRAY, "rabbit-small");
        }
        return new DisplayInformation(Color.GRAY, "rabbit-large");
    }

    public void digBurrow(){
        if(burrowProb <= 0){
            this.world.setTile(this.getLocation(),new Burrow(this.world));
            burrowProb = 25;
        } else{
            rnd = new Random();
            burrowProb = burrowProb - rnd.nextInt(5);
        }
    }

    public void enterBurrow(){
        Location burrowLocation = burrow.getLocation();
        if(this.world.isNight() && this.location.getX() ==burrowLocation.getX() && this.location.getY() == burrowLocation.getY()){
            this.sleeping = true;
            this.world.remove(this);
        }
    }

    public void exitBurrow(){

            if(!this.world.isTileEmpty(burrow.getLocation())){
                Set<Location> neighbours = world.getEmptySurroundingTiles(this.location);
                ArrayList<Location> list = new ArrayList<>(neighbours);
                Location l = list.get((int)(Math.random() * list.size()));
                world.setTile(l, this);
            }else{
                world.setTile(burrow.getLocation(), this);
            }

    }
    protected void die(World world){
        Location here = location;
        super.die(world);
        world.setTile(here, new Carcass(world,25));
    }
    protected void reproduce(World world)  {

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


            world.setTile(l,new Rabbit(world));

            this.canReproduce = false;
        }
    }
}
