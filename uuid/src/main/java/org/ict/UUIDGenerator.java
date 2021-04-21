package org.ict;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ict.content.BaseInfo;
import org.ict.content.DeviceInfo;
import org.ict.content.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

class ResponseID{
    String status;
    String prefix;
    String check;
    String id;

    public String getStatus() {
        return status;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCheck() {
        return check;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public void setId(String id) {
        this.id = id;
    }
}

@RestController
public class UUIDGenerator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int PREFIX_LEN = 10;

    @ResponseBody
    @RequestMapping("/generate")
    public ResponseID generate(@RequestBody UUIDParam paramJSON) throws IOException {
        ResponseID responseID = new ResponseID();
        ArrayList<Byte>tmpID = new ArrayList<>();

        //build prefix
        StringBuilder tmpPrefix = new StringBuilder(paramJSON.getPrefix());
        if(tmpPrefix.length() >= PREFIX_LEN){
            tmpPrefix = new StringBuilder(tmpPrefix.substring(0, PREFIX_LEN));
        }else{
            for(int i = tmpPrefix.length();i<PREFIX_LEN;i++){
                tmpPrefix.append(' ');
            }
        }
        responseID.setPrefix(tmpPrefix.toString());

        //build content
        ArrayList<ContentPiece> tmpContent = paramJSON.getContent();
        ObjectMapper mapper = new ObjectMapper();
        try{
        for(ContentPiece c : tmpContent){
           switch(c.getType()){
               case "timeInfo":
                   TimeInfo timeInfo = new TimeInfo();
                   tmpID.addAll(timeInfo.generateBytes());
                   break;
               case "deviceInfo":
                   DeviceInfo deviceInfo = mapper.readValue(c.getJsonContent(), DeviceInfo.class);
                   tmpID.addAll(deviceInfo.generateBytes());
                   break;
               default:
                   break;
           }
        }
        }
        catch(JsonParseException e){
            System.out.println("Error Request Json");
            responseID.setStatus("parse json error");
            return responseID;
        }

        responseID.setStatus("success");
        byte[] finalID = new byte[tmpID.size()];
        for(int i=0;i<tmpID.size();i++){
            finalID[i] = tmpID.get(i);
        }
        String finalIDString = BaseInfo.bytesToString(finalID);
        logger.info(finalIDString);
        logger.info(String.valueOf(finalIDString.length()));
        responseID.setCheck(BaseInfo.bytesToString(DigestUtils.md5Digest(finalID)));// return 16 bytes
        logger.info(String.valueOf(responseID.getCheck().length()));
        responseID.setId(finalIDString);
        return responseID;
    }
}
