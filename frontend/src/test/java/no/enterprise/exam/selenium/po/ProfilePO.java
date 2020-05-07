package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePO extends LayoutPO {

    public ProfilePO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public ProfilePO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Profile Page");
    }

    public boolean isChangingPassword() {
        return getDriver().findElements(By.id("changePasswordForm")).size() > 0;
    }

    public void changePassword(String previousPassword, String newPassword) {
        changePassword(previousPassword, newPassword, newPassword);
    }

    public void changePassword(String previousPassword, String newPassword, String confirmPassword) {
        setText("previousPassword", previousPassword);
        setText("password", newPassword);
        setText("confirmPassword", confirmPassword);

        clickAndWait("submit");

    }

}
