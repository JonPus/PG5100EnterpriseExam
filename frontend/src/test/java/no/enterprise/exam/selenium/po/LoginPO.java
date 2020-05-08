package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;

//This code is added and adjusted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/SignUpPO.java

public class LoginPO extends LayoutPO {

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
