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
    }

    /**
     * Tester om ulve kan placeres tilfældigt
     */
    @Test
    public void wolfSpawns() { assertNotNull(world.getLocation(wolf));}

    /**
     * Tester om wolf kan bevæge sig om dagen (negativ test)
     */
    @Test
    public void wolfMovesDuringDay() {
        wolf.randomSpawn(wolf, world);
        wolf.setLocation(new Location(2, 2));

        world.setDay();
        wolf.act(world);

        assertTrue(wolf.getLocation() != null);

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
