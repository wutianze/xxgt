package org.ict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 /**
   * <p>The information extracted from ID</p>
   *
   * @author TianzeWu
   * @date 2021-04-28
   */
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

 /**
   * <p>It was used to extract useful information from ID for users</p>
   *
   *
   * @author TianzeWu
   * @date 2021-04-28
   */
@RestController
public class UUIDParser {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${org.ict.PREFIX_LENGTH}")
    int PREFIX_LENGTH;

    @Value("${org.ict.CHECK_LENGTH}")
    private int CHECK_LENGTH;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Check whether the check code suits the data ID
     *
     * @param forCheck the check code in the full ID
     * @param ID the data ID in the full ID without check code or prefix
     * @return whether the forCheck code suits the data ID
     * @author TianzeWu
     */
    public boolean integrityCheck(String forCheck, byte[] ID, int cutLength){
        String calculateCheck = (BaseInfo.byteArrayToHexString(DigestUtils.md5Digest(ID))).substring(0,cutLength);
        return forCheck.substring(0,cutLength).equals(calculateCheck);
    }

    /**
     * Get the data segment's type
     *
     * @param foreHead the first two bytes of the data head
     * @return the type number of the data segment
     * @author TianzeWu
     */
    public short getInfoTypeFromForeHead(byte[] foreHead){
        return BaseInfo.byteArrayToShort(foreHead);
    }

    /**
     * Get the data segment's length in byte
     *
     * @param afterHead the next two bytes of the data head
     * @return the length of the data segment in byte
     * @author TianzeWu
     */
    public short getInfoLengthFromAfterHead(byte[] afterHead){
        return BaseInfo.byteArrayToShort(afterHead);
    }

    /**
     * Find data segment in data ID
     *
     * @param toFind the type number of the data segment to search
     * @param  ID the data ID byte array
     * @return <code>-1</code> cannot find the data segment; <code>int >= 0</code> index in the data ID of the data segment's head
     * @author TianzeWu
     */
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

    /**
     * Find one type of data segment in data ID
     *
     * @param toFind the type number of the data segment to search
     * @param hexID the data ID string
     * @return <code>-1</code> cannot find the data segment; <code>int >= 0</code> index in the data ID of the data segment's head
     * @author TianzeWu
     */
    @RequestMapping(value="/findInfo",method=RequestMethod.GET)
    public int findInfo(short toFind, String hexID){
        byte[] byteID = BaseInfo.hexStringToByteArray(hexID);
        return findInfo(toFind,byteID);
    }

    /**
     * Extract information from data segment byte array
     *
     * @param pieceID data segment byte array
     * @return <code>null</code> if do not know the type of the data segment; <code>different Info class</code> the Info class recovered from pieceID
     * @author TianzeWu
     */
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

    /**
     * How many Infos in this data ID
     *
     * @param byteID data ID byte array
     * @return the number of Infos in this data ID
     * @author TianzeWu
     */
    private int getInfoNumber(byte[] byteID){
        int number = 0;
        int startIndex = 0;
        while(startIndex<byteID.length){
            startIndex = startIndex+4+getInfoLengthFromAfterHead(Arrays.copyOfRange(byteID,startIndex+2,startIndex+4));
            number++;
        }
        return number;
    }

    /**
     * Get how many data segments in the ID from prefix
     *
     * @param prefix prefix in the ID
     * @return number of data segments
     * @author TianzeWu
     */
    private int getNumberFromPrefix(String prefix){
        String regEx="[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(prefix);
        return Integer.parseInt(matcher.replaceAll("").trim());
    }

    /**
     * Check whether the prefix suits the data ID
     *
     * @param prefix prefix in the ID
     * @param byteID data ID
     * @return whether the prefix suits the data ID
     * @author TianzeWu
     */
    public boolean checkPrefix(String prefix, byte[] byteID){
        if(prefix.contains("TAG")){
            return findInfo((short)7,byteID)!=-1;
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

    /**
     * Invoke integrityCheck to check whether the check code suits the data ID
     *
     * @param forCheck the check code in the full ID
     * @param hexID the data ID
     * @return whether the forCheck code suits the data ID
     * @author TianzeWu
     */
    @RequestMapping(value="/integrity",method=RequestMethod.GET)
    public boolean integrity(String forCheck, String hexID){
        return integrityCheck(forCheck,BaseInfo.hexStringToByteArray(hexID),CHECK_LENGTH);
    }

    /**
     * Extract information from the ID
     *
     * @param prefixCheckID the ID which contains prefix, check code and data ID
     * @return the information extracted from the ID
     * @author TianzeWu
     */
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
