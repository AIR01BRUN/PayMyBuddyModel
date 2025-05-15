package com.pay_my_buddy.DTO;

import com.pay_my_buddy.Model.Transaction;

public class TransactionDTO {

    private Transaction transaction;
    private String error;
    private String success;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction, String error, String success) {
        this.transaction = transaction;
        this.error = error;
        this.success = success;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
