package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;
import org.openqa.selenium.By;

//This code is added and adjusted from - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/SignUpPO.java

public class ProfilePO extends LayoutPO {

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
