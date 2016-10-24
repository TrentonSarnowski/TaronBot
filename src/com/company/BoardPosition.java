package com.company;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class BoardPosition{
    private Piece piece=null;

    public boolean empty(){
        return piece==null;
    }

    public void place(Piece piece){
        if(piece==null) {
            this.piece = piece;
        }
    }

    public void add(Piece piece){
        this.piece=piece;
    }
    public boolean placeOn(Piece piece){
        Piece top=piece.getAbove();
        if(top instanceof CapStone){
            return false;
        }
        if(top.getflat()){
            return top.placeOn(piece);
        }else if(piece instanceof CapStone){
            crush();
            return top.placeOn(piece);

        }
        return false;

    }
    public int depth(){
        return piece.depth();
    }

    private boolean crush(){
        Piece top = piece.getAbove();
        if( top instanceof CapStone) {
            return false;
        }
        return top.crush();

    }
}
