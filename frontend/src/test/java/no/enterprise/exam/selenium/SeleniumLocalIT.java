package no.enterprise.exam.selenium;

import no.enterprise.exam.Application;
import no.enterprise.exam.backend.entity.Item;
import no.enterprise.exam.backend.service.ItemService;
import no.enterprise.exam.selenium.po.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
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

        assertTrue(collectionPO.getDriver().getPageSource().contains("You currently have no items in your collection."));

        collectionPO.doLogout();
        collectionPO = home.getUserInfo();
        assertNull(collectionPO);
    }

    @Test
    public void testRedeemLootBox() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = getUniqueId();
        home = createNewUser(userID, "123456");
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));

        collectionPO.clickAndWait("collection_1");
        assertTrue(collectionPO.getDriver().getPageSource().contains("Number of lootboxes: 2"));

        assertEquals(3, home.getNumberOfItemsDisplayed());
    }

    @Test
    public void testFailedRedeemLootBox() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = getUniqueId();
        home = createNewUser(userID, "123456");
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));

        for (int i = 0; i < 3; i++) {
            collectionPO.clickAndWait("collection_1");
        }
        assertTrue(collectionPO.getDriver().getPageSource().contains("Please buy more lootboxes!"));
    }

    @Test
    public void testMillItem() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = getUniqueId();
        home = createNewUser(userID, "123456");
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));

        collectionPO.clickAndWait("collection_1");

        String currency1 = getDriver().findElement(By.xpath("//*[@id=\"currencyNumber\"]\n")).getText();

        Long currencyIntOne = Long.parseLong(currency1);

        //System.out.println(currencyIntOne);

        getDriver().findElement(By.xpath("//*[@id=\"itemTable:0:collectionBtn_\"]\n")).click();

        String milledItem = getDriver().findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]\n")).getText();

        Long milledIntOne = Long.parseLong(milledItem);

        //System.out.println(milledIntOne + "Milled");

        Long totalMillSale = currencyIntOne + milledIntOne;

        //System.out.println(totalMillSale + " Total");

        assertEquals(2, home.getNumberOfItemsDisplayed());

        assertNotNull(totalMillSale);

    }

    @Test
    public void testBuyLootBox() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = getUniqueId();
        home = createNewUser(userID, "123456");
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));

        collectionPO.clickAndWait("collection_2");

        assertTrue(collectionPO.getDriver().getPageSource().contains("Number of lootboxes: 4"));

        String userCurrency = getDriver().findElement(By.xpath("//*[@id=\"currencyNumber\"]\n")).getText();

        long userCurrencyOne = Long.parseLong(userCurrency);

        assertEquals(0, userCurrencyOne);

    }


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

    @Test
    public void testSignUpChangePasswordAndLogin() {

        CollectionPO collectionPO = home.getUserInfo();
        assertNull(collectionPO);

        String userID = "Foo";
        String password = "123456";
        home = createNewUser(userID, password);
        collectionPO = home.getUserInfo();
        assertNotNull(collectionPO);
        assertTrue(collectionPO.getUserName().contains(userID));

        ProfilePO profilePO = home.toProfile();
        assertTrue(profilePO.isOnPage());

        profilePO.clickAndWait("changePassword");
        profilePO.changePassword("Wrong", "New");
        assertTrue(profilePO.isChangingPassword());

        profilePO.changePassword("123456", "New", "Mismatch");
        assertTrue(profilePO.isChangingPassword());

        profilePO.changePassword("123456", "New");
        assertFalse(profilePO.isChangingPassword());

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(userID));

        home.clickAndWait("linkToLoginId");

        LoginPO loginPO = home.toLogin();
        assertNull(loginPO.login(userID, password + "TestFailure"));

        IndexPO indexPO = loginPO.login(userID, "New");
        assertTrue(indexPO.isOnPage());
    }


}
