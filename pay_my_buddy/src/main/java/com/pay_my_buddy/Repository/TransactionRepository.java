package com.pay_my_buddy.Repository;

import com.pay_my_buddy.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySenderId(int senderId);

    List<Transaction> findByReceiverId(int receiverId);
}