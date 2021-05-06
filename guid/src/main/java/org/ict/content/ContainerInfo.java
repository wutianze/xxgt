package org.ict.content;

import java.util.ArrayList;
 /**
   * <p>Information one Docker container</p>
   *
   *
   * @author TianzeWu
   * @date 2021-05-06
   */
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
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToByteList((short) 9));
        returnBytes.addAll(BaseInfo.shortToByteList((short)16));

        returnBytes.addAll(BaseInfo.hexStringToByteList(containerID));
        returnBytes.addAll(BaseInfo.hexStringToByteList(imageID));
        returnBytes.addAll(BaseInfo.intToByteList(containerPID));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
