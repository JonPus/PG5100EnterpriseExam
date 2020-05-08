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

//This code is edited and adjusted from Andrea Arcuri's repository - 'https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/test/java/org/tsdes/intro/exercises/quizgame/backend/service/CategoryServiceTest.java

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CopyServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateBuyLoot() {
        userService.createUser("Jonathan", "Jonathan", "Pusparajah", 100L,  3,"123", "Jonathan@email.com", "user");
        Long monsterID = itemService.createItem("Test", "My desc", 300L, "Mewtwo", 1);
        Long purchaseID = copyService.newCopies(monsterID, "Jonathan");
        assertNotNull(purchaseID);
    }

    @Test
    public void testFilterBuyLootByUser() {
        String userName = "Jonathan";

        userService.createUser(userName, userName, "Pusparajah", 100L, 3,"123", "Jonathan@email.com", "user");
        Long firstMonster = itemService.createItem("Test", "My desc", 100L, "Mew", 1);
        Long secondMonster = itemService.createItem("Test-2", "My desc-2", 200L, "Mewtwo", 1);

        Long firstPurchase = copyService.newCopies(firstMonster, userName);
        Long secondPurchase = copyService.newCopies(secondMonster, userName);

        Users users = userService.findUserByUserName(userName);
        assertNotNull(firstPurchase);
        assertNotNull(secondPurchase);

        List<Copy> userPurchase = copyService.filterBuyLootByUser(users.getUserID());

        assertEquals(2, userPurchase.size());
    }

    @Test
    public void testFilterLootBoxByItem() {
        String firstUser = "Jonathan";
        String secondUser = "Jon";

        userService.createUser(firstUser, firstUser, "Pusparajah", 100L, 3,"123", "Jonathan@gmail.com", "user");
        userService.createUser(secondUser, secondUser, "Rotnebo", 200L, 3,"123", "Jon@gmail.com", "user");

        Long firstMonster = itemService.createItem("Test", "My desc", 100L, "Mew", 1);
        Long secondMonster = itemService.createItem("Test-2", "My desc-2", 200L, "Mewtwo", 1);

        Long firstPurchase = copyService.newCopies(firstMonster, firstUser);
        Long secondPurchase = copyService.newCopies(secondMonster, firstUser);
        Long thirdPurchase = copyService.newCopies(secondMonster, secondUser);

        assertNotNull(firstPurchase);
        assertNotNull(secondPurchase);
        assertNotNull(thirdPurchase);

        List<Copy> firstMonsterFilter = copyService.filterCopiesByItem(firstMonster);
        List<Copy> secondMonsterFilter = copyService.filterCopiesByItem(secondMonster);

        assertEquals(1, firstMonsterFilter.size());
        assertEquals(2, secondMonsterFilter.size());

    }

}