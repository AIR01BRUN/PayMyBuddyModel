package com.pay_my_buddy.DTO;

public class TransfereDTO {

    private int idReceiver;
    private String nameReceiver;
    private String description;
    private double amount;

    public TransfereDTO(int idReceiver, String nameReceiver, String description, double amount) {
        this.idReceiver = idReceiver;
        this.nameReceiver = nameReceiver;
        this.description = description;
        this.amount = amount;
    }

    public TransfereDTO() {
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
