package Game;
import itumulator.world.*;
import Game.*;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class BearTest {
World world;
Bear bear;
Animal animal;

@BeforeEach
void SetUpWorld () {
    world = new World(5);
    bear = new Bear(world);
}
    /**
     * Tester bjørn kan placeres et tilfældigt sted (K2-5a)
     */
    @Test
    public void BearAppears () {
        bear.randomSpawn(bear, world);
        assertNotNull(world.getLocation(bear));
    }
    /**
     * Tester om bear kan dø og
     */
@Test
    public void BearDies () {
    bear.randomSpawn(bear, world);
    world.setDay();
    bear.setAge(12);
    Location bearLoc = world.getLocation(bear);
    assertNotNull(bearLoc);
    bear.die(world, 55);
    assertTrue(world.isTileEmpty(bearLoc));

}
/**
 * Tester om bear kan bevæge sig om dagen (negativ test)
 */
@Test
    public void bearMovesDuringDay() {
    bear.randomSpawn(bear, world);
    world.setDay();
    bear.setAge(25);
    Location theLoc = world.getLocation(bear);

    bear.act(world);
    Location initLoc = world.getLocation(bear);

    assertTrue(initLoc.equals(bear.getLocation()));
}
/**
 * Tester om bear kan spise kaniner
 */
@Test
    public void BearCanEat () {
    World world1 = new World(3);
    Bear bear1 = new Bear(world1);
    bear1.randomSpawn(bear1, world1);
    Animal prey = new Rabbit(world1);
    prey.randomSpawnNonBlocking(prey, world1);
    assertFalse(bear1.isFull);
    int beforeEat = bear1.getCounter();
    bear1.eat(world1);
    int afterEat = bear1.getCounter();

    assertFalse(beforeEat > afterEat);
}

/**
 * Tester om bjørn kan blive ældre med Day and Night cyklus
 */
@Test
    public void bearAges () {
    bear.randomSpawn(bear, world);
    world.setDay();
    bear.setAge(10);
    int initAge = bear.getAge();

    bear.act(world);
    int newAge = bear.getAge();
    assertTrue(newAge > initAge);
}

    /**
     * Tester om bjørn sover om natten
     */
    @Test
    public void bearSleepDuringNight() {
    bear.randomSpawn(bear,world);
    world.setNight();

    bear.bearSleeps(world);

    assertTrue(bear.isSleeping());
}

/**
 * Tester om bjørn kan reproducere
 */
@Test
    public void bearReproduces () {
    bear.randomSpawn(bear, world);
    world.setDay();
    bear.setAge(10);

    assertFalse(bear.isSleeping());

    Animal babybear = bear.createChild();
    assertNotNull(babybear, "Bear should be able to reproduce and create a child");
    assertTrue(world.getEntities().containsKey(babybear));


}

}
