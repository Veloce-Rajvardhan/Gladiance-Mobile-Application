package com.gladiance.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.gladiance.AppConstants;

@Entity(tableName = AppConstants.NOTIFICATION_TABLE)
public class NotificationEvent implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long notificationId;

    @ColumnInfo(name = AppConstants.KEY_EVENT_VERSION)
    private String eventVersion;

    @ColumnInfo(name = AppConstants.KEY_EVENT_TYPE)
    private String eventType;

    @ColumnInfo(name = AppConstants.KEY_DESCRIPTION)
    private String eventDescription;

    @ColumnInfo(name = AppConstants.KEY_ID)
    private String eventId;

    @ColumnInfo(name = AppConstants.KEY_EVENT_DATA)
    private String eventData;

    @ColumnInfo(name = AppConstants.KEY_TIMESTAMP)
    private long timestamp;

    @ColumnInfo(name = AppConstants.KEY_NOTIFICATION_MSG)
    private String notificationMsg;

    public NotificationEvent() {
    }

    protected NotificationEvent(Parcel in) {
        notificationId = in.readLong();
        eventVersion = in.readString();
        eventType = in.readString();
        eventDescription = in.readString();
        eventId = in.readString();
        eventData = in.readString();
        timestamp = in.readLong();
        notificationMsg = in.readString();
    }

    public static final Creator<NotificationEvent> CREATOR = new Creator<NotificationEvent>() {
        @Override
        public NotificationEvent createFromParcel(Parcel in) {
            return new NotificationEvent(in);
        }

        @Override
        public NotificationEvent[] newArray(int size) {
            return new NotificationEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(notificationId);
        dest.writeString(eventVersion);
        dest.writeString(eventType);
        dest.writeString(eventDescription);
        dest.writeString(eventId);
        dest.writeString(eventData);
        dest.writeLong(timestamp);
        dest.writeString(notificationMsg);
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }
}
