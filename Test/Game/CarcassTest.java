package Game;

import itumulator.world.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CarcassTest {
    World world;
    Carcass carcass;
    Rabbit rabbit;

    @BeforeEach
    void setUp() {
        world = new World(3);
    }
}