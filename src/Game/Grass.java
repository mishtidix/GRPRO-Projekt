package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;
import java.util.*;
import itumulator.world.Location.*;

import static itumulator.world.World.getTotalDayDuration;
import static org.mockito.Mockito.when;

public class Grass extends Plant implements NonBlocking, Actor, DynamicDisplayInformationProvider {
    private static final int SPREAD_PROBABILITY = 25;
    private static final int SPREAD_COOLDOWN = 5;
    private int spreadCooldown;
    private int HP;

    /**
     * Creates grass at a random location
     * HP: for hvor mange gange græsset skal spises før den forsvinder
     * spreadCooldown: field to track the cooldown before the grass can spread again
     */
    public Grass( World world) {
        //*Vi har tilføjet to variabler: HP for hvor mange gange græsset skal spise
        super(world);
        this.HP = 100;
        this.spreadCooldown = 0;
    }


    public void spread() {
        Random r = new Random();
        if (spreadCooldown > 0) {
            Set<Location> neighbours = world.getSurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Location l = list.get((int)(Math.random() * list.size()));

            while(!world.isTileEmpty(l)) {
                l = list.get((int)(Math.random() * list.size()));
            }

            world.setTile(l, new Grass(world));
        }

    }



    public void beenEaten() {
        if (HP > 0) {
            HP -= 10;
            if (HP <= 0) {
                super.die();
            }
        }
    }

    @Override
    public void act(World world) {

    }
      

    @Override
    public DisplayInformation getInformation(){

        return new DisplayInformation(Color.GRAY, "grass");
    }


}