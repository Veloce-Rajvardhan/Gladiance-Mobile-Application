
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thermostat {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("gendb")
    @Expose
    private Gendb gendb;
    @SerializedName("Power")
    @Expose
    private Boolean power;
    @SerializedName("Ambient")
    @Expose
    private Double ambient;
    @SerializedName("Set")
    @Expose
    private Integer set;
    @SerializedName("Speed")
    @Expose
    private String speed;
    @SerializedName("Mode")
    @Expose
    private String mode;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("WelcomeFlag")
    @Expose
    private Boolean welcomeFlag;
    @SerializedName("evt_src")
    @Expose
    private String evtSrc;
    @SerializedName("map")
    @Expose
    private Map map;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private Integer t;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gendb getGendb() {
        return gendb;
    }

    public void setGendb(Gendb gendb) {
        this.gendb = gendb;
    }

    public Boolean getPower() {
        return power;
    }

    public void setPower(Boolean power) {
        this.power = power;
    }

    public Double getAmbient() {
        return ambient;
    }

    public void setAmbient(Double ambient) {
        this.ambient = ambient;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getWelcomeFlag() {
        return welcomeFlag;
    }

    public void setWelcomeFlag(Boolean welcomeFlag) {
        this.welcomeFlag = welcomeFlag;
    }

    public String getEvtSrc() {
        return evtSrc;
    }

    public void setEvtSrc(String evtSrc) {
        this.evtSrc = evtSrc;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

}
