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
    }

    /**
     * Tester om grasset kan placeres et tilfældigt sted (K1-1a.)
     */
    @Test
    public void grassSpawns() {
        grass.randomSpawnNonBlocking(grass, world);
        Location initLoc = world.getLocation(grass);
        assertNotNull(initLoc);
    }

    /**
     * Tester om grasset kan sprede sig (K1-1c.)
     */
    @Test
    public void grassSpreading() {
        grass.randomSpawnNonBlocking(grass, world);
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

    /**
     * Tester om grasset kan blive spist og forsvinde
     */

    @Test
    public void grassGetsEaten() {
        Location initLoc = new Location(1,1);
        world.setTile(initLoc, grass);
        assertTrue(world.containsNonBlocking(initLoc));

        grass.setHP(0);
        grass.act(world);



        assertFalse(world.containsNonBlocking(initLoc));

    }

    /**
     * Tester om dyr kan står på grasset uden der sker noget (K1-1d.)
     */
    @Test
    public void grassIsNonBlocking()  {

        Location sameLoc = new Location(1, 1);
        Rabbit rabbit = new Rabbit(world);

        world.setTile(sameLoc, rabbit);
        world.setTile(sameLoc, grass);

        Location rabbitLoc = world.getLocation(rabbit);
        Location burrowLoc = world.getLocation(grass);

        assertEquals(rabbitLoc, burrowLoc);

    }
}