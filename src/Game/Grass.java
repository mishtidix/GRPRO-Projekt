package Game;
import itumulator.world.*;
import itumulator.executable.*;
import java.awt.*;

import itumulator.simulator.*;


import java.util.*;


public class Grass extends Plant implements NonBlocking, Actor, DynamicDisplayInformationProvider {

    private int spreadCooldown;

    private Random rnd;

    /**
     * Creates grass at a random location
     * HP: for hvor mange gange græsset skal spises før den forsvinder
     * spreadCooldown: field to track the cooldown before the grass can spread again
     */
    public Grass(World world) {
        //*Vi har tilføjet to variabler: HP for hvor mange gange græsset skal spise
        super(world);
        this.spreadCooldown = 250;
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
                this.spreadCooldown += 250;

            } catch (Exception e){

            }

        }

    }



    public boolean readyToSpread() {
        rnd = new Random();
        if (spreadCooldown > 0) {
            this.spreadCooldown -= rnd.nextInt(5);

            return false;
        }
        return  true;
    }


    public void beenEaten(World world) {
        if (HP > 0) {
            HP -= 10;
        }
    }

    public void setSpreadCooldown() {
        spreadCooldown = 0;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    @Override
    public void act(World world) {
        super.act(world);
        spread();
        readyToSpread();
        aging();
        die(world);
    }

    @Override
    public DisplayInformation getInformation(){

        return new DisplayInformation(Color.GRAY, "grass");
    }


}