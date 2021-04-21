package org.ict.content;

import com.fasterxml.jackson.databind.util.ArrayBuilders;

import java.util.ArrayList;

public class DeviceInfo implements BaseInfo{
    boolean a; //default false

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isA() {
        return a;
    }

    @Override
    public ArrayList<Byte> generateBytes() {
        return null;
    }
}
