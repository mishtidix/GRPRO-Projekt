package Game;
import java.time.*;

import itumulator.world.*;

public abstract class Plant extends Entity {

    public int age;
    protected int HP;
    public LocalDateTime plantedDateTime;

    public Plant(World world) {
        super(world);
        this.HP = 100;
        this.age = 0;
        this.plantedDateTime = LocalDateTime.now();
    }

    public Location getLocation() {
        return location;
    }

    public int getAge() {
        return age;
    }

    public void aging() {
        age++;
    }

    public void die(World world) {//used to delete the plant if it gets eaten?
        if (this.HP <= 0) {
            if (world.getEntities().containsKey(this)){
//            world.remove(this);
                world.delete(this);
            }
        }
    }

    @Override
    public void act(World world) {
        aging();

        die(world);

    }
}



