package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

//This code is added and ajusted from - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/LayoutPO.java


import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp() {

        clickAndWait("linkToSignupId");

        SignUpPO po = new SignUpPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public ProfilePO toProfile() {

        clickAndWait("profileBtn");

        ProfilePO profilePO = new ProfilePO(this);
        assertTrue(profilePO.isOnPage());

        return profilePO;

    }

    public IndexPO doLogout() {

        clickAndWait("logoutBtn");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public LoginPO toLogin() {
        clickAndWait("submit");
        LoginPO loginPO = new LoginPO(this);
        assertTrue(loginPO.isOnPage());

        return loginPO;
    }

    public boolean isLoggedIn() {

        return getDriver().findElements(By.id("logoutBtn")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }

    public boolean isInFirstColumn(String id) {
        List<WebElement> table = getDriver().findElements(By.xpath("//*[@id=\"itemTable\"]//tbody//td[1]"));
        boolean isFound = false;
        for (WebElement column : table) {
            if (column.getText().equals(id)) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }

}
