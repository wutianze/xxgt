package org.ict;

import java.util.ArrayList;
 /**
   * <p>This class was used to get jsonContent from user's request body to generate an ID. Different type of data has different format of "jsonContent".</p>
   *
   * @author TianzeWu
   * @date 2021-04-28
   */
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

 /**
   * <p>The class is used to parse the JSON from user's request to generate a new ID,
   * JSON example:{"prefix":"UniTimDev4","content":[{"type":"timeInfo"},{"type":"containerInfo","jsonContent":"{\"containerID\":\"abcdef\",\"imageID\":\"abcdef\",\"containerPID\":3333}"}]}. </p>
   *
   * @author TianzeWu
   * @date 2021-04-28
   */
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