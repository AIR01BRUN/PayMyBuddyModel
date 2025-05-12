package com.pay_my_buddy.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User", schema = "PayMyBuddy", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true, length = 45)
    private String username;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(nullable = false, length = 45)
    private String password;

    @Column(nullable = false)
    private double amount;

    @ManyToMany
    @JoinTable(name = "Connections", schema = "PayMyBuddy", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "connection_user_id"))
    private List<User> connections = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> receivedTransactions = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String password, List<User> connections,
            List<Transaction> sentTransactions, List<Transaction> receivedTransactions, double amount) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.connections = connections;
        this.sentTransactions = sentTransactions;
        this.receivedTransactions = receivedTransactions;
        this.amount = amount;

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.connections = new ArrayList<>();
        this.sentTransactions = new ArrayList<>();
        this.receivedTransactions = new ArrayList<>();
        this.amount = 0;
    }

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.connections = new ArrayList<>();
        this.sentTransactions = new ArrayList<>();
        this.receivedTransactions = new ArrayList<>();
        this.amount = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getConnections() {
        return connections;
    }

    public void setConnections(List<User> connections) {
        this.connections = connections;
    }

    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
