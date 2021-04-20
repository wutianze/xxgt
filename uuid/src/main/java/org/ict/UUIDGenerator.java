package org.ict;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ict.content.DeviceInfo;
import org.ict.content.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

class ResponseID{
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
public class UUIDGenerator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int PREFIX_LEN = 8;

    @ResponseBody
    @RequestMapping("/generate")
    public ResponseID generate(@RequestBody UUIDParam uP) throws IOException {
        long now = System.currentTimeMillis();
        System.out.println(now);
        ResponseID rI = new ResponseID();
        StringBuilder id= new StringBuilder();

        //build prefix
        StringBuilder tmpPrefix = new StringBuilder(uP.getPrefix());
        if(tmpPrefix.length() >= PREFIX_LEN){
            id = new StringBuilder(tmpPrefix.substring(0, 8));
        }else{
            for(int i = tmpPrefix.length();i<PREFIX_LEN;i++){
                tmpPrefix.append(' ');
            }
            logger.info("prefix generated:");
            logger.info(tmpPrefix.toString());
        }

        //build content
        ArrayList<ContentPiece> tmpContent = uP.getContent();
        ObjectMapper mapper = new ObjectMapper();
        try{
        for(ContentPiece c : tmpContent){
           switch(c.getType()){
               case "timeInfo":
                   TimeInfo tI = new TimeInfo();
                   id.append(tI.generateString());
                   break;
               case "deviceInfo":
                   DeviceInfo dI = mapper.readValue(c.getJsonContent(), DeviceInfo.class);
                   id.append(dI.generateString());
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
        rI.setId(id.toString());
        return rI;
    }
}
