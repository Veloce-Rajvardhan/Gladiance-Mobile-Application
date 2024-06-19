
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Gendbarr {

    @SerializedName("Speed")
    @Expose
    private Speed speed;
    @SerializedName("set_range")
    @Expose
    private SetRange setRange;
    @SerializedName("keep_time_sec")
    @Expose
    private Integer keepTimeSec;
    @SerializedName("scan_interval_sec")
    @Expose
    private Integer scanIntervalSec;
    @SerializedName("occu")
    @Expose
    private Occu occu;
    @SerializedName("unoc")
    @Expose
    private Unoc unoc;
    @SerializedName("sfyt")
    @Expose
    private Sfyt sfyt;
    @SerializedName("keytag_sensor")
    @Expose
    private Boolean keytagSensor;
    @SerializedName("occupancy_sensor")
    @Expose
    private Boolean occupancySensor;
    @SerializedName("trans_time_sec")
    @Expose
    private Integer transTimeSec;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;
    @SerializedName("b_timeout")
    @Expose
    private Integer bTimeout;
    @SerializedName("b_tx_time")
    @Expose
    private Integer bTxTime;

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public SetRange getSetRange() {
        return setRange;
    }

    public void setSetRange(SetRange setRange) {
        this.setRange = setRange;
    }

    public Integer getKeepTimeSec() {
        return keepTimeSec;
    }

    public void setKeepTimeSec(Integer keepTimeSec) {
        this.keepTimeSec = keepTimeSec;
    }

    public Integer getScanIntervalSec() {
        return scanIntervalSec;
    }

    public void setScanIntervalSec(Integer scanIntervalSec) {
        this.scanIntervalSec = scanIntervalSec;
    }

    public Occu getOccu() {
        return occu;
    }

    public void setOccu(Occu occu) {
        this.occu = occu;
    }

    public Unoc getUnoc() {
        return unoc;
    }

    public void setUnoc(Unoc unoc) {
        this.unoc = unoc;
    }

    public Sfyt getSfyt() {
        return sfyt;
    }

    public void setSfyt(Sfyt sfyt) {
        this.sfyt = sfyt;
    }

    public Boolean getKeytagSensor() {
        return keytagSensor;
    }

    public void setKeytagSensor(Boolean keytagSensor) {
        this.keytagSensor = keytagSensor;
    }

    public Boolean getOccupancySensor() {
        return occupancySensor;
    }

    public void setOccupancySensor(Boolean occupancySensor) {
        this.occupancySensor = occupancySensor;
    }

    public Integer getTransTimeSec() {
        return transTimeSec;
    }

    public void setTransTimeSec(Integer transTimeSec) {
        this.transTimeSec = transTimeSec;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Integer getbTimeout() {
        return bTimeout;
    }

    public void setbTimeout(Integer bTimeout) {
        this.bTimeout = bTimeout;
    }

    public Integer getbTxTime() {
        return bTxTime;
    }

    public void setbTxTime(Integer bTxTime) {
        this.bTxTime = bTxTime;
    }

}
