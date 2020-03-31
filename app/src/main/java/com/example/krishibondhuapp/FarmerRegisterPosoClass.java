package com.example.krishibondhuapp;

public class FarmerRegisterPosoClass {

    private String fName;
    private String fPhone;
    private String fNid;
    private String fImageurl;

    public FarmerRegisterPosoClass() {

    }

    public FarmerRegisterPosoClass(String fName, String fPhone, String fNid, String fImageurl) {
        this.fName = fName;
        this.fPhone = fPhone;
        this.fNid = fNid;
        this.fImageurl = fImageurl;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfPhone() {
        return fPhone;
    }

    public void setfPhone(String fPhone) {
        this.fPhone = fPhone;
    }

    public String getfNid() {
        return fNid;
    }

    public void setfNid(String fNid) {
        this.fNid = fNid;
    }

    public String getfImageurl() {
        return fImageurl;
    }

    public void setfImageurl(String fImageurl) {
        this.fImageurl = fImageurl;
    }
}
