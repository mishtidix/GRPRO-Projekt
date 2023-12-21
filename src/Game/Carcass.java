package Game;

import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Carcass extends Entity {
    private final int MaxHp;
    private  int currentHp;
    private boolean isInfected;

    private Fungi fungi;



    public Carcass(World world, boolean isInfected) {
        super(world);
        this.MaxHp = 50;
        this.currentHp = 50;
        this.isInfected =  isInfected;
        if (this.isInfected){
            this.fungi=new Fungi(world,MaxHp);
            this.fungi.setCarcass(this);
        }
    }
    public Carcass(World world, int Maxhp){
        super(world);
        this.MaxHp = Maxhp;
        this.currentHp = Maxhp;
        isInfected = false;

    }

    @Override
    public void act(World world) {
        this.location = world.getLocation(this);
        Decay();
    }
    /**
     * This method simply makes the carcass slowly decay overtime and it decays faster if its infected
     * if it's currentHp drops to 0 or below then it will call the method fullyDecay to remove itself
     */

    private void Decay(){
        if (currentHp<= 0){
            fullyDecay();
        }
        currentHp= currentHp-7;
        if (isInfected){
            currentHp= currentHp-7;
            this.fungi.addInfectedCount();
        } else if(currentHp<25){
            fungi = new Fungi(world, MaxHp);
            fungi.setCarcass(this);
            isInfected=true;
        }
/**
 * if an animal is eating a carcass then they will call this method and that will decrease currentHp and if it falls to zero or below then it will call fully decay
 */

    }
    public void beingEaten(){
        currentHp= currentHp-10;
        if (currentHp<= 0){
            fullyDecay();
        }
    }
    private void fullyDecay(){
        Location here = world.getLocation(this);
        this.world.remove(this);
        this.world.setTile(here,fungi);
        fungi.setCarcass(null);
        this.world.delete(this);
    }

    @Override
    public DisplayInformation getInformation() {
        if(currentHp<=30){
            return new DisplayInformation(Color.GRAY, "carcass-small");
        }
        return new DisplayInformation(Color.GRAY, "carcass");
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return MaxHp;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }

    public void setFungi(Fungi fungi) {
        this.fungi = fungi;
        setInfected(true);
    }
}
