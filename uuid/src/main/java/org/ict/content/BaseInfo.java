package org.ict.content;

import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

 /**
   * <p>Abstract class of different type of Info. It also provides some useful functions to construct or parse ID</p>
   *
   *
   * @author TianzeWu
   * @date 2021-04-28
   */
public interface BaseInfo {
    static byte[] hexStringToByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
    static byte[] hexStringToByteArray(String hexString,int startIndex, int endIndex) {
        if (StringUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }

        hexString = hexString.substring(startIndex,endIndex).toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
    static ArrayList<Byte> hexStringToByteList(String hexString){
        if (StringUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }

        hexString = hexString.toLowerCase();
        final ArrayList<Byte> byteList = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < hexString.length() / 2; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteList.add((byte) (high << 4 | low));
            k += 2;
        }
        return byteList;
    }
    static String byteArrayToHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        }

        final StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            if ((b & 0xff) < 0x10) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString().toLowerCase();
    }
    static String byteArrayToHexString(byte[] byteArray, int startIndex, int endIndex) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        }
        byteArray = Arrays.copyOfRange(byteArray,startIndex,endIndex);
        final StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            if ((b & 0xff) < 0x10) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString().toLowerCase();
    }
    static String byteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    static String byteArrayToString(byte[] bytes, int startIndex, int endIndex) {
        bytes = Arrays.copyOfRange(bytes,startIndex,endIndex);
        return new String(bytes, StandardCharsets.US_ASCII);
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

    @Deprecated
    static byte[] stringToBytesArray(String s){
        return s.getBytes(StandardCharsets.US_ASCII);
    }

    static byte[] longToByteArray(long number) {
        return ByteBuffer.allocate(8).putLong(0,number).array();
    }
    @Deprecated
    static ArrayList<Byte> stringToBytesList(String s){
        byte[] tempBytes =  s.getBytes(StandardCharsets.US_ASCII);
        ArrayList<Byte> returnBytes = new ArrayList<>();
        for (byte tempByte : tempBytes) {
            returnBytes.add(tempByte);
        }
        return returnBytes;
    }

    static ArrayList<Byte> longToByteList(long number){
        ByteBuffer buffer = ByteBuffer.allocate(8).putLong(0,number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<8;i++){
            returnArray.add(buffer.get(i));
        }
        return returnArray;
    }
    static ArrayList<Byte> intToByteList(int number){
        ByteBuffer buffer = ByteBuffer.allocate(4).putInt(0,number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<4;i++){
            returnArray.add(buffer.get(i));
        }
        return returnArray;
    }
    static ArrayList<Byte> shortToByteList(short number){
        ByteBuffer buffer = ByteBuffer.allocate(2).putShort(0,number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<2;i++){
            returnArray.add(buffer.get(i));
        }
        return returnArray;
    }
    static short byteArrayToShort(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getShort();
    }
    static int byteArrayToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getInt();
    }
    static long byteArrayToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    /**
     * Generate a Byte List of the Info
     *
     * @return the Byte List generated
     * @author TianzeWu
     */
    ArrayList<Byte> generateBytes();

    /**
     * Rebuild the Info class from the ID
     *
     * @param id ID byte array
     * @author TianzeWu
     */
    void recoverFromID(byte[] id);
}
