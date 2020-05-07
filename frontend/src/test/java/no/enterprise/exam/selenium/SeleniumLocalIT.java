package no.enterprise.exam.selenium;

import no.enterprise.exam.Application;
import no.enterprise.exam.backend.entity.Item;
import no.enterprise.exam.backend.service.ItemService;
import no.enterprise.exam.selenium.po.CollectionPO;
import no.enterprise.exam.selenium.po.IndexPO;
import no.enterprise.exam.selenium.po.SignUpPO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT {

    private static WebDriver driver;

    @LocalServerPort
    private int port;

    @Autowired
    ItemService itemService;


    @BeforeAll
    public static void initClass() {

        driver = SeleniumDriverHandler.getChromeDriver();

        assumeTrue(driver != null, "Cannot find/initialize Chrome driver");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected String getServerHost() {
        return "localhost";
    }

    protected int getServerPort() {
        return port;
    }

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement();
    }

    private IndexPO home;

    private IndexPO createNewUser(String username, String password) {
        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.signUP(username, password);
        assertNotNull(indexPO);

        return indexPO;
    }

    @BeforeEach
    public void initTest() {

        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from home page");
    }

    @Test
    public void testDisplayHomePag() {
        assertEquals(10, home.getNumberOfItemsDisplayed());
    }

    @Test
    public void testEmptyCollection() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = getUniqueId();
        home = createNewUser(userID, "123456");
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));


        //Will always be 1 due to empty tabelcolumns.
        assertEquals(1, home.getNumberOfItemsDisplayed());

        collectionPO.doLogout();
        collectionPO = home.getUserInfo();
        assertNull(collectionPO);
    }

   /* @Test
    public void testRedeemLootBox() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = getUniqueId();
        home = createNewUser(userID, "123456");
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));

        String buttonId = home.getRandomButton();
        collectionPO = home.getDetails(buttonId);
        collectionPO.redeemLootBox(userID);

        System.out.println("COLLECTION PAGE");

    }*/


    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String userID = getUniqueId();
        String password = "123456";

        home = createNewUser(userID, password);

        assumeTrue(home.isLoggedIn());
        assumeTrue(home.getDriver().getPageSource().contains(userID));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(userID));

    }

    @Test
    public void testSearch() {
        List<Item> allItems = itemService.getAllItems(false);
        Item firstItem = allItems.get(0);
        home = home.searchOnPage("byValue", firstItem.getValue().toString());
        assertTrue(home.isInFirstColumn(firstItem.getId().toString()));
        home.toStartingPage();
        home = home.searchOnPage("byName", firstItem.getItemName());
        assertTrue(home.isInFirstColumn(firstItem.getId().toString()));
    }


}
