package org.ict;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class uuidGenerator {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test(@RequestBody uuidParam uP) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonGet = mapper.writeValueAsString(uP);
        System.out.println(jsonGet);
        return jsonGet;
    }
}
