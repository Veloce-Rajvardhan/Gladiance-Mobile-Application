package com.gladiance.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule implements Parcelable {
    private String id;
    private String name;
    private boolean isEnabled;
    private ArrayList<Action> actions;
    private HashMap<String, Integer> triggers;

    public Schedule() {
    }

    protected Schedule(Parcel in) {
        id = in.readString();
        name = in.readString();
        isEnabled = in.readByte() != 0;
        actions = in.createTypedArrayList(Action.CREATOR);
        triggers = (HashMap<String, Integer>) in.readSerializable();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public HashMap<String, Integer> getTriggers() {
        return triggers;
    }

    public void setTriggers(HashMap<String, Integer> triggers) {
        this.triggers = triggers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (isEnabled ? 1 : 0));
        dest.writeTypedList(actions);
        dest.writeSerializable(triggers);
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}