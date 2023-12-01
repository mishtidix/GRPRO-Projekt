package Game;
import java.util.Random;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.executable.*;
import itumulator.display.*;
import java.util.*;
public class Rabbit extends Animal {
    private Location burrow;

    public Rabbit() {
        super();
        this.burrow = null;
    }

    @Override
    public void act(World world) {
        super.act(world);
        moveTowardsHole(world);
    }

    public void moveTowardsHole(World world) {
        if (sleeping) { //Rabbit will move towards the hole when it wants to sleep
            if(burrow != null) {
                move();
            }

        }
    }
}
