package com.example.elva_yiwei.menu_order;

/**
 * Created by sunny on 15/10/13.
 */
public class Info {

    private String name;
    private double price;
    private int quantity;
    private double tax;
    private double total;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTax() {
        return tax;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

}
