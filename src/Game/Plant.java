package Game;
import java.time.*;

import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;
public abstract class Plant extends Entity {
    public Location location;
    public int age;
    public LocalDateTime plantedDateTime;
    public World world;

    public Plant(Location location, int age, World world) {
        super(world);
        this.location = location;
        this.age = age;
        this.plantedDateTime = LocalDateTime.now();
    }

    public Location getLocation() {
        return location;
    }

    public int getAge() {
        return age;
    }

    public void die() { //hvis en plant eksisterer i længere end 20 world.days, så bliver den deleted fra world
        int currentTime = world.getCurrentTime();
        int daysPassed = currentTime / World.getTotalDayDuration();
        int dayTimeTicks = currentTime % World.getTotalDayDuration();

        if (daysPassed >= 20 && dayTimeTicks >= World.getDayDuration()) { // Logic for plant death
            world.delete(this);
        }

    }

}

