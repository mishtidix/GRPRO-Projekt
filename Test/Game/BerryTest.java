package Game;
import itumulator.world.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class BerryTest {
    World world;
    Berry berry;
    int i;

    @BeforeEach
    void setUp() {
        world = new World(5);
        berry = new Berry(world);
    }

    /**
     * Tester om berries kan placeres et tilfældigt sted
     */
    @Test
    public void berrySpawns() {
        berry.randomSpawnNonBlocking(berry, world);
        Location initLoc = world.getLocation(berry);
        assertNotNull(initLoc);}

    /**
     * Tester om berries kan gro med tiden
     * */
    @Test
    public void berryGrows() {
        berry.randomSpawnNonBlocking(berry, world);

        assertEquals(0, berry.getAge());
        assertEquals(0, berry.getHP());

        for (int i = 0; i < 15; i++) {
            berry.act(world);
        }
        assertEquals(15,berry.getAge());
        assertEquals(100, berry.getHP());

        for (int i = 0; i < 5; i++) {
            berry.act(world);
        }
        assertEquals(20, berry.getAge());
        assertEquals(100, berry.getHP());
    }

    /**
     * Tester om berries kan blive spist
     */
    @Test
    public void berryEaten () {
        berry.randomSpawnNonBlocking(berry,world);

        assertEquals(0, berry.getAge());
        assertEquals(0, berry.getHP());

        for (int i = 0; i < 15; i++) {
            berry.act(world);
        }

        assertEquals(15, berry.getAge());
        assertEquals(100, berry.getHP());

        assertTrue(berry.ReadyToBeEaten());

        berry.beenEaten();

        assertEquals(90, berry.getHP());
        assertEquals(0, berry.getAge());
    }
}