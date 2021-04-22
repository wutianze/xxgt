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
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x07);
        returnBytes.add((byte)0x0e);

        returnBytes.addAll(Arrays.asList(sourceIP));
        returnBytes.addAll(BaseInfo.shortToBytesList(sourcePort));
        returnBytes.addAll(Arrays.asList(destinationIP));
        returnBytes.addAll(BaseInfo.shortToBytesList(destinationPort));
        returnBytes.addAll(BaseInfo.shortToBytesList(protocol));
        return returnBytes;
    }
}
