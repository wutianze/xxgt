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
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToByteList((short) 9));
        returnBytes.addAll(BaseInfo.shortToByteList((short)4));

        returnBytes.addAll(BaseInfo.intToByteList(r.nextInt()));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
