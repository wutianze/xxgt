package org.ict.content;

import java.util.ArrayList;
import java.util.Arrays;

public class QuintupleInfo implements BaseInfo{
    Byte[] sourceIP;
    short sourcePort;
    Byte[] destinationIP;
    short destinationPort;
    short protocol;

    public Byte[] getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(Byte[] sourceIP) {
        this.sourceIP = sourceIP;
    }

    public short getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(short sourcePort) {
        this.sourcePort = sourcePort;
    }

    public Byte[] getDestinationIP() {
        return destinationIP;
    }

    public void setDestinationIP(Byte[] destinationIP) {
        this.destinationIP = destinationIP;
    }

    public short getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(short destinationPort) {
        this.destinationPort = destinationPort;
    }

    public short getProtocol() {
        return protocol;
    }

    public void setProtocol(short protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "QuintupleInfo{" +
                "sourceIP=" + Arrays.toString(sourceIP) +
                ", sourcePort=" + sourcePort +
                ", destinationIP=" + Arrays.toString(destinationIP) +
                ", destinationPort=" + destinationPort +
                ", protocol=" + protocol +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>(BaseInfo.shortToBytesList((short) 7));
        returnBytes.addAll(BaseInfo.shortToBytesList((short)14));

        returnBytes.addAll(Arrays.asList(sourceIP));
        returnBytes.addAll(BaseInfo.shortToBytesList(sourcePort));
        returnBytes.addAll(Arrays.asList(destinationIP));
        returnBytes.addAll(BaseInfo.shortToBytesList(destinationPort));
        returnBytes.addAll(BaseInfo.shortToBytesList(protocol));
        return returnBytes;
    }

    @Override
    public void recoverFromID(byte[] id) {

    }
}
