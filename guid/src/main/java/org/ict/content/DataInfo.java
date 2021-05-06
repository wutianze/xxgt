package org.ict.content;

import java.util.ArrayList;
 /**
   * <p>Information of one piece of data in info-superbahn</p>
   *
   *
   * @author TianzeWu
   * @date 2021-05-06
   */
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
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToByteList((short) 5));
        returnBytes.addAll(BaseInfo.shortToByteList((short)16));

        returnBytes.addAll(BaseInfo.intToByteList(dataType));
        returnBytes.addAll(BaseInfo.longToByteList(dataSize));
        returnBytes.addAll(BaseInfo.intToByteList(dataAuthority));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
