package Game;
import java.time.*;

import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;
public abstract class Plant extends Entity implements NonBlocking, Actor, DynamicDisplayInformationProvider {
    protected Location location;
    protected int age;
    protected int HP;
    protected LocalDateTime plantedDateTime;

    public Plant(World world) {
        super(world);
        //this.location = location;
        this.age = 0;
        this.plantedDateTime = LocalDateTime.now();
    }

    public Location getLocation() {
        return location;
    }

    public int getHP() {
        return HP;
    }

    public int getAge() {
        return age;
    }

    public void aging() {
            age++;
    }

    public void die() { //used to delete the plant if it gets eaten?
            world.delete(this);
        }



    @Override
    public void act(World world) {

    }

    @Override
    public DisplayInformation getInformation(){
        return  new DisplayInformation(Color.cyan);
    }

}


