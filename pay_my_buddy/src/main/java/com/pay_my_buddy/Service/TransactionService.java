package com.pay_my_buddy.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pay_my_buddy.DTO.TransfereDTO;
import com.pay_my_buddy.Model.Transaction;
import com.pay_my_buddy.Repository.TransactionRepository;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves a list of transactions initiated by a specific sender.
     *
     * @param id The unique identifier of the sender whose transactions are to be
     *           retrieved.
     * @return A list of {@link Transaction} objects associated with the specified
     *         sender.
     */
    public List<Transaction> getTransactionBySender(int id) {
        return transactionRepository.findBySenderId(id);
    }

    /**
     * Retrieves a list of transactions where the specified user is the receiver.
     *
     * @param id The ID of the receiver whose transactions are to be fetched.
     * @return A list of transactions associated with the specified receiver ID.
     */
    public List<Transaction> getTransactionByReceiver(int id) {
        return transactionRepository.findByReceiverId(id);
    }

    /**
     * Adds a new transaction to the repository.
     *
     * @param transaction the transaction to be added
     * @return the saved transaction object
     */
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Deletes a transaction from the repository based on its unique identifier.
     *
     * @param id the unique identifier of the transaction to be deleted
     */
    public void deleteTransactionById(int id) {
        transactionRepository.deleteById(id);
    }

    /**
     * Retrieves a list of TransfereDTO objects for a given sender ID.
     * Each TransfereDTO contains information about a transaction, including
     * the receiver's ID, username, transaction description, and amount.
     *
     * @param senderId the ID of the sender whose transactions are to be retrieved
     * @return a list of TransfereDTO objects representing the sender's transactions
     */
    public List<TransfereDTO> getTransfereDTO(int senderId) {
        List<TransfereDTO> transferes = new ArrayList<>();
        for (Transaction transaction : getTransactionBySender(senderId)) {
            transferes.add(new TransfereDTO(transaction.getReceiver().getId(), transaction.getReceiver().getUsername(),
                    transaction.getDescription(), transaction.getAmount()));
        }
        return transferes;
    }

}
