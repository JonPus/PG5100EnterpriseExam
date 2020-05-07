package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;
import org.openqa.selenium.WebDriver;

public class LoginPO extends LayoutPO {


    public LoginPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LoginPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Login Page");
    }

    public IndexPO login(String userId, String password) {
        setText("username", userId);
        setText("password", password);
        clickAndWait("submit");

        IndexPO indexPO = new IndexPO(this);
        if (indexPO.isOnPage()) {
            return indexPO;
        }
        return null;
    }
}
