package Game;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.util.*;
public abstract class Entity implements NonBlocking, Actor, DynamicDisplayInformationProvider {
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
        if (world.isTileEmpty(location)) {
            world.setTile(location, this);
        }
    }
}
