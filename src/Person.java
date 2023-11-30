import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Person implements Actor {
    private final Random random = new Random();

    @Override
    public void act(World world) {
        Set<Location> neighbours = world.getEmptySurroundingTiles();

        // Check if there are available tiles
        if (!neighbours.isEmpty()) {
            // Convert the set to an array and get the first location
            List<Location> locationList = new ArrayList<>(neighbours);
            Location targetLocation = locationList.get(random.nextInt(locationList.size()));
            world.move(this, targetLocation);

            if (world.isNight()) {
                world.delete(this);
            return;
            }
        /*System.out.println("I ain't doin’ nothin’!");
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        Location 1 = neighbours.toArray()[0];
        world.move(this, 1);
        */
        }
    }
}
