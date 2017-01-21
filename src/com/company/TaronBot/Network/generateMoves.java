package com.company.TaronBot.Network;

import com.company.TaronBot.Game.Moves.DeStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by sarnowskit on 1/13/2017.
 */
public class generateMoves {
    public static void main(String args[]) {
        long time = System.currentTimeMillis();
        getDestacks(8);
        System.out.println(System.currentTimeMillis() - time);
    }

    public static List<DeStack> getDestacks(int i) {
        List<DeStack> returnVal = new ArrayList<>();

        for (boolean[] b : getMoves(i)) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < i; k++) {
                    returnVal.add(new DeStack(b, true, true, 0, j, k));
                    returnVal.add(new DeStack(b, true, true, 0, j, k));
                    returnVal.add(new DeStack(b, true, true, 0, j, k));
                    returnVal.add(new DeStack(b, true, true, 0, j, k));
                    returnVal.add(new DeStack(b, true, false, 0, j, k));
                    returnVal.add(new DeStack(b, true, false, 0, j, k));
                    returnVal.add(new DeStack(b, true, false, 0, j, k));
                    returnVal.add(new DeStack(b, true, false, 0, j, k));
                    returnVal.add(new DeStack(b, false, false, 0, j, k));
                    returnVal.add(new DeStack(b, false, false, 0, j, k));
                    returnVal.add(new DeStack(b, false, false, 0, j, k));
                    returnVal.add(new DeStack(b, false, false, 0, j, k));
                    returnVal.add(new DeStack(b, false, true, 0, j, k));
                    returnVal.add(new DeStack(b, false, true, 0, j, k));
                    returnVal.add(new DeStack(b, false, true, 0, j, k));
                    returnVal.add(new DeStack(b, false, true, 0, j, k));
                }
            }

        }
        return returnVal;
    }

    public static List<boolean[]> getMoves(int height) {

        int l = 1;
        List<boolean[]> bs = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            l *= 2;
        }
        for (int j = 1; j <= l; j++) {
            bs.add(toArray(j));

        }
        return bs;
    }

    public static boolean[] toArray(int b) {
        boolean ba[] = new boolean[8];
        if ((b & 1) == 1) {
            ba[0] = true;
        }
        if ((b & 2) == 2) {
            ba[1] = true;
        }
        if ((b & 4) == 4) {
            ba[2] = true;
        }
        if ((b & 8) == 8) {
            ba[3] = true;
        }
        if ((b & 16) == 16) {
            ba[4] = true;
        }
        if ((b & 32) == 32) {
            ba[5] = true;
        }
        if ((b & 64) == 64) {
            ba[6] = true;
        }
        if ((b & 128) == 128) {
            ba[7] = true;
        }
        return ba;
    }
}
