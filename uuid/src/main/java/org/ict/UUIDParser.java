package org.ict;

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
import java.util.Map;

class ResponseInfo{
    public boolean integrity;
    public Map<String,String> IDInfos;

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

    private BaseInfo getOneInfo(byte[] id, Integer startIndex){
        int endIndex = startIndex+2;
        int infoType = BaseInfo.bytesToShort(Arrays.copyOfRange(id,startIndex,endIndex));
        startIndex = endIndex;
        endIndex = endIndex+2;
        int infoLength = BaseInfo.bytesToShort(Arrays.copyOfRange(id,startIndex,endIndex));
        startIndex = endIndex;
        endIndex = endIndex+infoLength;
        switch(infoType){
            case 0:
                TimeInfo timeInfo = new TimeInfo();
                timeInfo.recoverFromID(Arrays.copyOfRange(id,startIndex,endIndex));
                startIndex = endIndex;
                return timeInfo;
            default:
                logger.error("invalid info type");
                return null;
        }
    }

    public boolean integrityCheck(String forCheck, byte[] content, int cutLength){
        return forCheck.equals((BaseInfo.bytesToString(DigestUtils.md5Digest(content))).substring(0,cutLength));
    }

    @RequestMapping("/integrity")
    public boolean integrity(@PathVariable String forCheck, @PathVariable String contentStr){
        return integrityCheck(forCheck,BaseInfo.stringToBytesArray(contentStr),CHECK_LENGTH);
    }

    @ResponseBody
    @RequestMapping("/parse")
    public ResponseInfo parse(@PathVariable String uuidQuery){
        ResponseInfo returnInfo = new ResponseInfo();
        byte[] idBytes = BaseInfo.stringToBytesArray(uuidQuery);
        String prefix = BaseInfo.bytesToString(idBytes,0,PREFIX_LENGTH);
        String check = BaseInfo.bytesToString(idBytes,PREFIX_LENGTH,PREFIX_LENGTH+CHECK_LENGTH);
        byte[] id = Arrays.copyOfRange(idBytes,PREFIX_LENGTH+CHECK_LENGTH,idBytes.length);
        returnInfo.integrity = integrityCheck(check,id,CHECK_LENGTH);
        Integer startIndex = 0;
        while(startIndex<id.length){
            BaseInfo pieceInfo = getOneInfo(id,startIndex);
            returnInfo.IDInfos.put(pieceInfo.getClass().getSimpleName(),pieceInfo.toString());
        }

        return returnInfo;
    }
}