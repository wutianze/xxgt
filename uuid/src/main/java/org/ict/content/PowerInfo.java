package org.ict.content;

import java.util.ArrayList;

public class PowerInfo implements BaseInfo{
    int powerType;
    int powerAbility;
    String powerOwner;
    int powerAuthority;

    public int getPowerType() {
        return powerType;
    }

    public void setPowerType(int powerType) {
        this.powerType = powerType;
    }

    public int getPowerAbility() {
        return powerAbility;
    }

    public void setPowerAbility(int powerAbility) {
        this.powerAbility = powerAbility;
    }

    public String getPowerOwner() {
        return powerOwner;
    }

    public void setPowerOwner(String powerOwner) {
        this.powerOwner = powerOwner;
    }

    public int getPowerAuthority() {
        return powerAuthority;
    }

    public void setPowerAuthority(int powerAuthority) {
        this.powerAuthority = powerAuthority;
    }

    @Override
    public ArrayList<Byte> generateBytes(){
        ArrayList<Byte> returnBytes = new ArrayList<>();
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x00);
        returnBytes.add((byte)0x04);
        returnBytes.add((byte)0x18);

        returnBytes.addAll(BaseInfo.intToBytesList(powerType));
        returnBytes.addAll(BaseInfo.intToBytesList(powerAbility));
        returnBytes.addAll(BaseInfo.stringToBytesList(powerOwner));
        returnBytes.addAll(BaseInfo.intToBytesList(powerAuthority));
        return returnBytes;
    }

}
