package co.edu.uptc.model.entities;

import java.time.LocalDateTime;
import co.edu.uptc.enums.MovementType;

public class Accounting {

    private String description;
    private MovementType type;
    private double amount;
    private LocalDateTime dateTime;

    public Accounting(String description, MovementType type, double amount, LocalDateTime dateTime) {

        this.description = description;
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
