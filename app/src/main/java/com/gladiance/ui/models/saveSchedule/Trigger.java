package com.gladiance.ui.models.saveSchedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trigger {
    @SerializedName("Monday")
    @Expose
    private Boolean monday;
    @SerializedName("Tuesday")
    @Expose
    private Boolean tuesday;
    @SerializedName("Wednesday")
    @Expose
    private Boolean wednesday;
    @SerializedName("Thursday")
    @Expose
    private Boolean thursday;
    @SerializedName("Friday")
    @Expose
    private Boolean friday;
    @SerializedName("Saturday")
    @Expose
    private Boolean saturday;
    @SerializedName("Sunday")
    @Expose
    private Boolean sunday;
    @SerializedName("Hour")
    @Expose
    private Integer hour;
    @SerializedName("Minute")
    @Expose
    private Integer minute;
    @SerializedName("Second")
    @Expose
    private Integer second;
    @SerializedName("DayOfMonth")
    @Expose
    private Integer dayOfMonth;
    @SerializedName("January")
    @Expose
    private Boolean january;
    @SerializedName("February")
    @Expose
    private Boolean february;
    @SerializedName("March")
    @Expose
    private Boolean march;
    @SerializedName("April")
    @Expose
    private Boolean april;
    @SerializedName("May")
    @Expose
    private Boolean may;
    @SerializedName("June")
    @Expose
    private Boolean june;
    @SerializedName("July")
    @Expose
    private Boolean july;
    @SerializedName("August")
    @Expose
    private Boolean august;
    @SerializedName("September")
    @Expose
    private Boolean september;
    @SerializedName("October")
    @Expose
    private Boolean october;
    @SerializedName("November")
    @Expose
    private Boolean november;
    @SerializedName("December")
    @Expose
    private Boolean december;
    @SerializedName("Year")
    @Expose
    private Integer year;
    @SerializedName("RepeatEveryYear")
    @Expose
    private Boolean repeatEveryYear;

    public Trigger(Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Integer hour, Integer minute, Integer second, Integer dayOfMonth, Boolean january, Boolean february, Boolean march, Boolean april, Boolean may, Boolean june, Boolean july, Boolean august, Boolean september, Boolean october, Boolean november, Boolean december, Integer year, Boolean repeatEveryYear) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.dayOfMonth = dayOfMonth;
        this.january = january;
        this.february = february;
        this.march = march;
        this.april = april;
        this.may = may;
        this.june = june;
        this.july = july;
        this.august = august;
        this.september = september;
        this.october = october;
        this.november = november;
        this.december = december;
        this.year = year;
        this.repeatEveryYear = repeatEveryYear;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Boolean getJanuary() {
        return january;
    }

    public void setJanuary(Boolean january) {
        this.january = january;
    }

    public Boolean getFebruary() {
        return february;
    }

    public void setFebruary(Boolean february) {
        this.february = february;
    }

    public Boolean getMarch() {
        return march;
    }

    public void setMarch(Boolean march) {
        this.march = march;
    }

    public Boolean getApril() {
        return april;
    }

    public void setApril(Boolean april) {
        this.april = april;
    }

    public Boolean getMay() {
        return may;
    }

    public void setMay(Boolean may) {
        this.may = may;
    }

    public Boolean getJune() {
        return june;
    }

    public void setJune(Boolean june) {
        this.june = june;
    }

    public Boolean getJuly() {
        return july;
    }

    public void setJuly(Boolean july) {
        this.july = july;
    }

    public Boolean getAugust() {
        return august;
    }

    public void setAugust(Boolean august) {
        this.august = august;
    }

    public Boolean getSeptember() {
        return september;
    }

    public void setSeptember(Boolean september) {
        this.september = september;
    }

    public Boolean getOctober() {
        return october;
    }

    public void setOctober(Boolean october) {
        this.october = october;
    }

    public Boolean getNovember() {
        return november;
    }

    public void setNovember(Boolean november) {
        this.november = november;
    }

    public Boolean getDecember() {
        return december;
    }

    public void setDecember(Boolean december) {
        this.december = december;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getRepeatEveryYear() {
        return repeatEveryYear;
    }

    public void setRepeatEveryYear(Boolean repeatEveryYear) {
        this.repeatEveryYear = repeatEveryYear;
    }

}