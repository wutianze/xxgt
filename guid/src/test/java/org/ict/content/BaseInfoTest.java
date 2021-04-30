package org.ict.content;

import com.fasterxml.jackson.databind.ser.Serializers;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseInfoTest extends TestCase {

    @Test
    public void testHexStringToByteArray() {
        String hexString = "0123456789abcdef";
        byte[] byteArray = BaseInfo.hexStringToByteArray(hexString);
        Assert.assertThat(byteArray[3],is((byte)103));
        Assert.assertThat(byteArray[6],is((byte)205));
    }

    @Test
    public void testTestHexStringToByteArray() {
        String hexString = "0123456789abcdef";
        byte[] byteArray = BaseInfo.hexStringToByteArray(hexString,0,2);
        Assert.assertThat(byteArray[0],is((byte)1));
        Assert.assertThat(byteArray.length,is(1));
    }

    @Test
    public void testHexStringToByteList() {
        String hexString = "0123456789abcdef";
        ArrayList<Byte> byteArray = BaseInfo.hexStringToByteList(hexString);
        Assert.assertThat(byteArray.get(3),is((byte)103));
        Assert.assertThat(byteArray.get(6),is((byte)205));
    }

    @Test
    public void testByteArrayToHexString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String hexString = BaseInfo.byteArrayToHexString(byteArray);
        Assert.assertThat(hexString,is("67cd"));
    }

    @Test
    public void testTestByteArrayToHexString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String hexString = BaseInfo.byteArrayToHexString(byteArray,0,1);
        Assert.assertThat(hexString.charAt(1),is('7'));
        Assert.assertThat(hexString.length(),is(2));
    }

    @Test
    public void testByteArrayToString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String asciiString = BaseInfo.byteArrayToString(byteArray);
        Assert.assertThat(asciiString.length(),is(2));
    }

    @Test
    public void testTestByteArrayToString() {
        byte[] byteArray = {(byte)103,(byte)205};
        String asciiString = BaseInfo.byteArrayToString(byteArray,0,1);
        Assert.assertThat(asciiString.length(),is(1));
    }

    @Test
    public void testByteArrayToByteArray() {
        byte[] byteArray = {(byte)103,(byte)205};
        Byte[] to = BaseInfo.byteArrayToByteArray(byteArray);
        Assert.assertThat(to.length,is(2));
    }

    @Test
    public void testByteArrayFromByteArray() {
        Byte[] ByteArray = {(byte)103,(byte)205};
        byte[] to = BaseInfo.byteArrayFromByteArray(ByteArray);
        Assert.assertThat(to.length,is(2));
    }

    @Test
    public void testStringToByteArray() {
        String s = "aa";
        byte[] byteArray = BaseInfo.stringToByteArray(s);
        Assert.assertThat(byteArray.length,is(2));
    }

    @Test
    public void testLongToByteArray() {
        long number = 280;
        byte[] byteArray = BaseInfo.longToByteArray(number);
        Assert.assertThat(byteArray[7],is((byte)24));
    }

    @Test
    public void testStringToByteList() {
        String s = "aa";
        ArrayList<Byte> byteArray = BaseInfo.stringToByteList(s);
        Assert.assertThat(byteArray.size(),is(2));
    }

    @Test
    public void testLongToByteList() {
        long number = 280;
        byte[] byteArray = BaseInfo.longToByteArray(number);
        long recover = BaseInfo.byteArrayToLong(byteArray);
        Assert.assertThat(byteArray[7],is((byte)24));
        Assert.assertThat(number,is(recover));
    }

    @Test
    public void testIntToByteList() {
        int i = 3;
        ArrayList<Byte> byteList = BaseInfo.intToByteList(i);
        Assert.assertThat(byteList.get(3),is((byte)3));
    }

    @Test
    public void testShortToByteList() {
        short i = 3;
        ArrayList<Byte> byteList = BaseInfo.shortToByteList(i);
        Assert.assertThat(byteList.get(1),is((byte)3));
    }

    @Test
    public void testByteArrayToShort() {
        byte[] byteArray = {(byte)1,(byte)2};
        short number = BaseInfo.byteArrayToShort(byteArray);
        Assert.assertThat(number,is((short)258));
    }

    @Test
    public void testByteArrayToInt() {
        byte[] byteArray = {(byte)0,(byte)0,(byte)1,(byte)2};
        int number = BaseInfo.byteArrayToInt(byteArray);
        Assert.assertThat(number,is(258));
    }

    @Test
    public void testByteArrayToLong() {
        long number = 280;
        byte[] byteArray = BaseInfo.longToByteArray(number);
        long recover = BaseInfo.byteArrayToLong(byteArray);
        Assert.assertThat(byteArray[7],is((byte)24));
        Assert.assertThat(number,is(recover));
    }
}