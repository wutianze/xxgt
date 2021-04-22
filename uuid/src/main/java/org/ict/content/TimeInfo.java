package org.ict.content;

import com.fasterxml.jackson.databind.util.ArrayBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeInfo implements BaseInfo{
    private AtomicInteger atomicSequenceInteger;
    public long timestamp;
    public byte timeSequence;
    public TimeInfo(){
        atomicSequenceInteger = new AtomicInteger(0);
    }
    public AtomicInteger getAtomicSequenceInteger() {
        return atomicSequenceInteger;
    }

    public void setAtomicSequenceInteger(AtomicInteger atomicSequenceInteger) {
        this.atomicSequenceInteger = atomicSequenceInteger;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte getTimeSequence() {
        return timeSequence;
    }

    public void setTimeSequence(byte timeSequence) {
        this.timeSequence = timeSequence;
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "timestamp=" + timestamp +
                ", timeSequence=" + timeSequence +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes(){
        ArrayList<Byte>returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x09);

        returnBytes.addAll(BaseInfo.longToBytesList(System.currentTimeMillis()));
        atomicSequenceInteger.getAndIncrement();
        returnBytes.add(atomicSequenceInteger.byteValue());
        return returnBytes;
    }

    @Override
    public void recoverFromID(String id) {
        this.timestamp = Long.parseLong(id.substring(0,8));
        this.timeSequence = BaseInfo.stringToBytesArray(id.substring(8,9))[0];
    }
}