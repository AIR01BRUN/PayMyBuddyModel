package com.pay_my_buddy.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Transaction", schema = "PayMyBuddy")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true, length = 45)
    private String description;

    @Column(nullable = false)
    private double amount;

    // Relation avec l'expéditeur (User)
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Relation avec le destinataire (User)
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    public Transaction() {
    }

    public Transaction(String description, double amount, User sender, User receiver) {
        this.description = description;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }

    // Getters and Setters
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}