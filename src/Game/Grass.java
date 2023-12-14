package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;

import java.lang.reflect.*;
import java.util.*;
import itumulator.world.Location.*;

import static itumulator.world.World.getTotalDayDuration;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.when;

public class Grass extends Plant implements NonBlocking, Actor, DynamicDisplayInformationProvider {
    private static final int SPREAD_PROBABILITY = 25;
    private static final int SPREAD_COOLDOWN = 5;
    private int spreadCooldown;
    private  int age;

    private Random rnd;

    /**
     * Creates grass at a random location
     * HP: for hvor mange gange græsset skal spises før den forsvinder
     * spreadCooldown: field to track the cooldown before the grass can spread again
     */
    public Grass(World world) {
        //*Vi har tilføjet to variabler: HP for hvor mange gange græsset skal spise
        super(world);
        this.HP = 100;
        this.spreadCooldown = 100;
        this.age = 0;
    }


    public void spread() {
        Random r = new Random();
        if (readyToSpread()) {
            Set<Location> neighbours = world.getSurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            try {
                for (int i = 0; i < list.size(); i++) {
                    if (!world.isTileEmpty(list.get(i)) && world.containsNonBlocking(list.get(i))) {
                        list.remove(list.get(i));
                    }

                    if (!list.isEmpty()) {
                        Location l = list.get((int) (Math.random() * list.size()));
                        world.setTile(l, new Grass(world));

                    }

                }
                this.spreadCooldown += 100;

            } catch (Exception e){

                }

            }

    }



    public boolean readyToSpread() {
        rnd = new Random();
        if (spreadCooldown > 0) {
            this.spreadCooldown -= rnd.nextInt(10);

            return false;
        }
        return  true;
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
        this.location=world.getLocation(this);
        spread();
        readyToSpread();
        aging();
    }

    @Override
    public DisplayInformation getInformation(){

        return new DisplayInformation(Color.GRAY, "grass");
    }


}