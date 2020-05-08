package no.enterprise.exam.selenium.po;

import no.enterprise.exam.selenium.PageObject;

//This code is added and adjusted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/ui/MatchPO.java

public class CollectionPO extends LayoutPO {

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

}
