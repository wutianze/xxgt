package org.ict.content;

import java.util.ArrayList;

public class DataInfo implements BaseInfo{
    int dataType;
    long dataSize;
    int dataAuthority;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public long getDataSize() {
        return dataSize;
    }

    public void setDataSize(long dataSize) {
        this.dataSize = dataSize;
    }

    public int getDataAuthority() {
        return dataAuthority;
    }

    public void setDataAuthority(int dataAuthority) {
        this.dataAuthority = dataAuthority;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "dataType=" + dataType +
                ", dataSize=" + dataSize +
                ", dataAuthority=" + dataAuthority +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x05);
        returnBytes.add((byte)0x10);

        returnBytes.addAll(BaseInfo.intToBytesList(dataType));
        returnBytes.addAll(BaseInfo.longToBytesList(dataSize));
        returnBytes.addAll(BaseInfo.intToBytesList(dataAuthority));
        return returnBytes;
    }

    @Override
    public void recoverFromID(String id) {

    }
}
