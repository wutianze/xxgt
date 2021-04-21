package org.ict.content;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public interface BaseInfo {

    static String bytesToString(byte[] b) throws UnsupportedEncodingException {
        return new String(b, StandardCharsets.US_ASCII);
    }

    static byte[] stringToBytes(String s){
        return s.getBytes(StandardCharsets.US_ASCII);
    }

    static byte[] longToBytesArray(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, number);
        return buffer.array();
    }

    static ArrayList<Byte> longToBytesList(long number){
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, number);
        ArrayList<Byte> returnArray = new ArrayList<>();
        for(int i=0;i<8;i++){
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
    ArrayList<Byte> generateBytes() throws UnsupportedEncodingException;
}
