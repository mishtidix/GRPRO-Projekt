package Game;
import itumulator.world.*;
import java.util.*;

public class Path {
    Location goal;
    Location current;
    World world;

    public Path(Location goal, Location current){
        this.goal = goal;
        this.current = current;
    }

    public Location getPath(World world){
        int gX = this.goal.getX();
        int gY = this.goal.getY();
        int iX;
        int iY;
        int bX;
        int bY;
        Location best = this.current;
        world.setCurrentLocation(this.current);

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);
            if (!list.isEmpty()){best=list.get(0);}
            for (Location i : list) {
                if (world.isTileEmpty(i)) {
                    if (i.getX() < gX) {
                        iX = gX - i.getX();
                    } else {
                        iX = i.getX() - gX;
                    }
                    if (i.getY() < gY) {
                        iY = gY - i.getY();
                    } else {
                        iY = i.getY() - gY;
                    }
                    if (best.getX() < gX) {
                        bX = gX - best.getX();
                    } else {
                        bX = best.getX() - gX;
                    }
                    if (best.getY() < gY) {
                        bY = gY - best.getY();
                    } else {
                        bY = best.getY() - gY;
                    }

                    if (iX < bX && iY < bY && iX >= 0 && iY >= 0) {
                        best = i;

                    } else if (iX < bX && iY <= bY && iX >= 0 || iY < bY && iX <= bX && iY >= 0) {
                        best = i;
                    }
                }
            }
if (best==current){System.out.println("fail");}
        return best;
    }

    public Set<Location> getPathAround(World world){
        int cX = this.current.getX();
        int cY = this.current.getY();
        int lX = 0; int lY = 0;
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        for(Location l: list){
            
            if(l.getX()<cX){
                lX = cX - l.getX();
            }else{
                cX = l.getX() - cX;
            }
            if(l.getY()<cY){
                lY = cY - l.getY();
            }else{
                lY = l.getY() - cY;
            }
            if(lX > 2||lY >2){
                neighbours.remove(l);
            }
        }
        
        return neighbours;
    }
}
