package org.ict.content;

import java.util.ArrayList;

public class ContainerInfo implements BaseInfo{
    String containerID;
    String imageID;
    int containerPID;

    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public int getContainerPID() {
        return containerPID;
    }

    public void setContainerPID(int containerPID) {
        this.containerPID = containerPID;
    }

    @Override
    public String toString() {
        return "ContainerInfo{" +
                "containerID='" + containerID + '\'' +
                ", imageID='" + imageID + '\'' +
                ", containerPID=" + containerPID +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToBytesList((short) 10));
        returnBytes.addAll(BaseInfo.shortToBytesList((short)16));

        returnBytes.addAll(BaseInfo.stringToBytesList(containerID));
        returnBytes.addAll(BaseInfo.stringToBytesList(imageID));
        returnBytes.addAll(BaseInfo.intToBytesList(containerPID));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
