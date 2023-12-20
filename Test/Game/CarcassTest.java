package Game;

import itumulator.world.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CarcassTest {
    World world;
    Carcass carcass;

    @BeforeEach
    void setUp() {
        world = new World(3);
    }

    /**
     * Tester om et ådsel kan paceres et vilkårligt sted (K3-1a
     */
    @Test
    public void spawnCarcass() {
        carcass = new Carcass(world, 50);
        carcass.randomSpawn(carcass, world);

        assertNotNull(world.getTile(carcass.getLocation()));
    }

    /**
     *
     */
    @Test
    public void rabbitCarcass() {
        Rabbit rabbit = new Rabbit(world);
        rabbit.randomSpawn(rabbit, world);


        //rabbit.setCountTo25();
        rabbit.die(world, 50);

        Location rabbitLoc = rabbit.getLocation();
        //assertTrue(carcass instanceof rabbit);


    }
}