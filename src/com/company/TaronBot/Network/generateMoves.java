package com.company.TaronBot.Network;

import com.company.TaronBot.Game.Moves.DeStack;

import java.util.ArrayList;
import java.util.List;

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
            bs.add(toArray(j, height));

        }
        return bs;
    }

    public static boolean[] toArray(int b, int height) {
        boolean ba[] = new boolean[height];
        int mult=1;
        for (int i = 0; i < height ; i++) {
            if ((b & mult) == mult) {
                ba[i] = true;
            }
            mult*=2;
        }

        return ba;
    }
}
