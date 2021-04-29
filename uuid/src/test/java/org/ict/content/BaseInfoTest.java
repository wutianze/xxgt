package org.ict.content;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

public class BaseInfoTest extends TestCase {

    public void testHexStringToByteArray() {
        String hexString = "0123456789abcdef";
        byte[] byteArray = BaseInfo.hexStringToByteArray(hexString);
        Assert.assertThat(byteArray[3],is((byte)103));
        Assert.assertThat(byteArray[6],is((byte)205));
    }

    public void testTestHexStringToByteArray() {
        String hexString = "0123456789abcdef";
        byte[] byteArray = BaseInfo.hexStringToByteArray(hexString,0,2);
        Assert.assertThat(byteArray[0],is((byte)1));
        Assert.assertThat(byteArray.length,is(1));
    }

    public void testHexStringToByteList() {
        String hexString = "0123456789abcdef";
        ArrayList<Byte> byteArray = BaseInfo.hexStringToByteList(hexString);
        Assert.assertThat(byteArray.get(3),is((byte)103));
        Assert.assertThat(byteArray.get(6),is((byte)205));
    }

    public void testByteArrayToHexString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String hexString = BaseInfo.byteArrayToHexString(byteArray);
        Assert.assertThat(hexString,is("67cd"));
    }

    public void testTestByteArrayToHexString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String hexString = BaseInfo.byteArrayToHexString(byteArray,0,1);
        Assert.assertThat(hexString.charAt(1),is('7'));
        Assert.assertThat(hexString.length(),is(2));
    }

    public void testByteArrayToString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String asciiString = BaseInfo.byteArrayToString(byteArray);
        Assert.assertThat(asciiString.length(),is(2));
    }

    public void testTestByteArrayToString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String asciiString = BaseInfo.byteArrayToString(byteArray,0,1);
        Assert.assertThat(asciiString.length(),is(1));
    }

    public void testByteArrayToByteArray() {
        byte[] byteArray = {(byte)103,(byte)205};
        Byte[] to = BaseInfo.byteArrayToByteArray(byteArray);
        Assert.assertThat(to.length,is(2));
    }

    public void testByteArrayFromByteArray() {
        Byte[] ByteArray = {(byte)103,(byte)205};
        byte[] to = BaseInfo.byteArrayFromByteArray(ByteArray);
        Assert.assertThat(to.length,is(2));
    }

    public void testStringToByteArray() {
        String s = "aa";
        byte[] byteArray = BaseInfo.stringToByteArray(s);
        Assert.assertThat(byteArray.length,is(2));
    }

    public void testLongToByteArray() {
        long number = 280;
        byte[] byteArray = BaseInfo.longToByteArray(number);
        Assert.assertThat(byteArray[7],is((byte)24));
    }

    public void testStringToByteList() {
        String s = "aa";
        ArrayList<Byte> byteArray = BaseInfo.stringToByteList(s);
        Assert.assertThat(byteArray.size(),is(2));
    }

    public void testLongToByteList() {
        long number = 280;
        byte[] byteArray = BaseInfo.longToByteArray(number);
        long recover = BaseInfo.byteArrayToLong(byteArray);
        Assert.assertThat(byteArray[7],is((byte)24));
        Assert.assertThat(number,is(recover));
    }

    public void testIntToByteList() {
    }

    public void testShortToByteList() {
    }

    public void testByteArrayToShort() {
    }

    public void testByteArrayToInt() {
    }

    public void testByteArrayToLong() {
        long number = 280;
        byte[] byteArray = BaseInfo.longToByteArray(number);
        long recover = BaseInfo.byteArrayToLong(byteArray);
        Assert.assertThat(byteArray[7],is((byte)24));
        Assert.assertThat(number,is(recover));
    }

}