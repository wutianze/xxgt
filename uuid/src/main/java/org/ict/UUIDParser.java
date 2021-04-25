package org.ict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.ict.content.BaseInfo;
import org.ict.content.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ResponseInfo{
    public boolean integrity;
    public Map<String,String> IDInfos = new HashMap<String,String>();

    public boolean isIntegrity() {
        return integrity;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }

    public Map<String, String> getIDInfos() {
        return IDInfos;
    }

    public void setIDInfos(Map<String, String> IDInfos) {
        this.IDInfos = IDInfos;
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



    public boolean integrityCheck(String forCheck, byte[] id, int cutLength){
        return forCheck.substring(0,cutLength).equals((BaseInfo.byteArrayToHexString(DigestUtils.md5Digest(id))).substring(0,cutLength));
    }
    public short getInfoTypeFromForeHead(byte[] foreHead){
        return BaseInfo.byteArrayToShort(foreHead);
    }
    public short getInfoLengthFromAfterHead(byte[] afterHead){
        return BaseInfo.byteArrayToShort(afterHead);
    }
    public int findInfo(short toFind, String hexID){
        byte[] idByte = BaseInfo.hexStringToByteArray(hexID);
        for(int i=0;i<idByte.length;i++){
            if(toFind == getInfoTypeFromForeHead(Arrays.copyOfRange(idByte,i,i+2))){
                return i;
            }else{
                i = i+4+getInfoLengthFromAfterHead(Arrays.copyOfRange(idByte,i+2,i+4));
            }
        }
        return -1;
    }

    private BaseInfo getOneInfo(byte[] id, short infoType){
        switch(infoType){
            case 0:
                TimeInfo timeInfo = new TimeInfo();
                timeInfo.recoverFromID(id);
                return timeInfo;
            default:
                logger.error("invalid info type");
                return null;
        }
    }

    @RequestMapping("/integrity")
    public boolean integrity(@PathVariable String forCheck, @PathVariable String idHexString){
        return integrityCheck(forCheck,BaseInfo.hexStringToByteArray(idHexString),CHECK_LENGTH);
    }

    @ResponseBody
    @RequestMapping("/parse")
    public ResponseInfo parse(String uuidQuery) throws JsonProcessingException {
        ResponseInfo returnInfo = new ResponseInfo();

        String prefix = uuidQuery.substring(0,PREFIX_LENGTH);

        String check = uuidQuery.substring(PREFIX_LENGTH,PREFIX_LENGTH+CHECK_LENGTH);
        byte[] idBytes = BaseInfo.hexStringToByteArray(uuidQuery, PREFIX_LENGTH+CHECK_LENGTH,uuidQuery.length());
        returnInfo.integrity = integrityCheck(check,idBytes,CHECK_LENGTH);
        int startIndex = 0;
        while(startIndex<idBytes.length){

            short infoType = getInfoTypeFromForeHead(Arrays.copyOfRange(idBytes,startIndex,startIndex+2));
            short infoLength = getInfoLengthFromAfterHead(Arrays.copyOfRange(idBytes,startIndex+2,startIndex+4));
            BaseInfo pieceInfo = getOneInfo(Arrays.copyOfRange(idBytes,startIndex+4,startIndex+4+infoLength),infoType);
            startIndex = startIndex+4+infoLength;

            returnInfo.IDInfos.put(pieceInfo.getClass().getSimpleName(),pieceInfo.toString());
            String jsonString = mapper.writeValueAsString(pieceInfo);
            System.out.println(jsonString);
        }

        return returnInfo;
    }
}
