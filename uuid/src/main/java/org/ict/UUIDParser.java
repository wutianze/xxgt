package org.ict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.ict.content.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ResponseInfo{
    public String status;
    public boolean integrity;
    public Map<String,String> infoMap = new HashMap<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIntegrity() {
        return integrity;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }

    public Map<String, String> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, String> infoMap) {
        this.infoMap = infoMap;
    }
}
@RestController
public class UUIDParser {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${org.ict.PREFIX_LENGTH}")
    int PREFIX_LENGTH;

    @Value("${org.ict.CHECK_LENGTH}")
    private int CHECK_LENGTH;

    private final ObjectMapper mapper = new ObjectMapper();



    public boolean integrityCheck(String forCheck, byte[] ID, int cutLength){
        String calculateCheck = (BaseInfo.byteArrayToHexString(DigestUtils.md5Digest(ID))).substring(0,cutLength);
        return forCheck.substring(0,cutLength).equals(calculateCheck);
    }
    public short getInfoTypeFromForeHead(byte[] foreHead){
        return BaseInfo.byteArrayToShort(foreHead);
    }
    public short getInfoLengthFromAfterHead(byte[] afterHead){
        return BaseInfo.byteArrayToShort(afterHead);
    }

    public int findInfo(short toFind, byte[] ID){
        for(int i=0;i<ID.length;i++){
            if(toFind == getInfoTypeFromForeHead(Arrays.copyOfRange(ID,i,i+2))){
                return i;
            }else{
                i = i+4+getInfoLengthFromAfterHead(Arrays.copyOfRange(ID,i+2,i+4));
            }
        }
        return -1;
    }
    public int findInfo(short toFind, String hexID){
        byte[] byteID = BaseInfo.hexStringToByteArray(hexID);
        return findInfo(toFind,byteID);
    }
    private BaseInfo getOneInfo(byte[] pieceID, short infoType){
        switch(infoType){
            case 0:
                TimeInfo timeInfo = new TimeInfo();
                timeInfo.recoverFromID(pieceID);
                return timeInfo;
            case 1:
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.recoverFromID(pieceID);
                return deviceInfo;
            case 2:
                UserInfo userInfo = new UserInfo();
                userInfo.recoverFromID(pieceID);
                return userInfo;
            case 3:
                AlgorithmInfo algorithmInfo = new AlgorithmInfo();
                algorithmInfo.recoverFromID(pieceID);
                return algorithmInfo;
            case 4:
                PowerInfo powerInfo = new PowerInfo();
                powerInfo.recoverFromID(pieceID);
                return powerInfo;
            case 5:
                DataInfo dataInfo = new DataInfo();
                dataInfo.recoverFromID(pieceID);
                return dataInfo;
            case 6:
                QuintupleInfo quintupleInfo = new QuintupleInfo();
                quintupleInfo.recoverFromID(pieceID);
                return quintupleInfo;
            case 7:
                QosInfo qosInfo = new QosInfo();
                qosInfo.recoverFromID(pieceID);
                return qosInfo;
            case 8:
                RandomInfo randomInfo = new RandomInfo();
                randomInfo.recoverFromID(pieceID);
                return randomInfo;
            case 9:
                ContainerInfo containerInfo = new ContainerInfo();
                containerInfo.recoverFromID(pieceID);
                return containerInfo;
            case 10:
                ThreadInfo threadInfo = new ThreadInfo();
                threadInfo.recoverFromID(pieceID);
                return threadInfo;
                default:
                logger.error("invalid info type");
                return null;
        }
    }
private int getInfoNumber(byte[] byteID){
        int number = 0;
        int startIndex = 0;
        while(startIndex<byteID.length){
            startIndex = startIndex+4+getInfoLengthFromAfterHead(Arrays.copyOfRange(byteID,startIndex+2,startIndex+4));
            number++;
    }
        return number;
}
    private int getNumberFromPrefix(String prefix){
        String regEx="[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(prefix);
        return Integer.parseInt(matcher.replaceAll("").trim());
    }
    public boolean checkPrefix(String prefix, byte[] byteID){
        if(prefix.contains("TAG")){
            return findInfo((short)8,byteID)!=-1;
        }
        if(prefix.contains("ALG")){
            return findInfo((short)3,byteID)!=-1;
        }
        if(prefix.contains("POW")){
            return findInfo((short)4,byteID)!=-1;
        }
        if(prefix.contains("DAT")){
            return findInfo((short)5,byteID)!=-1;
        }
        if(prefix.contains("AUT")){
            return findInfo((short) 2, byteID) != -1;
        }
        return getNumberFromPrefix(prefix) == getInfoNumber(byteID);
    }
    @RequestMapping("/integrity")
    public boolean integrity(@PathVariable String forCheck, @PathVariable String hexID){
        return integrityCheck(forCheck,BaseInfo.hexStringToByteArray(hexID),CHECK_LENGTH);
    }

    @ResponseBody
    @RequestMapping("/parse")
    public ResponseInfo parse(String prefixCheckID) throws JsonProcessingException {
        ResponseInfo returnInfo = new ResponseInfo();

        String prefix = prefixCheckID.substring(0,PREFIX_LENGTH);
        String check = prefixCheckID.substring(PREFIX_LENGTH,PREFIX_LENGTH+CHECK_LENGTH);
        byte[] byteID = BaseInfo.hexStringToByteArray(prefixCheckID, PREFIX_LENGTH+CHECK_LENGTH,prefixCheckID.length());
        if(!checkPrefix(prefix,byteID)){
            returnInfo.setStatus("Warning: prefix not match");
        }
        returnInfo.integrity = integrityCheck(check,byteID,CHECK_LENGTH);
        int startIndex = 0;
        while(startIndex<byteID.length){

            short infoType = getInfoTypeFromForeHead(Arrays.copyOfRange(byteID,startIndex,startIndex+2));
            short infoLength = getInfoLengthFromAfterHead(Arrays.copyOfRange(byteID,startIndex+2,startIndex+4));
            BaseInfo pieceInfo = getOneInfo(Arrays.copyOfRange(byteID,startIndex+4,startIndex+4+infoLength),infoType);
            startIndex = startIndex+4+infoLength;

            if (pieceInfo != null) {
                returnInfo.infoMap.put(pieceInfo.getClass().getSimpleName(),mapper.writeValueAsString(pieceInfo));
            }else{
                returnInfo.setStatus(returnInfo.getStatus()+"; Error: Info format parse error");
            }
        }

        return returnInfo;
    }
}
