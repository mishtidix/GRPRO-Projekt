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

    assertFalse(theLoc.equals(bear.getLocation()));
}
/**
 * Tester om bear kan jæge andre dyre (spis) og spise berrys
 */
@Test
    public void BearHunts () {
    bear.randomSpawn(bear, world);
    world.setDay();
    bear.setAge(21);
    Location bLoc = world.getLocation(bear);

    Animal prey = new Rabbit(world);
    prey.randomSpawn(prey, world);

    bear.act(world);

    assertTrue(bear.isFull);




}


}
