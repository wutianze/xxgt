package org.ict;

import java.util.ArrayList;

class ContentPiece{
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

public class UUIDParam {
    String prefix;
    ArrayList<ContentPiece>content;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ArrayList<ContentPiece> getContent() {
        return content;
    }

    public void setContent(ArrayList<ContentPiece> content) {
        this.content = content;
    }


}