package Game;
import itumulator.world.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class DenTest {
    World world;
    Den den;

    @BeforeEach
    void setUp() {
        world = new World(5);
        den = new Den(world);
    }

    /**
     * Tester om ulvehuller kan placeres et tilfældigt sted
     */
    @Test
    public void denSpawns() {
        den.randomSpawnNonBlocking(den, world);

        Location initLoc = world.getLocation(den);
        assertNotNull(initLoc);
    }

    /**
     * Tester om nonblocking virker
     */
    @Test
    public void denIsNonBlocking () {
        Location sameloc = new Location(1, 1);
        Wolf wolf = new Wolf(world);

        world.setTile(sameloc, wolf);
        world.setTile(sameloc,den);

        Location wolfLoc = world.getLocation(wolf);
        Location denLoc = world.getLocation(den);

        assertEquals(wolfLoc, denLoc);
    }
}