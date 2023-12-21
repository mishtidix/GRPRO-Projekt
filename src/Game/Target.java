package Game;

import itumulator.world.*;

import java.util.*;


public class Target {
    private World world;
    private int currentX;
    private int currentY;
    private Entity me;
    /**
     * The point of this class is to find the best option of a specific sub-class of Entity
     * So for example when Rabbit needs to find a burrow to sleep in, then it will call this class.
     * @param world
     * @param current
     * @param me
     */

    public Target(World world, Location current, Entity me) {
        this.world = world;
        this.me = me;

        currentX = current.getX();
        currentY = current.getY();
    }
    /**
     * This Method finds the clossest instance of a certain class and returns it as an entity,
     * First us world.getEntities, the method gets all objects in the world, and then it takes all those of the wanted class into a separate list
     * afterwards through some if-statements and some small calculations it finds the clossest target to the entity that called this method
     * @param target
     * @return
     */


    public Entity getBestTarget(Class target) {
        Map tiles = this.world.getEntities();
        ArrayList<Entity> targets = new ArrayList<>();
        for(Object object : tiles.keySet()){
            if (object != null && object.getClass().equals(target)) {
                Entity a = (Entity) object;
                targets.add(a);
            }
        }


        Entity bestTarget = null;
        if(targets.size() > 0) bestTarget = targets.get(0);
        for (int i = 0; i < targets.size(); i++) {
            Entity o = targets.get(i);
            if (o.getLocation()== null && world.getEntities().get(o)!=null){o.setLocation(world.getLocation(o));}
            if (o.getLocation()!=null && bestTarget.getLocation()!=null){
                Location check = o.getLocation();
                Location best = bestTarget.getLocation();
                int bX;
                int bY;
                int cX;
                int cY;

                if (check.getX() < currentX) {
                    cX = currentX - check.getX();
                } else {
                    cX = check.getX() - currentX;
                }
                if (check.getY() < currentY) {
                    cY = currentY - check.getY();
                } else {
                    cY = check.getY() - currentY;
                }
                if (best.getX() < currentX) {
                    bX = currentX - best.getX();
                } else {
                    bX = best.getX() - currentX;
                }
                if (best.getY() < currentY) {
                    bY = currentY - best.getY();
                } else {
                    bY = best.getY() - currentY;
                }

                if (cX < bX && cY < bY && cX >= 0 && cY >= 0) {
                    bestTarget = o;
                } else if (cX < bX && cY <= bY && cX >= 0 || cY < bY && cX <= bX && cY >= 0) {
                    bestTarget = o;

                }
            }
        }
        return bestTarget;
    }
    /**
     * This Method those basically the same as the one above, this version of it however takes in a list of different classes
     * it then proceeds to do basically the same just this time with the different classes.
     * @param targets
     * @return
     */

    public Entity getBestTarget(List<Class> targets) {
        Entity bestTarget = this.me;
        ArrayList<Entity> bestTargets = new ArrayList<>();
        for (Class o : targets) {
            bestTargets.add(getBestTarget(o));
        }
        for (Entity e : bestTargets) {
            Location check = e.getLocation();
            Location best = bestTarget.getLocation();
            int bX;
            int bY;
            int cX;
            int cY;

            if (check.getX() < currentX) {
                cX = currentX - check.getX();
            } else {
                cX = check.getX() - currentX;
            }
            if (check.getY() < currentY) {
                cY = currentY - check.getY();
            } else {
                cY = check.getY() - currentY;
            }
            if (best.getX() < currentX) {
                bX = currentX - best.getX();
            } else {
                bX = best.getX() - currentX;
            }
            if (best.getY() < currentY) {
                bY = currentY - best.getY();
            } else {
                bY = best.getY() - currentY;
            }

            if (cX < bX && cY < bY && cX >= 0 && cY >= 0) {
                bestTarget = e;
            } else if (cX < bX && cY <= bY && cX >= 0 || cY < bY && cX <= bX && cY >= 0) {
                bestTarget = e;
                ;
            }
        }
        return bestTarget;
    }

    public Entity getBestTargetWithinRange(Class target, int Range) {
        int mlX = me.getLocation().getX();
        int mlY = me.getLocation().getY();
        Map tiles = this.world.getEntities();
        ArrayList<Entity> targets = new ArrayList<>();
        for(Object object : tiles.keySet()){
            if (object != null && object.getClass().equals(target)) {
                Entity a = (Entity) object;
                targets.add(a);
            }
        }
        if (targets.size()<= 0){
            return null;
        }

        Entity bestTarget = null;
        if(!targets.isEmpty()) bestTarget = targets.get(0);

        for (Entity e : targets){
            int distX; int distY;
            Location eloc =world.getLocation(e);
            int X = eloc.getX();
            int Y = eloc.getY();
            if(X<mlX){
                distX = mlX-X;
            }else{
                distX = X-mlX;
            }
            if(Y<mlY){
                distY = mlY-Y;
            }else{
                distY = Y-mlY;
            }
            if (distY>Range || distX> Range){
                targets.remove(e);
            }
        }
        if (targets.size()== 1){
            return targets.get(0);
        }
        for (int i = 0; i < targets.size(); i++) {
            Entity o = targets.get(i);
            Location check = world.getLocation(o);
            Location best = world.getLocation(bestTarget);
            int bX;
            int bY;
            int cX;
            int cY;

            if (check.getX() < currentX) {
                cX = currentX - check.getX();
            } else {
                cX = check.getX() - currentX;
            }
            if (check.getY() < currentY) {
                cY = currentY - check.getY();
            } else {
                cY = check.getY() - currentY;
            }
            if (best.getX() < currentX) {
                bX = currentX - best.getX();
            } else {
                bX = best.getX() - currentX;
            }
            if (best.getY() < currentY) {
                bY = currentY - best.getY();
            } else {
                bY = best.getY() - currentY;
            }

            if (cX < bX && cY < bY && cX >= 0 && cY >= 0) {
                bestTarget = o;
            } else if (cX < bX && cY <= bY && cX >= 0 || cY < bY && cX <= bX && cY >= 0) {
                bestTarget = o;

            }

        }
        return bestTarget;
    }

}
