package Model;

import java.io.Serializable;

public class Entry implements Serializable {

    private String date, category, payer, place, comment;

    private double amount;

    private int id;

    public Entry(String date, double amount, String category, String payer, String place, String comment, int id) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.payer = payer;
        this.place = place;
        this.comment = comment;
        this.id = id;
    }

    public String toStringDetailed() {
        return "day: " + getDay() + ", month: " + getMonth() + ", year: " + getYear() + ", amount: " + amount + ", category: " + category + ", payer: " + payer + ", place: " + place + ", comment: " + comment;
    }

    public String getYear() {
        return date.substring(6, 10);
    }

    public String getMonth() {
        return date.substring(3, 5);
    }

    public String getDay() {
        return date.substring(0, 2);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

}
