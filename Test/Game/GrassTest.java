package Game;

import itumulator.world.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {
    World world;
    Grass grass;

    @BeforeEach
    void setUp() {
        world = new World(5);

        grass = new Grass(world);
        grass.randomSpawnNonBlocking(grass, world);
    }

    @Test
    public void grassSpreading() {
        Location initLoc = world.getLocation(grass);

        Set<Location> neighbours = world.getSurroundingTiles();
        ArrayList<Location> liste = new ArrayList<>(neighbours);


        for (int i = 0; i < 12; i++) {
            grass.act(world);
        }

        assertNotNull(world.getTile(initLoc));


    }

    @Test
    public void grassGetsEaten() {
        Rabbit rabbit = new Rabbit(world);
        rabbit.randomSpawn(rabbit, world);

    }

    @AfterEach
    void tearDown() {
    }
}