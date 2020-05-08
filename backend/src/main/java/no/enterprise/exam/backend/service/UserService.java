package no.enterprise.exam.backend.service;

import no.enterprise.exam.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

//This code is adjusted and edited from Andrea Arcuri's Github repository - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/UserService.java'

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

    public void updatePassword(String userId, String password) {
        Users users = em.find(Users.class, userId);
        users.setHashedPassword(passwordEncoder.encode(password));
    }
}