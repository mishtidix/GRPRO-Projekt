package Game;

import itumulator.executable.*;
import itumulator.world.*;

import java.awt.*;

public class Berry extends Plant implements NonBlocking, DynamicDisplayInformationProvider {
    //private int eatable;

    public Berry(World world) {
        super(world);
        this.age = 0;
        this.HP = 0;
    }

    public void beenEaten() {
        if (HP > 0) {
            HP -= 10;
            if (HP <= 0) {
                this.age = 0;
                this.HP = 0;
            }
        }
    }

    public void growBerry() {
        if (age > 10) {
            this.HP = 100;
        }

    }

    public boolean ReadyToBeEaten() {
        if (age < 10) {
            return false;
        }
        return true;
    }

    public int getHP() {
        return HP;
    }

    @Override
    public DisplayInformation getInformation() {
        if(age < 10){
            return new DisplayInformation(Color.GRAY, "bush");
        }
        return new DisplayInformation(Color.GRAY, "bush-berries");
    }

    @Override
    public void act(World world) {
        aging();
        growBerry();
    }
}
