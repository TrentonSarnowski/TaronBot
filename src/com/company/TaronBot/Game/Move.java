package com.company.TaronBot.Game;

import com.company.TaronBot.Game.Moves.DeStack;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public interface Move {

    String toString();

    /**
     * Performs a move ona boardstate
     * @param map
     * @param control
     * @return
     */
    List<Integer>[][] performMove(Board map, boolean control);

    /**
     * checks if a move is feasible for a board stae
     * @param map
     * @param cont
     * @return
     */
    boolean checkFeasible(Board map, boolean cont);
    double getWeight();

    /**
     * returns move type
     * @return
     */
    int  getType();

    /**
     * returns playtak readable string
     * @return
     */
    String toPlayTakString();

    boolean checkVictory(Board map, boolean cont);

    /**
     * checks if a move is the idrect inverse of another
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

    /**
     * checks to see if to Moves are equal based on playtakstring
     * @param b
     * @return
     */
    default public boolean isEqual(Object b){
        if(b instanceof Move){
            return((Move) b).toPlayTakString().equals(this.toPlayTakString());
        }
        return false;
    }

}
