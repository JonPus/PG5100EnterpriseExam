package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

//This code is added and adjusted from - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/main/resources/application.yml


import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexPO extends LayoutPO {

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public IndexPO(PageObject other) {
        super(other);
    }

    public void toStartingPage() {
        getDriver().get(host + ":" + port);
    }

    public int getNumberOfItemsDisplayed() {
        return getDriver().findElements(By.xpath("//table//tr")).size() - 1;
    }

    public IndexPO searchOnPage(String selection, String query) {
        setText("queryInputID", query);
        WebElement dropDown = getDriver().findElement(By.id("selectOneID"));
        Select searchType = new Select(dropDown);
        searchType.selectByValue(selection);
        IndexPO indexPO = new IndexPO(this);
        clickAndWait("searchBtn");
        assertTrue(indexPO.isOnPage());
        return indexPO;
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Home page");
    }

    public CollectionPO getUserInfo() {

        if (getDriver().findElements(By.id("userID")).size() == 0)
            return null;
        clickAndWait("userID");
        CollectionPO collectionPO = new CollectionPO(this);
        assertTrue(collectionPO.isOnPage());
        return collectionPO;
    }


}
