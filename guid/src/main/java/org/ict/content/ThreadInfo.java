package org.ict.content;

import java.util.ArrayList;
import java.util.Arrays;
 /**
   * <p>Information of a thread in info-superbahn</p>
   *
   *
   * @author TianzeWu
   * @date 2021-05-06
   */
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
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToByteList((short) 10));
        returnBytes.addAll(BaseInfo.shortToByteList((short)12));

        returnBytes.addAll(BaseInfo.intToByteList(threadID));
        returnBytes.addAll(BaseInfo.intToByteList(processID));
        returnBytes.addAll(BaseInfo.intToByteList(parentID));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
