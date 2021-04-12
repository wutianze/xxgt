package org.ict;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ict.content.deviceId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

class responseId{
    String status;
    String id;

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }
}
@RestController
public class uuidGenerator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int PrefixLen = 8;

    @ResponseBody
    @RequestMapping("/generate")
    public responseId generate(@RequestBody uuidParam uP) throws IOException {
        long now = System.currentTimeMillis();
        System.out.println(now);
        responseId rI = new responseId();
        String id="";
        StringBuilder tmpPrefix = new StringBuilder(uP.getPrefix());
        if(tmpPrefix.length() >= PrefixLen){
            id = tmpPrefix.substring(0,8);
        }else{
            for(int i = tmpPrefix.length();i<PrefixLen;i++){
                tmpPrefix.append(' ');
            }
            logger.info("prefix generated:");
            logger.info(tmpPrefix.toString());
        }
        ArrayList<contentPiece> tmpContent = uP.getContent();
        ObjectMapper mapper = new ObjectMapper();
        try{
        for(contentPiece c : tmpContent){
           switch(c.getType()){
               case "timestamp":
                   break;
               case "device":
                   deviceId dI = mapper.readValue(c.getJsonContent(),deviceId.class);
                   break;
               default:
                   break;
           }
        }
        }
        catch(JsonParseException e){
            System.out.println("Error Request Json");
            rI.setStatus("parse json error");
            return rI;
        }
        rI.setStatus("success");
        rI.setId(id);
        return rI;
    }
}
