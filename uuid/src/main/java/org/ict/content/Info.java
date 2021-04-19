package org.ict.content;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public interface Info {

    public static String bytes2String(byte[] b) throws UnsupportedEncodingException {
        return new String(b, StandardCharsets.UTF_8);
    }

    public static byte[] string2Bytes(String s){
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
    String generateString() throws UnsupportedEncodingException;
}
