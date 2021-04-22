package org.ict.content;

import java.util.ArrayList;
import java.util.Random;

public class RandomInfo implements BaseInfo{
    public Random r;
    public RandomInfo(){
        r = new Random();
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "RandomInfo{" +
                "r=" + r +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes(){
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x09);
        returnBytes.add((byte)0x04);
        returnBytes.addAll(BaseInfo.intToBytesList(r.nextInt()));
        return returnBytes;
    }
}
