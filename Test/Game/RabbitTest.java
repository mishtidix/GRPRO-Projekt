package Game;

import itumulator.world.*;
import org.junit.jupiter.api.*;

import java.util.*;

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
    public void rabbitSpawns() {
        assertNotNull(world.getLocation(rabbit));
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
    /*
    @Test
    public void rabbitDies() {
        assertNotNull(world.getLocation(rabbit));

        rabbit.setMaxHP();
        rabbit.act(world);

        assertNull(world.getLocation(rabbit));
    }

    @Test
    public void rabbitEatsAndGainsHP() {
        rabbit.setMaxHP();
        Grass grass = new Grass(world);
        grass.randomSpawn(grass, world);
        rabbit.act(world);
        int newHP = rabbit.getHealth();
        assertTrue(newHP > initialHP);
    }

    */


    @Test
    public void rabbitAges() {
        int initAge = rabbit.getAge();

        rabbit.act(world);
        int newAge = rabbit.getAge();
        assertTrue(newAge > initAge);
    }

    @Test
    public void rabbitCanReproduce() {
        
        assertTrue(rabbit.reproduce(););
        /*
        rabbit.reproduce(world);
        Location rabbitLoc = world.getLocation(rabbit);
        Set<Location> emptySurroundingTiles = world.getEmptySurroundingTiles(rabbitLoc);
        assertFalse(emptySurroundingTiles.isEmpty());
        Location babyrabbitLoc = emptySurroundingTiles.iterator().next();
        assertTrue(world.isTileEmpty(babyrabbitLoc));
        */
    }




    @Test
    public void rabbitCanDigBurrow() {

    }

    @Test
    public void rabbitExitsBurrow() {
        Burrow burrow = new Burrow(world);
        burrow.randomSpawn(burrow, world);

        for (int i = 0; i < 15; i++) {
            rabbit.act(world);
        }

    }

    @Test
    public void rabbitEntersBurrow() {

    }

    @Test
    public void rabbitSleepsDuringNight() {
        world.setNight();

        rabbit.act(world);


    }


}