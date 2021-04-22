package org.ict.content;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public interface BaseInfo {

    static String bytesToString(byte[] b) {
        return new String(b, StandardCharsets.US_ASCII);
    }

    static Byte[] stringToBytes(String s){
        byte[] tempBytes =  s.getBytes(StandardCharsets.US_ASCII);
        Byte[] returnBytes = new Byte[tempBytes.length];
        for(int i=0;i<tempBytes.length;i++){
            returnBytes[i] = tempBytes[i];
        }
        return returnBytes;
    }
    static ArrayList<Byte> stringToBytesList(String s){
        byte[] tempBytes =  s.getBytes(StandardCharsets.US_ASCII);
        ArrayList<Byte> returnBytes = new ArrayList<>();
        for (byte tempByte : tempBytes) {
            returnBytes.add(tempByte);
        }
        return returnBytes;
    }

    static byte[] longToBytesArray(long number) {
        return ByteBuffer.allocate(8).putLong(0,number).array();
    }

    static ArrayList<Byte> longToBytesList(long number){
        ByteBuffer buffer = ByteBuffer.allocate(8).putLong(0,number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<8;i++){
            returnArray.add(buffer.get(i));
        }
        return returnArray;
    }
    static ArrayList<Byte> intToBytesList(int number){
        ByteBuffer buffer = ByteBuffer.allocate(4).putInt(0,number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<4;i++){
            returnArray.add(buffer.get(i));
        }
        return returnArray;
    }
    static ArrayList<Byte> shortToBytesList(short number){
        ByteBuffer buffer = ByteBuffer.allocate(2).putShort(0,number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<2;i++){
            returnArray.add(buffer.get(i));
        }
        return returnArray;
    }
    static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
    ArrayList<Byte> generateBytes();
}
