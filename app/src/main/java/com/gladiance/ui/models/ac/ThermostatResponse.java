
package com.gladiance.ui.models.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThermostatResponse {

    @SerializedName("Thermostat")
    @Expose
    private Thermostat thermostat;
    @SerializedName("System")
    @Expose
    private System system;
    @SerializedName("Time")
    @Expose
    private Time time;
    @SerializedName("Schedule")
    @Expose
    private Schedule schedule;
    @SerializedName("Scenes")
    @Expose
    private Scenes scenes;
    @SerializedName("LocalControl")
    @Expose
    private LocalControl localControl;

    public Thermostat getThermostat() {
        return thermostat;
    }

    public void setThermostat(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Scenes getScenes() {
        return scenes;
    }

    public void setScenes(Scenes scenes) {
        this.scenes = scenes;
    }

    public LocalControl getLocalControl() {
        return localControl;
    }

    public void setLocalControl(LocalControl localControl) {
        this.localControl = localControl;
    }

}
