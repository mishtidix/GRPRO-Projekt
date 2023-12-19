package Game;
import itumulator.world.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WolfTest {
    private World world;
    private Wolf wolf;

    @BeforeEach
    void setUp() {
        world = new World(5);
        wolf = new Wolf(world);
        wolf.randomSpawn(wolf, world);
    }

    @Test
    public void wolfSpawns() { assertNotNull(world.getLocation(wolf));}

    @Test
    public void wolfMovesDuringDay() {
        Location initialLocation = world.getLocation(wolf);
        wolf.setLocation(initialLocation);

        // Simulate the world state during the day
        world.setDay();
        wolf.act(world);

        // Assert that the wolf has moved
        Location moved = world.getLocation(wolf);
        assertNull(world.getTile(initialLocation));
        assertNotNull(world.getLocation(wolf));
        assertNotEquals(initialLocation, moved);
    }

    @Test
    public void wolfDies() {
        /*
        assertNotNull(world.getLocation(wolf));
        wolf.die(world, 50);
        assertNull(wolf);

         */
    }

    @Test
    public void wolfEatsToGainHP () {
        Rabbit rabbit = new Rabbit(world);
        wolf.randomSpawnNonBlocking(rabbit, world);
        //int beforeEat = wolf.getCounter();
    }
}
