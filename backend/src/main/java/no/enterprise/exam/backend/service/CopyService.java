package no.enterprise.exam.backend.service;

import no.enterprise.exam.backend.entity.Copy;
import no.enterprise.exam.backend.entity.Item;
import no.enterprise.exam.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

//This code is edited and adjusted from Andrea Arcuri's repository from Github - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/QuizService.java'

@Service
@Transactional
public class CopyService {

    @Autowired
    private EntityManager entityManager;

    public List<Copy> getAllCopies() {
        TypedQuery<Copy> query = entityManager.createQuery(
                "SELECT p FROM Copy p", Copy.class);

        return query.getResultList();
    }

    public List<Copy> filterCopiesByItem(Long itemID) {
        TypedQuery<Copy> query = entityManager.createQuery(
                "SELECT c FROM Copy c WHERE c.itemInformation.id =?1", Copy.class);
        query.setParameter(1, itemID);

        return query.getResultList();
    }

    public Long newCopies(Long itemID, String userID) {
        Item item = entityManager.find(Item.class, itemID);
        Users users = entityManager.find(Users.class, userID);

        if (item == null) {
            throw new IllegalStateException("Item not found.");
        }
        if (users == null) {
            throw new IllegalStateException("User not found.");
        }

        Copy copy = new Copy();
        copy.setOwnedBy(users);
        copy.setItemInformation(item);
        users.getOwnedItems().add(item);
        entityManager.persist(copy);

        return copy.getId();
    }

    public List<Copy> filterBuyLootByUser(String userID) {
        TypedQuery<Copy> query = entityManager.createQuery(
                "SELECT p FROM Copy p WHERE p.ownedBy.userID =?1", Copy.class
        );

        query.setParameter(1, userID);

        return query.getResultList();
    }
}