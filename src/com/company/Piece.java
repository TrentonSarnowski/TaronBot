package com.company;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class Piece {
    boolean flat;
    boolean white;
    private Piece above=null;
    public  Piece(boolean flat, boolean white){
        this.flat=flat;
        this.white=white;
    }
    public void move(){

    }
    public boolean placeOn(Piece piece){
        if(above==null&&flat) {
            above = piece;
            return true;
        }
        return false;
    }
    public int depth(){

        if(above!=null) {

            return 1+above.depth();
        }
        return 0;

    }
    public boolean getflat(){
        if(above!=null){
            return getflat();
        }
        return flat;
    }
    public Piece getAbove(){
        if(above!=null) {
            return above.getAbove();
        }
        return this;

    }
    public boolean crush(){
        flat=true;
        return true;
    }
}
