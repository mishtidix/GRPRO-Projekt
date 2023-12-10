package Game;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.util.*;
public abstract class Entity implements Actor, DynamicDisplayInformationProvider {
    protected World world;
    protected Location location;
    protected Random rnd;

    public Entity(World world) {
        this.world = world;
    }

    public void randomSpawn(Object object, World world) {
        rnd = new Random();
        int x = rnd.nextInt(world.getSize());
        int y = rnd.nextInt(world.getSize());
        location = new Location(x,y);
        while(!world.isTileEmpty(location)) {
            x = rnd.nextInt(world.getSize());
            y = rnd.nextInt(world.getSize());
            location = new Location(x, y);
        }
        if (world.isTileEmpty(location)) {
            this.location = location;
            world.setTile(location, this);
        }
    }

    public void randomSpawnNonBlocking(Object object, World world) {
        rnd = new Random();
        int x = rnd.nextInt(world.getSize());
        int y = rnd.nextInt(world.getSize());
        location = new Location(x,y);
        while(!world.containsNonBlocking(location)) {
            x = rnd.nextInt(world.getSize());
            y = rnd.nextInt(world.getSize());
            location = new Location(x, y);
        }
        if (world.isTileEmpty(location)) {
            this.location = location;
            world.setTile(location, this);
        }
    }
    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

