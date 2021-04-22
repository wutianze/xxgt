package org.ict.content;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public interface BaseInfo {

    static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }
    static String bytesToString(byte[] bytes, int startIndex, int endIndex) {
        byte[] cut = Arrays.copyOfRange(bytes,startIndex,endIndex);
        return new String(cut, StandardCharsets.US_ASCII);
    }
    static Byte[] bytesToBytes(byte[] tempBytes){
        Byte[] returnBytes = new Byte[tempBytes.length];
        for(int i=0;i<tempBytes.length;i++){
            returnBytes[i] = tempBytes[i];
        }
        return returnBytes;
    }
    static byte[] bytesFromBytes(Byte[] tempBytes){
       byte[] returnBytes = new byte[tempBytes.length];
        for(int i=0;i<tempBytes.length;i++){
            returnBytes[i] = tempBytes[i];
        }
        return returnBytes;
    }
    static byte[] stringToBytesArray(String s){
        return s.getBytes(StandardCharsets.US_ASCII);
    }
    static byte[] longToBytesArray(long number) {
        return ByteBuffer.allocate(8).putLong(0,number).array();
    }
    static ArrayList<Byte> stringToBytesList(String s){
        byte[] tempBytes =  s.getBytes(StandardCharsets.US_ASCII);
        ArrayList<Byte> returnBytes = new ArrayList<>();
        for (byte tempByte : tempBytes) {
            returnBytes.add(tempByte);
        }
        return returnBytes;
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
    static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getInt();
    }
    static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
    ArrayList<Byte> generateBytes();
    void recoverFromID(String id);
}
