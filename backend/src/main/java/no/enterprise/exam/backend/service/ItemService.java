package no.enterprise.exam.backend.service;

import no.enterprise.exam.backend.entity.Item;
import no.enterprise.exam.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Random;

//This code is is adjusted and edited from Andrea Arcuri's repository - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/CategoryService.java'

@Service
@Transactional
public class ItemService {

    @Autowired
    private EntityManager entityManager;

    public List<Item> getAllItems(Boolean withOwners) {
        TypedQuery<Item> query = entityManager.createQuery(
                "SELECT i FROM Item i ORDER BY i.value ASC", Item.class
        );
        List<Item> allItems = query.getResultList();

        if (withOwners) {
            allItems.forEach(u -> u.getAllOwners().size());
        }
        return allItems;

    }

    public Long createItem(
            String title,
            String description,
            Long value,
            String itemName,
            Integer numberOfItems
    ) {
        Item item = new Item();

        item.setTitle(title);
        item.setDescription(description);
        item.setValue(value);
        item.setItemName(itemName);
        item.setNumberOfItem(numberOfItems);

        entityManager.persist(item);

        return item.getId();
    }

    public Item getItem(Long itemID, Boolean withOwners) {
        Item item = entityManager.find(Item.class, itemID);

        if (item == null) {
            throw new IllegalStateException("No such item found.");
        }
        if (withOwners) {
            item.getAllOwners().size();
        }
        return item;
    }

    public void deleteItem(Long itemID) {
        Item itemToRemove = entityManager.find(Item.class, itemID);

        if (itemToRemove == null) {
            throw new IllegalStateException("Item not found in database.");
        }

        entityManager.remove(itemToRemove);
    }

    public List<Item> filterItemsByItemName(String itemName) {
        TypedQuery<Item> query = entityManager.createQuery(
                "SELECT i FROM Item i WHERE i.itemName =?1 ORDER BY i.value ASC", Item.class
        );

        query.setParameter(1, itemName);

        return query.getResultList();
    }

    public List<Item> filterByCost(Long cost) {
        TypedQuery<Item> query = entityManager.createQuery(
                "SELECT i FROM Item i WHERE i.value =?1", Item.class
        );

        query.setParameter(1, cost);

        return query.getResultList();
    }

    public Item getRandomItem() {

        TypedQuery<Long> sizeQuery = entityManager.createQuery(
                "SELECT COUNT(i) FROM Item i", Long.class);
        long size = sizeQuery.getSingleResult();

        Random random = new Random();
        int randomInt = random.nextInt((int) size);

        TypedQuery<Item> query = entityManager.createQuery(
                "SELECT i FROM Item  i", Item.class).setFirstResult(randomInt).setMaxResults(1);
        Item item = query.getSingleResult();
        return item;
    }

    public List<Item> openLootBox(String userID) {
        Users users = entityManager.find(Users.class, userID);

        List<Item> items = users.getOwnedItems();

        if (users.getAvailableBoxes() >= 1) {
            int newLootBoxCount = users.getAvailableBoxes() - 1;
            users.setAvailableBoxes(newLootBoxCount);
            items.add(getRandomItem());
            items.add(getRandomItem());
            items.add(getRandomItem());
            users.setOwnedItems(items);
            System.out.println(items);
            return items;
        } else {
            throw new IllegalArgumentException("You are out of Lootboxes, buy more!");
        }
    }

}
