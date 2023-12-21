package Game;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.World;

import java.awt.*;

public class Fungi extends Entity implements Actor, DynamicDisplayInformationProvider {
    private Carcass carcass;
    private int infectedCount;
    public Fungi(World world){//constructor
        super(world);
        infectedCount = 25;
    }
    public Fungi(World world, int infectedCount){//constructor, som bruges når en ny fungi bliver skabt af carcass eller fungi selv
        super(world);
        this.infectedCount = infectedCount/2;
    }
    /**
     * This method checks whether the fungi should keep growing or start decaying
     * if the fungi has a carcass then it will keep growing otherwise it will find a new carcass to spread to
     * if there is no carcass it will start decaying rapidly until its infectedcount is zero or below then it will remove itself from the world
     */

    public void spread() {

        if (!(carcass == null)) {
            infectedCount = infectedCount + 2;
        } else {

            this.location = world.getLocation(this);
            Target target = new Target(world, location, this);
            Carcass carcass1 = (Carcass) target.getBestTarget(Carcass.class);
            if (!(carcass1 == null)) {

                infectedCount = infectedCount + 1;
                if (!carcass1.isInfected()) {

                    carcass1.setFungi(new Fungi(world, carcass1.getMaxHp()));
                }
            } else {

                infectedCount = infectedCount - 7;
                if (infectedCount <= 0) {
                    world.delete(this);
                }
            }
        }
    }

    @Override
    public DisplayInformation getInformation() {

        if (carcass == null && infectedCount>=25 && infectedCount<=34){
            return new DisplayInformation(Color.red, "fungi-small");
        } else if (carcass == null && infectedCount>=35) {
            return new DisplayInformation(Color.red, "fungi");
        }
        return new DisplayInformation(Color.green);
    }

    @Override
    public void act(World world) {

        spread();
    }

    public void setCarcass(Carcass carcass) {
        this.carcass = carcass;
    }
    public void addInfectedCount(){
        this.infectedCount=this.infectedCount+2;
    }
}
