package Game;

import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Carcass extends Entity {
    private final int MaxHp;
    private  int currentHp;
    private boolean isInfected;

    Fungi fungi;

    Carcass(World world){
        super(world);
        this.MaxHp = 50;
        this.currentHp = 50;

    }
    Carcass(World world, int Maxhp){
        super(world);
        this.MaxHp = Maxhp;
        this.currentHp = Maxhp;
        isInfected = false;

    }

    @Override
    public void act(World world) {
    Decay();
    }
    private void Decay(){
      currentHp= currentHp-5;
      if (isInfected){
          currentHp= currentHp-5;
      } else if(!isInfected&&currentHp<25){
          fungi = new Fungi(world, MaxHp);
          isInfected=true;
      }
      if (currentHp<= 0){
          fullyDecay();
      }
    }
    public void beingEaten(){
        currentHp= currentHp-10;
        if (currentHp<= 0){
            fullyDecay();
        }
    }
    private void fullyDecay(){
        Location here = this.location;
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
