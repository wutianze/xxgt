package org.ict.content;

public class deviceInfo implements Info{
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
