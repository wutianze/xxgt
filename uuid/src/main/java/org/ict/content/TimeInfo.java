package org.ict.content;

import com.fasterxml.jackson.databind.util.ArrayBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeInfo implements BaseInfo{
    AtomicInteger atomicSequenceInteger;
    public TimeInfo(){
        atomicSequenceInteger = new AtomicInteger(0);
    }
    public AtomicInteger getAtomicSequenceInteger() {
        return atomicSequenceInteger;
    }

    public void setAtomicSequenceInteger(AtomicInteger atomicSequenceInteger) {
        this.atomicSequenceInteger = atomicSequenceInteger;
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
}