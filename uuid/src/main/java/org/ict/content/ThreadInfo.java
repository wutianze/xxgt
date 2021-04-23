package org.ict.content;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreadInfo implements BaseInfo{
    int threadID;
    int processID;
    int parentID;

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "threadID=" + threadID +
                ", processID=" + processID +
                ", parentID=" + parentID +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToBytesList((short) 11));
        returnBytes.addAll(BaseInfo.shortToBytesList((short)12));

        returnBytes.addAll(BaseInfo.intToBytesList(threadID));
        returnBytes.addAll(BaseInfo.intToBytesList(processID));
        returnBytes.addAll(BaseInfo.intToBytesList(parentID));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
