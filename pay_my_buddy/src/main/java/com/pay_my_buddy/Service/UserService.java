package com.pay_my_buddy.Service;

import com.pay_my_buddy.DTO.RelationDTO;
import com.pay_my_buddy.Model.Transaction;
import com.pay_my_buddy.Model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.pay_my_buddy.Repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user to retrieve
     * @return the User object if found, or null if no user exists with the given id
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return the User object if found, or null if no user exists with the given
     *         email
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Adds a new user to the system.
     * This method hashes the user's password before saving the user.
     *
     * @param user The user to be added. Must not be null and should contain valid
     *             user details.
     * @return The saved user object after being persisted.
     */
    public User addUser(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        return saveUser(user);
    }

    /**
     * Saves the given user to the repository.
     *
     * @param user the User object to be saved
     * @return the saved User object
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUserById(int id) {
        if (userRepository.existsById(id)) {

            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean authenticateUser(String email, String password) {
        if (userExistsByEmail(email)) {
            User user = getUserByEmail(email);

            if (user.getPassword().equals(hashPassword(password))) {
                return true;
            }

        }

        return false;
    }

    public List<RelationDTO> getRelationsUser(User user) {
        List<RelationDTO> relations = new ArrayList<>();
        for (User u : user.getConnections()) {
            relations.add(new RelationDTO(u.getId(), u.getUsername()));
        }
        return relations;
    }

    public boolean hasRelation(User user, User userRelation) {
        for (User u : user.getConnections()) {
            if (u == userRelation) {
                return true;
            }
        }
        return false;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }

    public boolean hasAmount(User user, double amount) {
        return user.getAmount() >= amount;
    }

    public void soldTransfer(Transaction transaction) {
        User sender = transaction.getSender();
        User receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        double soldPercent = monetizationPercentage(amount);
        User bank = getUserById(1);
        addSold(receiver, amount);
        addSold(sender, -amount - soldPercent);
        addSold(bank, soldPercent);

    }

    public void addSold(User receiver, double amount) {
        receiver.setAmount(receiver.getAmount() + amount);
        saveUser(receiver);
    }

    public double monetizationPercentage(double amount) {
        return amount * 0.005;
    }

}
