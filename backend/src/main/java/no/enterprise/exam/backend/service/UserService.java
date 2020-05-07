package no.enterprise.exam.backend.service;

import no.enterprise.exam.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(
            String userName,
            String name,
            String lastName,
            Long currency,
            Integer availableLootBoxes,
            String password,
            String email,
            String role) {
        String hashedPassword = passwordEncoder.encode(password);

        if ((em.find(Users.class, userName) != null) || (em.find(Users.class, email) != null)) {
            return false;
        }

        Users users = new Users();
        users.setUserID(userName);
        users.setName(name);
        users.setLastName(lastName);
        users.setCurrency(currency);
        users.setAvailableBoxes(availableLootBoxes);
        users.setHashedPassword(hashedPassword);
        users.setRoles(Collections.singleton(role));
        users.setEnabled(true);
        users.setEmail(email);

        em.persist(users);

        return true;
    }

    public Users getUser(String userID) {
        Users users = em.find(Users.class, userID);

        if (users == null) {
            throw new IllegalStateException("No such User found.");
        }
        return users;
    }

    public Users findUserByUserName(String userName) {
        Users users = em.find(Users.class, userName);
        if (users == null) {
            throw new IllegalStateException("No user with given userName");
        }
        users.getOwnedItems().size();
        return users;
    }

    public Users findByEmail(String email) {
        try {
            return em.createNamedQuery(Users.GET_BY_EMAIL, Users.class)
                    .setParameter("email", email.toLowerCase())
                    .getSingleResult();
        } catch (Exception ignored) {
            return null;
        }
    }

    public void updatePassword(String userId, String password) {
        Users users = em.find(Users.class, userId);
        users.setHashedPassword(passwordEncoder.encode(password));
    }
}