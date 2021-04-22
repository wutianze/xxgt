package org.ict.content;

import java.util.ArrayList;

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
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x03);
        returnBytes.add((byte)0x12);

        returnBytes.addAll(BaseInfo.longToBytesList(algorithmType));
        returnBytes.addAll(BaseInfo.shortToBytesList(algorithmVersion));
        returnBytes.addAll(BaseInfo.intToBytesList(powerNeeded));
        return returnBytes;
    }

    @Override
    public void recoverFromID(String id) {

    }
}
