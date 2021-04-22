package org.ict.content;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;

public class DeviceInfo implements BaseInfo{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${org.ict.MANUFACTURER_ID_LENGTH}")
    private int MANUFACTURER_ID_LENGTH;
    @Value("${org.ict.PRODUCT_ID_LENGTH}")
    private int PRODUCT_ID_LENGTH;
   String manufacturerID;
   String productID;
   short resourceType;
   int resourceID;

    public String getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(String manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public short getResourceType() {
        return resourceType;
    }

    public void setResourceType(short resourceType) {
        this.resourceType = resourceType;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "manufacturerID='" + manufacturerID + '\'' +
                ", productID='" + productID + '\'' +
                ", resourceType=" + resourceType +
                ", resourceID=" + resourceID +
                '}';
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x01);
        returnBytes.add((byte)0x12);
        if(manufacturerID.length() != MANUFACTURER_ID_LENGTH){
            logger.error("manufactureID's length != "+MANUFACTURER_ID_LENGTH);
            return null;
        }
        if(productID.length()!=PRODUCT_ID_LENGTH){
            logger.error("productID's length != "+PRODUCT_ID_LENGTH);
            return null;
        }
        returnBytes.addAll(BaseInfo.stringToBytesList(manufacturerID));
        returnBytes.addAll(BaseInfo.stringToBytesList(productID));
        returnBytes.addAll(BaseInfo.shortToBytesList(resourceType));
        returnBytes.addAll(BaseInfo.intToBytesList(resourceID));
        return returnBytes;
    }
}
