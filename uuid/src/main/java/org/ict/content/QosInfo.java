package org.ict.content;

import java.util.ArrayList;

public class QosInfo implements BaseInfo{
    int qosType;
    long qosValue;

    public int getQosType() {
        return qosType;
    }

    public void setQosType(int qosType) {
        this.qosType = qosType;
    }

    public long getQosValue() {
        return qosValue;
    }

    public void setQosValue(long qosValue) {
        this.qosValue = qosValue;
    }

    @Override
    public String toString() {
        return "QosInfo{" +
                "qosType=" + qosType +
                ", qosValue=" + qosValue +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToBytesList((short) 8));
        returnBytes.addAll(BaseInfo.shortToBytesList((short)12));

        returnBytes.addAll(BaseInfo.intToBytesList(qosType));
        returnBytes.addAll(BaseInfo.longToBytesList(qosValue));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
