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

@Service
@Transactional
public class CopyService {

    @Autowired
    private EntityManager entityManager;

    public List<Copy> getAllPurchases() {
        TypedQuery<Copy> query = entityManager.createQuery(
                "SELECT p FROM Copy p", Copy.class);

        return query.getResultList();
    }

    public Long millCopy(Long copyID, String userID) {
        Copy copy = entityManager.find(Copy.class, copyID);
        Users users = entityManager.find(Users.class, userID);

        if (copy == null) {
            throw new IllegalArgumentException("Item does not exist.");
        }

        long currency = users.getCurrency() + copy.getItemInformation().getValue();
        int duplicates = copy.getDuplicates();

        if (duplicates > 1) {
            entityManager.remove(copy);
        } else {
            duplicates--;
            copy.setDuplicates(duplicates);
        }
        return users.setCurrency(currency);
    }

    public List<Copy> filterPurchasesByMonster(Long monsterID) {
        TypedQuery<Copy> query = entityManager.createQuery(
                "SELECT p FROM Copy p WHERE p.itemInformation.id =?1", Copy.class);
        query.setParameter(1, monsterID);


        return query.getResultList();
    }

    public Long newPurchase(Long monsterID, String userID) {
        Item monster = entityManager.find(Item.class, monsterID);
        Users users = entityManager.find(Users.class, userID);

        if (monster == null) {
            throw new IllegalStateException("Monster not found.");
        }
        if (users == null) {
            throw new IllegalStateException("User not found.");
        }

        Copy purchase = new Copy();
        purchase.setOwnedBy(users);
        purchase.setItemInformation(monster);
        users.getOwnedItems().add(monster);
        entityManager.persist(purchase);

        return purchase.getId();
    }

    public List<Copy> filterPurchaseByUser(String userID) {
        TypedQuery<Copy> query = entityManager.createQuery(
                "SELECT p FROM Copy p WHERE p.ownedBy.userID =?1", Copy.class
        );

        query.setParameter(1, userID);

        return query.getResultList();
    }
}
