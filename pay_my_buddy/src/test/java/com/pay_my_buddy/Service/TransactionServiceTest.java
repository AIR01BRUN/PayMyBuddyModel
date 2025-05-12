package com.pay_my_buddy.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.pay_my_buddy.DTO.TransfereDTO;
import com.pay_my_buddy.Model.Transaction;
import com.pay_my_buddy.Model.User;
import com.pay_my_buddy.Repository.TransactionRepository;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private List<Transaction> mockTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockTransaction = new ArrayList<Transaction>();
        User user01 = new User(1, "aa", "aa@aa.com", "aa");
        User user02 = new User(2, "bb", "bb@b.com", "bb");

        Transaction transaction01 = new Transaction("", 100, user01, user02);
        mockTransaction.add(transaction01);

    }

    @Test
    void testGetTransactionBySender() {
        int senderId = 1;

        when(transactionRepository.findBySenderId(senderId)).thenReturn(Arrays.asList(mockTransaction.get(0)));

        List<Transaction> result = transactionService.getTransactionBySender(senderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(senderId, result.get(0).getSender().getId());
        verify(transactionRepository, times(1)).findBySenderId(senderId);
    }

    @Test
    void testGetTransactionByReceiver() {
        int receiverId = 2;

        when(transactionRepository.findByReceiverId(receiverId)).thenReturn(Arrays.asList(mockTransaction.get(0)));

        List<Transaction> result = transactionService.getTransactionByReceiver(receiverId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(receiverId, result.get(0).getReceiver().getId());
        verify(transactionRepository, times(1)).findByReceiverId(receiverId);
    }

    @Test
    void testAddTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.addTransaction(transaction);

        assertNotNull(result);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testDeleteTransactionById() {
        int transactionId = 3;

        transactionService.deleteTransactionById(transactionId);

        verify(transactionRepository, times(1)).deleteById(transactionId);
    }

    @Test
    void testGetTransfereDTO() {
        int senderId = 1;

        when(transactionRepository.findBySenderId(senderId)).thenReturn(Arrays.asList(mockTransaction.get(0)));

        List<TransfereDTO> result = transactionService.getTransfereDTO(senderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        verify(transactionRepository, times(1)).findBySenderId(senderId);
    }
}