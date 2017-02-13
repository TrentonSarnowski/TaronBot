package com.company.TaronBot.Game;

import com.company.TaronBot.Game.Moves.DeStack;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public interface Move {

    String toString();
    List<Integer>[][] performMove(Board map, boolean control);
    boolean checkFeasible(Board map, boolean cont);
    double getWeight();
    int  getType();
    String toPlayTakString();

    boolean checkVictory(Board map, boolean cont);

    /**
     *
     * @param b
     * @return
     */
    default boolean looped(Move b){
        if(b instanceof DeStack && this instanceof DeStack){
            DeStack d = (DeStack)b;
            DeStack f = (DeStack) this;
            if(d.getX()==f.getXend()&&f.getYend()==d.getY()&&f.getPickUpC()==d.getPickUpC()){
                return true;
            }

        }
        return false;
    }


    default public boolean isEqual(Object b){
        if(b instanceof Move){
            return((Move) b).toPlayTakString().equals(this.toPlayTakString());
        }
        return false;
    }

}
