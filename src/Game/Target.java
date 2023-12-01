package Game;

import itumulator.world.*;
import java.util.*;
import java.util.Arrays;

public class Target
{
    Location current;
    World world;
    int currentX ; int currentY;
    Animal me;
    public Target(World world, Location current, Animal me)
    {
        this.current = current;
        this.world = world;
        this.me = me;
        currentX = current.getX();
        currentY = current.getY();
    }

    public Entity getBestTarget(Object target){
        Object[][][] tiles = this.world.getTiles();
        List targets = new ArrayList<Entity>();
        Entity bestTarget = (Entity)this.me;
        try{
            for(int i = 0; i<tiles.length ; i++){
                for(int j = 0; j< tiles[i].length ; j++){
                    for(int k = 0; k < tiles[i][j].length ; k++){
                        Object o = tiles[i][j][k];
                        if(o.getClass().equals(target.getClass())){
                            Entity a = (Entity)o;
                            targets.add(a);
                        }
                    }
                }
            }

            for(int i = 0; i<targets.size() ; i++){
                Entity o = (Entity)targets.get(i);
                Location check = o.getLocation();
                Location best = bestTarget.getLocation();
                int bX;  int bY; int cX; int cY;

                if(check.getX() < currentX){
                    cX = currentX- check.getX();
                }else{
                    cX = check.getX() - currentX;
                }
                if(check.getY() < currentY){
                    cY = currentY- check.getY();
                }else{
                    cY = check.getY() - currentY;
                }
                if(best.getX()<currentX){
                    bX = currentX - best.getX();
                }else{
                    bX = best.getX() - currentX;
                }
                if(best.getY()<currentY){
                    bY = currentY - best.getY();
                }else{
                    bY = best.getY() - currentY;
                }

                if(cX < bX && cY  < bY && cX >=0 && cY >= 0){
                    bestTarget = o;
                } else if(cX < bX && cY  <=bY && cX >=0 || cY  < bY && cX <= bX && cY >= 0){
                    bestTarget = o;;
                }

            }
            return bestTarget;
        }catch (NullPointerException e){
        }
        return null;
    }
}