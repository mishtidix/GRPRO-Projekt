package Game;

import itumulator.world.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RabbitTest {
    World world;
    Rabbit rabbit;

    @BeforeEach
    void setUp() {
        world = new World(5);

        rabbit = new Rabbit(world);
        rabbit.randomSpawn(rabbit, world);
    }

    @Test
    public void rabbitMovesDuringDay() {
        Location initLoc = world.getLocation(rabbit);
        world.setDay();

        rabbit.act(world);

        Location moved = world.getLocation(rabbit);
        assertNull(world.getTile(initLoc));
        assertNotNull(world.getLocation(rabbit));
        assertNotEquals(initLoc, moved);
    }

    @Test
    public void rabbitEntersBurrow() {
        Burrow burrow = new Burrow(world);
        burrow.randomSpawn(burrow, world);
        Location burrowLoc = world.getLocation(burrow);

        for (int i = 0; i < 12; i++) {
            rabbit.act(world);
        }

        Location sleepingRabbit = world.getLocation(rabbit);
        //assertNull(sleepingRabbit);

        assertEquals(burrowLoc, sleepingRabbit);



    }

    @Test
    public void rabbitExitsBurrow() {
        Burrow burrow = new Burrow(world);
        burrow.randomSpawn(burrow, world);

        for (int i = 0; i < 15; i++) {
            rabbit.act(world);
        }

    }



    @AfterEach
    void tearDown() {
        //
    }
}