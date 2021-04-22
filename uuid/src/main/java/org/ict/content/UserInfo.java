package org.ict.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserInfo implements BaseInfo{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Byte userType;
    @Value("${org.ict.USER_KEY_LENGTH}")
    int USER_KEY_LENGTH;
    String userKey;
    int powerAuthority;
    int dataAuthority;

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public int getPowerAuthority() {
        return powerAuthority;
    }

    public void setPowerAuthority(int powerAuthority) {
        this.powerAuthority = powerAuthority;
    }

    public int getDataAuthority() {
        return dataAuthority;
    }

    public void setDataAuthority(int dataAuthority) {
        this.dataAuthority = dataAuthority;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userType=" + userType +
                ", USER_KEY_LENGTH=" + USER_KEY_LENGTH +
                ", userKey='" + userKey + '\'' +
                ", powerAuthority=" + powerAuthority +
                ", dataAuthority=" + dataAuthority +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes(){
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x02);
        returnBytes.add((byte)0x1d);

        //TODO:get info from database
        if(userKey.length() != USER_KEY_LENGTH){
            logger.error("userKey length != "+USER_KEY_LENGTH);
            return null;
        }
        userType = (byte)0x00;
        powerAuthority=100;
        dataAuthority=100;
        //
        returnBytes.add(userType);
        returnBytes.addAll(BaseInfo.stringToBytesList(userKey));
        returnBytes.addAll(BaseInfo.intToBytesList(powerAuthority));
        returnBytes.addAll(BaseInfo.intToBytesList(dataAuthority));
        return returnBytes;
    }

    @Override
    public void recoverFromID(String id) {

    }
}
