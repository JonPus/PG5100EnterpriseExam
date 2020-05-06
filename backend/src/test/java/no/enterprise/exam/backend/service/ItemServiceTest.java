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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ItemServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService monsterService;

    @Test
    public void testCreateMonster() {

        Long id = monsterService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name"
        );

        assertNotNull(id);

        Item myMonster = monsterService.getItem(id, true);
        List<Users> users = myMonster.getAllOwners();
        assertNotNull(myMonster);
    }

    @Test
    public void testGetAllMonsters() {

        Long firstMonster = monsterService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name"
        );

        Long secondMonster = monsterService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name"
        );

        assertNotNull(firstMonster);
        assertNotNull(secondMonster);

        List<Item> allMonsters = monsterService.getAllItems(false);
        assertEquals(2, allMonsters.size());

    }

    @Test
    public void testDeleteMonster() {
        Long id = monsterService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Name"
        );

        assertNotNull(id);

        monsterService.deleteItem(id);

        assertThrows(IllegalStateException.class, () -> monsterService.getItem(id, false));

    }

    @Test
    public void testFilterMonstersByNames() {

        Long firstMonster = monsterService.createItem(
                "Title",
                "MyDescription",
                100L,
                "Kaido"
        );

        Long secondMonster = monsterService.createItem(
                "Title-2",
                "MyDescription-2",
                100L,
                "Luffy"
        );

        Long thirdMonster = monsterService.createItem(
                "Title-2",
                "MyDescription-2",
                100L,
                "Luffy"
        );

        assertNotNull(firstMonster);
        assertNotNull(secondMonster);
        assertNotNull(thirdMonster);

        List<Item> kaidoMonsters = monsterService.filterItemsByItemName("Kaido");
        List<Item> luffyMonsters = monsterService.filterItemsByItemName("Luffy");

        assertEquals(1, kaidoMonsters.size());
        assertEquals(2, luffyMonsters.size());

    }

    @Test
    public void filterByCost() {

        Long firstMonster = monsterService.createItem(
                "Title",
                "MyDescription",
                200L,
                "Kaido"

        );

        Long secondMonster = monsterService.createItem(
                "Title-2",
                "MyDescription-2",
                100L,
                "Luffy"
        );

        Long thirdMonster = monsterService.createItem(
                "Title-2",
                "MyDescription-2",
                200L,
                "Luffy"

        );

        assertNotNull(firstMonster);
        assertNotNull(secondMonster);
        assertNotNull(thirdMonster);

        List<Item> cheapMonster = monsterService.filterByCost(100L);
        List<Item> expensiveMonster = monsterService.filterByCost(200L);

        assertEquals(1, cheapMonster.size());
        assertEquals(2, expensiveMonster.size());

    }


}