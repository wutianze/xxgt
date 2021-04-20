package org.ict.content;

public class DeviceInfo implements BaseInfo{
    boolean a; //default false

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isA() {
        return a;
    }

    @Override
    public String generateString() {
        return null;
    }
}
