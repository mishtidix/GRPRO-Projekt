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
     * Tester om kaniner kan placeres et tilfældigt sted (K1-3a.)
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

    /**
     *
     */
    @Test
    public void rabbitEatsAndGainsHP() {
        Grass grass = new Grass(world);
        Location currentLoc = new Location(1, 1);
        Location currentLoc2 = new Location(0, 1);

        world.setTile(currentLoc, grass);
        world.setTile(currentLoc2, rabbit);

        rabbit.eat(world);
        int beforeEat = rabbit.getCounter();
        rabbit.act(world);
        int afterEat = rabbit.getCounter();

        assertTrue(beforeEat > afterEat);

    }

    /**
     * Tester om kaninen bliver ældre og mister energi samtidigt (k1-2d.)
     */
    @Test
    public void rabbitAges() {
        rabbit.randomSpawn(rabbit, world);
        int initAge = rabbit.getAge();
        int energi = rabbit.getHealth();

        rabbit.act(world);
        int newAge = rabbit.getAge();
        int newEnergi = rabbit.getHealth();
        assertTrue(newAge > initAge);
        assertTrue(energi > newEnergi);
    }

    /**
     * Tester om kaninen kan reproducere (k1-2e.)
     */
    @Test
    public void rabbitCanReproduce() {
        world.setDay();
        rabbit.randomSpawn(rabbit, world);
        rabbit.setReproduce(true);

        rabbit.act(world);
        Map<Object, Location> amount = world.getEntities();

        assertTrue(amount.size() > 1);
    }


    /**
     * Tester om kaninen kan grave huller (k1-2f.)
     */
    @Test
    public void rabbitCanDigBurrow() {
        rabbit.randomSpawn(rabbit, world);

        rabbit.setBurrowProb(0);
        rabbit.digBurrow();

        Location rabbitLoc = world.getLocation(rabbit);

        assertTrue(world.containsNonBlocking(rabbitLoc));
    }

    @Test
    public void rabbitExitsBurrow() {
        Burrow burrow = new Burrow(world);
        Location loc = new Location(1,1);

        for (int i = 0; i < 15; i++) {
            rabbit.act(world);
        }

    }

    @Test
    public void rabbitEntersBurrow() {
        Burrow burrow = new Burrow(world);
        Location loc = new Location(1,1);

        world.setTile(loc, rabbit);
        world.setTile(loc, burrow);

        rabbit.enterBurrow();
        assertTrue(world.isTileEmpty(loc));
        //assert


    }

    @Test
    public void rabbitSleepsDuringNight() {
        rabbit.randomSpawn(rabbit, world);
        world.setNight();

        rabbit.act(world);
        assertTrue(rabbit.isSleeping());


    }


}