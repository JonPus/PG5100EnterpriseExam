package no.enterprise.exam.backend.service;

import no.enterprise.exam.backend.TestApplication;
import no.enterprise.exam.backend.entity.Item;
import no.enterprise.exam.backend.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

//This code is added and adjusted from Andrea Arcuri's Repository - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/test/java/org/tsdes/intro/exercises/quizgame/backend/service/CategoryServiceTest.java'

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ItemServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateItem() {

        Long id = itemService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name",
                1
        );

        assertNotNull(id);

        Item myMonster = itemService.getItem(id, true);
        List<Users> users = myMonster.getAllOwners();
        assertNotNull(myMonster);
    }

    @Test
    public void testGetAllItems() {

        Long firstMonster = itemService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name"
                , 1
        );

        Long secondMonster = itemService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name"
                , 1
        );

        assertNotNull(firstMonster);
        assertNotNull(secondMonster);

        List<Item> allMonsters = itemService.getAllItems(false);
        assertEquals(2, allMonsters.size());

    }

    @Test
    public void testDeleteItem() {
        Long id = itemService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name",
                1
        );

        assertNotNull(id);

        itemService.deleteItem(id);

        assertThrows(IllegalStateException.class, () -> itemService.getItem(id, false));

    }

    @Test
    public void testGetRandomItem() {
        itemService.createItem("Test1", "TestDesc1", 100L, "Test1", 1);
        itemService.createItem("Test2", "TestDesc2", 200L, "Test2", 1);
        itemService.createItem("Test3", "TestDesc3", 300L, "Test3", 1);
        itemService.createItem("Test4", "TestDesc4", 400L, "Test4", 1);
        itemService.createItem("Test5", "TestDesc5", 500L, "Test5", 1);

        Item newRandomItem = itemService.getRandomItem();
        assertNotNull(newRandomItem);
    }

    @Test
    public void testOpenLootBox() {
        String userOne = "Foo";
        userService.createUser(userOne, userOne, "Bar", 1000L, 3, "123", "Foo@email.com", "admin");

        itemService.createItem("Test1", "DescTest1", 300L, "TestTitle1", 1);
        itemService.createItem("Test2", "DescTest2", 200L, "TestTitle2", 1);
        itemService.createItem("Test3", "DescTest3", 200L, "TestTitle3", 1);
        itemService.createItem("Test4", "DescTest4", 200L, "TestTitle4", 1);
        itemService.createItem("Test5", "DescTest5", 200L, "TestTitle5", 1);

        itemService.openLootBox(userOne);
        List<Item> user = userService.getUser(userOne).getOwnedItems();
        //assertEquals(3, user.size());
        assertNotNull(user);
        System.out.println(userOne);

    }

    @Test
    public void testFilterItemsByNames() {

        Long firstMonster = itemService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Kaido",
                1
        );

        Long secondMonster = itemService.createItem(
                "Title-2",
                "MyDescription-2",
                100L,
                "Luffy",
                1
        );

        Long thirdMonster = itemService.createItem(
                "Title-2",
                "MyDescription-2",
                100L,
                "Luffy",
                1
        );

        assertNotNull(firstMonster);
        assertNotNull(secondMonster);
        assertNotNull(thirdMonster);

        List<Item> kaidoMonsters = itemService.filterItemsByItemName("Kaido");
        List<Item> luffyMonsters = itemService.filterItemsByItemName("Luffy");

        assertEquals(1, kaidoMonsters.size());
        assertEquals(2, luffyMonsters.size());

    }

    @Test
    public void filterByCost() {

        Long firstMonster = itemService.createItem(
                "Title",
                "MyDescription",
                200L,
                "Kaido",
                1

        );

        Long secondMonster = itemService.createItem(
                "Title-2",
                "MyDescription-2",
                100L,
                "Luffy",
                1
        );

        Long thirdMonster = itemService.createItem(
                "Title-2",
                "MyDescription-2",
                200L,
                "Luffy",
                1

        );

        assertNotNull(firstMonster);
        assertNotNull(secondMonster);
        assertNotNull(thirdMonster);

        List<Item> cheapMonster = itemService.filterByCost(100L);
        List<Item> expensiveMonster = itemService.filterByCost(200L);

        assertEquals(1, cheapMonster.size());
        assertEquals(2, expensiveMonster.size());

    }


}