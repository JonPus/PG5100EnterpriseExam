package no.enterprise.exam.backend.service;

import no.enterprise.exam.backend.TestApplication;
import no.enterprise.exam.backend.entity.Copy;
import no.enterprise.exam.backend.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CopyServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService monsterService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreatePurchase() {
        userService.createUser("Jonathan", "Jonathan", "Pusparajah", 100L, "123", "Jonathan@email.com", "user");
        Long monsterID = monsterService.createItem("Test", "My desc", 300L, "Mewtwo");
        Long purchaseID = copyService.newPurchase(monsterID, "Jonathan");
        assertNotNull(purchaseID);
    }

    @Test
    public void testFilterPurchasesByBuyer() {
        String userName = "Jonathan";

        userService.createUser(userName, userName, "Pusparajah", 100L, "123", "Jonathan@email.com", "user");
        Long firstMonster = monsterService.createItem("Test", "My desc", 100L, "Mew");
        Long secondMonster = monsterService.createItem("Test-2", "My desc-2", 200L, "Mewtwo");

        Long firstPurchase = copyService.newPurchase(firstMonster, userName);
        Long secondPurchase = copyService.newPurchase(secondMonster, userName);

        Users users = userService.findUserByUserName(userName);
        assertNotNull(firstPurchase);
        assertNotNull(secondPurchase);

        List<Copy> userPurchase = copyService.filterPurchaseByUser(users.getUserID());

        assertEquals(2, userPurchase.size());
    }

    @Test
    public void testFilterPurchasesByTrip() {
        String firstUser = "Jonathan";
        String secondUser = "Jon";

        userService.createUser(firstUser, firstUser, "Pusparajah", 100L, "123", "Jonathan@gmail.com", "user");
        userService.createUser(secondUser, secondUser, "Rotnebo", 200L, "123", "Jon@gmail.com", "user");

        Long firstMonster = monsterService.createItem("Test", "My desc", 100L, "Mew");
        Long secondMonster = monsterService.createItem("Test-2", "My desc-2", 200L, "Mewtwo");

        Long firstPurchase = copyService.newPurchase(firstMonster, firstUser);
        Long secondPurchase = copyService.newPurchase(secondMonster, firstUser);
        Long thirdPurchase = copyService.newPurchase(secondMonster, secondUser);

        assertNotNull(firstPurchase);
        assertNotNull(secondPurchase);
        assertNotNull(thirdPurchase);

        List<Copy> firstMonsterFilter = copyService.filterPurchasesByMonster(firstMonster);
        List<Copy> secondMonsterFilter = copyService.filterPurchasesByMonster(secondMonster);

        assertEquals(1, firstMonsterFilter.size());
        assertEquals(2, secondMonsterFilter.size());

    }

}