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
        world = new World(3);

        rabbit = new Rabbit(world);
    }


    /**
     * Tester kaniner kan placeres et tilfældigt sted (K1-3a.)
     */
    @Test
    public void rabbitSpawns() {
        rabbit.randomSpawn(rabbit, world);

        assertNotNull(world.getLocation(rabbit));
    }

    /**
     * Tester om kaninen kan bevæge sig i løbet af dagen (basale funktion af et dyr).
     */
    @Test
    public void rabbitMovesDuringDay() {
        rabbit.randomSpawn(rabbit, world);
        Location initLoc = world.getLocation(rabbit);
        world.setDay();

        rabbit.act(world);

        Location moved = world.getLocation(rabbit);
        assertNull(world.getTile(initLoc));
        assertNotNull(world.getLocation(rabbit));
        assertNotEquals(initLoc, moved);
    }

    /**
     * Tester om kaniner kan dø og fjernes fra verden (k1-2b.)
     * Denne test fungerer når man udkommenterer (world.setTile(here, new Carcass(world, Carcasshp))) i animals die() metode;
     */
    @Test
    public void rabbitDies() {
        rabbit.randomSpawn(rabbit, world);
        world.setDay();
        rabbit.setAge(35);
        Location rabbitLoc = world.getLocation(rabbit);

        assertNotNull(rabbitLoc);

        rabbit.die(world, 50);

        assertTrue(world.isTileEmpty(rabbitLoc));
    }

    @Test
    public void rabbitEatsAndGainsHP() {
        Grass grass = new Grass(world);
        grass.randomSpawnNonBlocking(grass, world);
        int beforeEat = rabbit.getCounter();
        rabbit.act(world);
        int afterEat = rabbit.getCounter();

        assertTrue(beforeEat > afterEat);




        /*
        rabbit.setMaxHP();
        Grass grass = new Grass(world);
        grass.randomSpawn(grass, world);
        rabbit.act(world);
        int newHP = rabbit.getHealth();
        assertTrue(newHP > initialHP);

         */
    }


    @Test
    public void rabbitAges() {
        int initAge = rabbit.getAge();

        rabbit.act(world);
        int newAge = rabbit.getAge();
        assertTrue(newAge > initAge);
    }

    @Test
    public void rabbitCanReproduce() {
        /*
        assertTrue(rabbit.reproduce(););

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
        rabbit.setBurrowProbToZero();
        rabbit.digBurrow();

        Location rabbitLoc = world.getLocation(rabbit);

        assertTrue(world.containsNonBlocking(rabbitLoc));
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
        Location loc = new Location(1, 1);
        world.setTile(loc, rabbit);

        Burrow burrow = new Burrow(world);
        world.setTile(loc, burrow);

        rabbit.enterBurrow();
        assertNull(world.getLocation(rabbit));
        //assert


    }

    @Test
    public void rabbitSleepsDuringNight() {
        world.setNight();

        rabbit.act(world);
        assertTrue(rabbit.isSleeping());


    }


}