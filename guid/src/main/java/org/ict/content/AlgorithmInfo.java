package org.ict.content;

import java.util.ArrayList;
 /**
   * <p>Information of one algorithm used in info-superbahn</p>
   *
   *
   * @author TianzeWu
   * @date 2021-05-06
   */
public class AlgorithmInfo implements BaseInfo{
    long algorithmType;
    short algorithmVersion;
    int powerNeeded;

    public long getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(long algorithmType) {
        this.algorithmType = algorithmType;
    }

    public short getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(short algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public int getPowerNeeded() {
        return powerNeeded;
    }

    public void setPowerNeeded(int powerNeeded) {
        this.powerNeeded = powerNeeded;
    }

    @Override
    public String toString() {
        return "AlgorithmInfo{" +
                "algorithmType=" + algorithmType +
                ", algorithmVersion=" + algorithmVersion +
                ", powerNeeded=" + powerNeeded +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes(){
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToByteList((short) 3));
        returnBytes.addAll(BaseInfo.shortToByteList((short)14));

        returnBytes.addAll(BaseInfo.longToByteList(algorithmType));
        returnBytes.addAll(BaseInfo.shortToByteList(algorithmVersion));
        returnBytes.addAll(BaseInfo.intToByteList(powerNeeded));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
