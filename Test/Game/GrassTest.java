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
    public void grassSpawns() {
        Location initLoc = world.getLocation(grass);
        assertNotNull(initLoc);
    }

    @Test
    public void grassSpreading() {
        Location initLoc = world.getLocation(grass);

        grass.setSpreadCooldown();
        world.setCurrentLocation(initLoc);
        grass.spread();

        Set<Location> neighbours = world.getSurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);

        for (int i = 0; i < list.size(); i++) {
            if (!world.containsNonBlocking(list.get(i))) {
                list.remove(list.get(i));
            }
        }

        assertFalse(list.isEmpty());
        assertNotNull(initLoc);
    }

    @Test
    public void grassGetsEaten() {
        Location initLoc = world.getLocation(grass);

        grass.setHP();
        grass.beenEaten();

        Location gotEaten = world.getLocation(grass);

        assertNotNull(initLoc);
        assertNull(gotEaten);



    }

    @AfterEach
    void tearDown() {
    }
}