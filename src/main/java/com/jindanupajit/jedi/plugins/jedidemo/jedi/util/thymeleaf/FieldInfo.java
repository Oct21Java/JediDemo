package com.jindanupajit.jedi.plugins.jedidemo.jedi.util.thymeleaf;

public class FieldInfo implements Comparable<FieldInfo> {

    private String name;
    private String type;

    private int ordinal;
    private String label;
    private String placeHolder;
    private boolean secret;
    private boolean id;
    private boolean generatedValue;
    private boolean notNull;
    private boolean notEmpty;
    private boolean notBlank;
    private boolean min;
    private long minValue;
    private boolean max;
    private long maxValue;
    private boolean size;
    private int sizeMinValue;
    private int sizeMaxValue;

    public FieldInfo(String name, String type) {
        this.name = name;
        this.type = type;
        this.placeHolder = "";
        this.ordinal = Integer.MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isGeneratedValue() {
        return generatedValue;
    }

    public void setGeneratedValue(boolean generatedValue) {
        this.generatedValue = generatedValue;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public boolean isNotBlank() {
        return notBlank;
    }

    public void setNotBlank(boolean notBlank) {
        this.notBlank = notBlank;
    }

    public boolean isMin() {
        return min;
    }

    public void setMin(boolean min) {
        this.min = min;
    }

    public long getMinValue() {
        return minValue;
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isSize() {
        return size;
    }

    public void setSize(boolean size) {
        this.size = size;
    }

    public int getSizeMinValue() {
        return sizeMinValue;
    }

    public void setSizeMinValue(int sizeMinValue) {
        this.sizeMinValue = sizeMinValue;
    }

    public int getSizeMaxValue() {
        return sizeMaxValue;
    }

    public void setSizeMaxValue(int sizeMaxValue) {
        this.sizeMaxValue = sizeMaxValue;
    }

    @Override
    public int compareTo(FieldInfo o) {
        return Integer.compare(this.getOrdinal(), o.getOrdinal());
    }
}
