package com.gladiance.ui.models.foodmoodlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectTag {

    @SerializedName("Ref")
    @Expose
    private Long ref;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("VideoURL")
    @Expose
    private String videoURL;
    @SerializedName("GAAProjectRBItemCategory")
    @Expose
    private Integer gAAProjectRBItemCategory;
    @SerializedName("GAAProjectRBItemTaste")
    @Expose
    private Integer gAAProjectRBItemTaste;
    @SerializedName("SpiceOrSweetLevel")
    @Expose
    private Integer spiceOrSweetLevel;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("GAAProjectRef")
    @Expose
    private Long gAAProjectRef;
    @SerializedName("GAAProjectName")
    @Expose
    private String gAAProjectName;
    @SerializedName("GAAProjectRBItemTasteName")
    @Expose
    private String gAAProjectRBItemTasteName;
    @SerializedName("GAAProjectRBItemCategoryName")
    @Expose
    private String gAAProjectRBItemCategoryName;

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public Integer getgAAProjectRBItemCategory() {
        return gAAProjectRBItemCategory;
    }

    public void setgAAProjectRBItemCategory(Integer gAAProjectRBItemCategory) {
        this.gAAProjectRBItemCategory = gAAProjectRBItemCategory;
    }

    public Integer getgAAProjectRBItemTaste() {
        return gAAProjectRBItemTaste;
    }

    public void setgAAProjectRBItemTaste(Integer gAAProjectRBItemTaste) {
        this.gAAProjectRBItemTaste = gAAProjectRBItemTaste;
    }

    public Integer getSpiceOrSweetLevel() {
        return spiceOrSweetLevel;
    }

    public void setSpiceOrSweetLevel(Integer spiceOrSweetLevel) {
        this.spiceOrSweetLevel = spiceOrSweetLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getgAAProjectRef() {
        return gAAProjectRef;
    }

    public void setgAAProjectRef(Long gAAProjectRef) {
        this.gAAProjectRef = gAAProjectRef;
    }

    public String getgAAProjectName() {
        return gAAProjectName;
    }

    public void setgAAProjectName(String gAAProjectName) {
        this.gAAProjectName = gAAProjectName;
    }

    public String getgAAProjectRBItemTasteName() {
        return gAAProjectRBItemTasteName;
    }

    public void setgAAProjectRBItemTasteName(String gAAProjectRBItemTasteName) {
        this.gAAProjectRBItemTasteName = gAAProjectRBItemTasteName;
    }

    public String getgAAProjectRBItemCategoryName() {
        return gAAProjectRBItemCategoryName;
    }

    public void setgAAProjectRBItemCategoryName(String gAAProjectRBItemCategoryName) {
        this.gAAProjectRBItemCategoryName = gAAProjectRBItemCategoryName;
    }

    public ObjectTag(Long ref, String name, String videoURL, Integer gAAProjectRBItemCategory, Integer gAAProjectRBItemTaste, Integer spiceOrSweetLevel, String description, Double price, Long gAAProjectRef, String gAAProjectName, String gAAProjectRBItemTasteName, String gAAProjectRBItemCategoryName) {
        this.ref = ref;
        this.name = name;
        this.videoURL = videoURL;
        this.gAAProjectRBItemCategory = gAAProjectRBItemCategory;
        this.gAAProjectRBItemTaste = gAAProjectRBItemTaste;
        this.spiceOrSweetLevel = spiceOrSweetLevel;
        this.description = description;
        this.price = price;
        this.gAAProjectRef = gAAProjectRef;
        this.gAAProjectName = gAAProjectName;
        this.gAAProjectRBItemTasteName = gAAProjectRBItemTasteName;
        this.gAAProjectRBItemCategoryName = gAAProjectRBItemCategoryName;
    }
}
