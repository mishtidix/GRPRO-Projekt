package Game;

import itumulator.world.*;
import jdk.jshell.execution.LoaderDelegate;

import java.util.*;


public class Target {
    private Location current;
    private World world;
    private int currentX;
    private int currentY;
    private Entity me;

    public Target(World world, Location current, Entity me) {
        this.current = current;
        this.world = world;
        this.me = me;

        currentX = current.getX();
        currentY = current.getY();
    }

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
            return bestTarget;
    }

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

    public Entity getBestTargetWithinRange(Object target, int Range) {
        Object[][][] tiles = this.world.getTiles();
        ArrayList<Entity> targets = new ArrayList<>();
        Entity bestTarget = this.me;
        Location myLocation = this.me.getLocation();
        int mlX = myLocation.getX();
        int mlY = myLocation.getY();
        try {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    for (int k = 0; k < tiles[i][j].length; k++) {
                        Object o = tiles[i][j][k];
                        if (o.getClass().equals(target.getClass())) {
                            Entity a = (Entity) o;
                            targets.add(a);
                        }
                    }
                }
            }
            for (Entity e : targets){
                int distX; int distY;
                int X = e.getLocation().getX();
                int Y = e.getLocation().getY();
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
            for (int i = 0; i < targets.size(); i++) {
                Entity o = targets.get(i);
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
            return bestTarget;
        } catch (NullPointerException e) {

        }
        return null;
    }

}
