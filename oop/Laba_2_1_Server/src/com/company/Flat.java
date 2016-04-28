package com.company;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Flat {
    private final SimpleIntegerProperty flatNumber;
    private final SimpleDoubleProperty flatSquare;
    private final SimpleIntegerProperty flatFloor;
    private final SimpleIntegerProperty countRooms;
    private final SimpleStringProperty typeBuild;
    private final SimpleStringProperty expluatationTime;

    public Flat(Integer flatNumber, Double flatSquare, Integer flatFloor, Integer countRooms, String typeBuild, String expluatationTime) {
        this.flatNumber = new SimpleIntegerProperty(flatNumber);
        this.flatSquare = new SimpleDoubleProperty(flatSquare);
        this.flatFloor = new SimpleIntegerProperty(flatFloor);
        this.countRooms = new SimpleIntegerProperty(countRooms);
        this.typeBuild = new SimpleStringProperty(typeBuild);
        this.expluatationTime = new SimpleStringProperty(expluatationTime);
    }

    public Integer getFlatNumber() {
        return this.flatNumber.get();
    }

    public String serializeObj() {
        String res = "";
        res += "{\"" + this.getFlatNumber() + "\",";
        res += "\"" + this.getFlatSquare() + "\",";
        res += "\"" + this.getFlatFloor() + "\",";
        res += "\"" + this.getCountRooms() + "\",";
        res += "\"" + this.getTypeBuild() + "\",";
        res += "\"" + this.getExpluatationTime() + "\"}";
        return res;
    }

    public static Flat deserializeFlat(String str) {
        str = str.substring(1, str.length() - 1);

        String[] str_split = str.split(",");

        int flatNumber = Integer.parseInt(str_split[0].substring(1, str_split[0].length() - 1));
        double flatSquare = Double.parseDouble(str_split[1].substring(1, str_split[1].length() - 1));
        int flatFloor = Integer.parseInt(str_split[2].substring(1, str_split[2].length() - 1));
        int countRooms = Integer.parseInt(str_split[3].substring(1, str_split[3].length() - 1));
        String typeBuild = str_split[4].substring(1, str_split[4].length() - 1);
        String expluatationTime = str_split[5].substring(1, str_split[5].length() - 1);
        Flat flat = new Flat(flatNumber, flatSquare, flatFloor, countRooms, typeBuild, expluatationTime);
        return flat;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber.set(flatNumber);
    }

    public Double getFlatSquare() {
        return this.flatSquare.get();
    }

    public void setFlatSquare(Float flatSquare) {
        this.flatSquare.set(flatSquare);
    }

    public Integer getFlatFloor() {
        return this.flatFloor.get();
    }

    public void setFlatFloor(Integer flatFloor) {
        this.flatFloor.set(flatFloor);
    }

    public Integer getCountRooms() {
        return this.countRooms.get();
    }

    public void setCountRooms(Integer countRooms) {
        this.countRooms.set(countRooms);
    }

    public String getTypeBuild() {
        return this.typeBuild.get();
    }

    public void setTypeBuild(String typeBuild) {
        this.typeBuild.set(typeBuild);
    }

    public String getExpluatationTime() {
        return this.expluatationTime.get();
    }

    public void setExpluatationTime(String expluatationTime) {
        this.expluatationTime.set(expluatationTime);
    }
}
