package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionPO extends LayoutPO {

    public CollectionPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public CollectionPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Collection page");
    }

    public String getUserName() {
        return getText("userNameID");
    }

    public CollectionPO redeemLootBox(String userID) {

        if (getDriver().findElements(By.id("collection_1")).size() == 0)
            return null;

        clickAndWait("collection_1");
        CollectionPO collectionPO = new CollectionPO(this);

        assertTrue(isInFirstColumn(userID));
        return collectionPO;
    }


}
