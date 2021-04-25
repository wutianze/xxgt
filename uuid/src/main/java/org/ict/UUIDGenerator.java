package org.ict;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ict.content.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    @Value("${org.ict.PREFIX_LENGTH}")
    private int PREFIX_LENGTH;
    @Value("${org.ict.CHECK_LENGTH}")
    private int CHECK_LENGTH;
    private ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping("/generate")
    public ResponseID generate(@RequestBody UUIDParam paramJSON) throws IOException {

        ResponseID responseID = new ResponseID();
        ArrayList<Byte>tmpID = new ArrayList<>();

        //build prefix
        StringBuilder tmpPrefix = new StringBuilder(paramJSON.getPrefix());
        if(tmpPrefix.length() >= PREFIX_LENGTH){
            tmpPrefix = new StringBuilder(tmpPrefix.substring(0, PREFIX_LENGTH));
        }else{
            for(int i = tmpPrefix.length();i<PREFIX_LENGTH;i++){
                tmpPrefix.append(' ');
            }
        }
        responseID.setPrefix(tmpPrefix.toString());

        //build content
        ArrayList<ContentPiece> tmpContent = paramJSON.getContent();
        try{
        for(ContentPiece c : tmpContent){
            ArrayList<Byte> newBytes = null;
            switch(c.getType()){
               case "timeInfo":
                   TimeInfo timeInfo = new TimeInfo();
                   newBytes = timeInfo.generateBytes();
                   break;
               case "deviceInfo":
                   DeviceInfo deviceInfo = mapper.readValue(c.getJsonContent(), DeviceInfo.class);
                   newBytes = deviceInfo.generateBytes();
                   break;
               case "randomInfo":
                   RandomInfo randomInfo = new RandomInfo();
                   newBytes = randomInfo.generateBytes();
                   break;
               case "userInfo":
                   UserInfo userInfo = mapper.readValue(c.getJsonContent(),UserInfo.class);
                   newBytes = userInfo.generateBytes();
                   break;
                case "algorithmInfo":
                    AlgorithmInfo algorithmInfo = mapper.readValue(c.getJsonContent(),AlgorithmInfo.class);
                    newBytes = algorithmInfo.generateBytes();
                    break;
                case "powerInfo":
                    PowerInfo powerInfo = mapper.readValue(c.getJsonContent(),PowerInfo.class);
                    newBytes = powerInfo.generateBytes();
                    break;
                case "dataInfo":
                    DataInfo dataInfo = mapper.readValue(c.getJsonContent(),DataInfo.class);
                    newBytes = dataInfo.generateBytes();
                    break;
                case "quintupleInfo":
                    QuintupleInfo quintupleInfo = mapper.readValue(c.getJsonContent(),QuintupleInfo.class);
                    newBytes = quintupleInfo.generateBytes();
                    break;
                case "qosInfo":
                    QosInfo qosInfo = mapper.readValue(c.getJsonContent(),QosInfo.class);
                    newBytes = qosInfo.generateBytes();
                    break;
                case "containerInfo":
                    ContainerInfo containerInfo = mapper.readValue(c.getJsonContent(),ContainerInfo.class);
                    newBytes = containerInfo.generateBytes();
                    break;
                case "threadInfo":
                    ThreadInfo threadInfo = mapper.readValue(c.getJsonContent(),ThreadInfo.class);
                    newBytes = threadInfo.generateBytes();
                    break;
               default:
                   break;
           }
           if(newBytes == null){
               responseID.setStatus("generate ID fail");
               return responseID;
           }else{
               tmpID.addAll(newBytes);
           }
        }
        }
        catch(JsonParseException e){
            responseID.setStatus("parse json error");
            return responseID;
        }

        responseID.setStatus("success");
        byte[] finalID = new byte[tmpID.size()];
        for(int i=0;i<tmpID.size();i++){
            finalID[i] = tmpID.get(i);
        }
        String finalIDString = BaseInfo.byteArrayToHexString(finalID);
        logger.info(finalIDString);
        logger.info(String.valueOf(finalIDString.length()));
        responseID.setCheck((BaseInfo.byteArrayToHexString(DigestUtils.md5Digest(finalID))).substring(0,CHECK_LENGTH));// return 16 bytes
        logger.info(String.valueOf(responseID.getCheck().length()));
        responseID.setId(finalIDString);
        return responseID;
    }
}
