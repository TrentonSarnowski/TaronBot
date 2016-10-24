package com.company;

import java.util.List;

/**
 * Created by sarnowskit on 10/21/2016.
 */
public class Board {
    private BoardPosition[][] board;
    int boardSize;
    public Board(int x){
        board= new BoardPosition[x][x];
        for (BoardPosition b[]:board) {
            for (BoardPosition p:b) {
                p=new BoardPosition();
            }
        }
        boardSize=x;
    }
    public boolean place(int x, int y, boolean flat, boolean cap, boolean white){
        if(board[x][y].empty()){
            if(cap){
                board[x][y].place(new CapStone(white));
            }else {
                board[x][y].place(new Piece(flat, white));
            }
            return true;
        }

        return false;
    }

    /**
     *
     * @param x
     * @param y
     * @param sideways is it moving sidways
     * @param up is it moving up or down
     * @param move
     * @return
     */
    public boolean move(int x, int y, boolean sideways, boolean up, List<Integer> move){
        int sum=0;
        for(int i:move){
            sum+=i;
        }
        return sum <= board[x][y].depth();
    }
}
