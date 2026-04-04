package co.edu.uptc.model.entity;

public class Product {
    private int id;
    private String description;
    private String unit;
    private double price;

    

    public Product(int id, String description, String unit, double price) {
        this.id = id;
        this.description = description;
        this.unit = unit;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
