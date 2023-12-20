package Game;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.World;

import java.awt.*;

public class Fungi extends Entity implements Actor, DynamicDisplayInformationProvider {
    Carcass carcass;
    int infectedCount;
    public Fungi(World world){//constructor
        super(world);
        infectedCount = 25;
    }
    public Fungi(World world, int infectedCount){//constructor, som bruges når en ny fungi bliver skabt af carcass eller fungi selv
        super(world);
        this.infectedCount = infectedCount/2;
    }

    public void spread() {
    //hvis fungi er i en carcass så bliver infectedCount ved med at gå op
    if (!(carcass == null)) {
        infectedCount = infectedCount + 2;
    } else {
        //hvis den ikke har nogen carcass, så prøver den at finde et carcass at sprede sig til
        this.location = world.getLocation(this);
        Target target = new Target(world, location, this);
        Carcass carcass1 = (Carcass) target.getBestTarget(Carcass.class);
        if (!(carcass1 == null)) {
            //hvis det lykkes at finde et nyt carcass, så går infectedCount op
            infectedCount = infectedCount + 2;
            if (!carcass1.isInfected()) {
                //hvis det carcass ikke er infected så bliver det infected
                carcass1.setFungi(new Fungi(world, carcass1.getMaxHp()));
            }
        } else {
            //hvis der ikke var nogen carcass så går infectedCount ned, og hvis den bliver 0 eller der under bliver den slettet
            infectedCount = infectedCount - 5;
            if (infectedCount <= 0) {
                world.delete(this);
            }
        }
    }
}

@Override
    public DisplayInformation getInformation() {
        //display information ændre sig i forhold til størrelse af fungi
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
