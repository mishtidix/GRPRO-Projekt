package Game;
import java.time.*;

import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;
public abstract class Plant extends Entity {

    public int age;
    protected int HP;
    public LocalDateTime plantedDateTime;

    public Plant( World world) {
        super(world);

        this.age = 0;
        this.plantedDateTime = LocalDateTime.now();
    }

    public Location getLocation() {
        return location;
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

    }



