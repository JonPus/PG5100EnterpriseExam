package no.enterprise.exam.frontend.controller;

import no.enterprise.exam.backend.entity.Item;
import no.enterprise.exam.backend.entity.Users;
import no.enterprise.exam.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ItemController implements Serializable {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItemService itemService;

    private Long itemID;

    public List<Item> getItems(int numberOfItems) {
        return itemService.getAllItems(true).stream().limit(numberOfItems).collect(Collectors.toList());
    }

    public String getMonsterRedirectionLink(Long itemID) {
        this.itemID = itemID;
        return "/details.jsf?monsterID=" + itemID + "&faces-redirect=true";
    }

    public List<Item> filterItemsBy(String searchBy, String query) {
        if (searchBy.equals("byValue")) {
            return itemService.filterByCost(Long.valueOf(query));
        } else if (searchBy.equals("byName")) {
            return itemService.filterItemsByItemName(query);
        } else {
            return null;
        }
    }

    public Item getItem(Long id) {
        return itemService.getItem(id, true);
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

    public void openLootBox(String userID) {
        Users users = entityManager.find(Users.class, userID);
        List<Item> items = users.getOwnedItems();

        if (users.getAvailableBoxes() >= 1) {
            int newLootBoxCount = users.getAvailableBoxes() - 1;
            users.setAvailableBoxes(newLootBoxCount);
            for (int i = 0; i <= 2; i++) {
                Item lootbox = getRandomItem();
                items.add(lootbox);
            }
            users.setOwnedItems(items);
        } else {
            throw new IllegalArgumentException("No more lootboxes, buy more.");
        }
    }

    public void sellLootBox(Long itemID, String userID) {
        Item item = entityManager.find(Item.class, itemID);
        Users users = entityManager.find(Users.class, userID);
        List<Item> items = users.getOwnedItems();
        items.remove(item);
        users.setCurrency(users.getCurrency() + item.getValue());
        users.setOwnedItems(items);
    }

    public Long buyLootBox(String userID) {
        Users users = entityManager.find(Users.class, userID);

        if (users == null) {
            throw new IllegalArgumentException("User not found.");
        }

        long currency = users.getCurrency();
        int lootBox = users.getAvailableBoxes();
        if (currency < 1000L) {
            return null;
        }
        currency = (currency - 1000L);
        lootBox++;

        users.setAvailableBoxes(lootBox);

        return users.setCurrency(currency);

    }


}
