package org.ict;

import java.util.ArrayList;
class contentPiece{
    String type;
    String jsonContent;

    public String getType() {
        return type;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }
}
public class uuidParam {
    String prefix;
    ArrayList<contentPiece>content;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ArrayList<contentPiece> getContent() {
        return content;
    }

    public void setContent(ArrayList<contentPiece> content) {
        this.content = content;
    }


}