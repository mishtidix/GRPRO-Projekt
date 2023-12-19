package Game;

import itumulator.world.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BurrowTest {
    World world;
    Burrow burrow;

    @BeforeEach
    void setUp() {
        world = new World(5);

        burrow = new Burrow(world);
    }

    /**
     * Tester om kaninhuller kan placeres et tilfældigt sted (K1-3a.)
     */
    @Test
    public void burrowSpawns() {
        burrow.randomSpawnNonBlocking(burrow, world);

        Location initLoc = world.getLocation(burrow);
        assertNotNull(initLoc);
    }

    /**
     * Tester om dyr kan står på kaninhuler uden der sker noget (K1-3b.)
     */

    @Test
    public void burrowIsNonBlocking()  {
        Location sameLoc = new Location(1, 1);
        Rabbit rabbit = new Rabbit(world);

        world.setTile(sameLoc, rabbit);
        world.setTile(sameLoc, burrow);

        Location rabbitLoc = world.getLocation(rabbit);
        Location burrowLoc = world.getLocation(burrow);

        assertEquals(rabbitLoc, burrowLoc);

    }

}