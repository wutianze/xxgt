package org.ict.content;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeInfo implements BaseInfo{
    AtomicInteger timeSeq;

    public AtomicInteger getTimeSeq() {
        return timeSeq;
    }

    public void setTimeSeq(AtomicInteger timeSeq) {
        this.timeSeq = timeSeq;
    }

    @Override
    public String generateString() throws UnsupportedEncodingException {
        byte[] tmp0 = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x09};
        long timeNow = System.currentTimeMillis();
        byte[] tmp1 = BaseInfo.longToBytes(timeNow);
        byte[] tmp2 = new byte[1];
        timeSeq.getAndIncrement();
        tmp2[0] = timeSeq.byteValue();
        byte[] returnBytes = new byte[tmp0.length + tmp1.length + tmp2.length];
        System.arraycopy(tmp0, 0, returnBytes, 0, tmp0.length);
        System.arraycopy(tmp1, 0, returnBytes, tmp0.length, tmp1.length);
        System.arraycopy(tmp2, 0, returnBytes, tmp0.length+tmp1.length, tmp2.length);
        return BaseInfo.bytes2String(returnBytes);
    }
}