package org.ict.content;

import java.util.ArrayList;
import java.util.Random;
 /**
   * <p>One random integer</p>
   *
   *
   * @author TianzeWu
   * @date 2021-05-06
   */
public class RandomInfo implements BaseInfo{
    public int randomNumber;

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    @Override
    public ArrayList<Byte> generateBytes(){
        Random r = new Random();
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToByteList((short) 8));
        returnBytes.addAll(BaseInfo.shortToByteList((short)4));

        this.randomNumber = r.nextInt();
        returnBytes.addAll(BaseInfo.intToByteList(this.randomNumber));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
