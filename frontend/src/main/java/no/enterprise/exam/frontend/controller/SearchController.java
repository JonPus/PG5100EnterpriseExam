package no.enterprise.exam.frontend.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

//This code is added and adjusted from Andrea Arcuri's repository - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/main/java/org/tsdes/intro/exercises/quizgame/frontend/controller/MatchController.java'

@Named
@SessionScoped
public class SearchController implements Serializable {
    String selection;
    String query;

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchLink() {
        return "index?searchBy=" + selection + "&query=" + query + "&faces-redirect=true";
    }

    public String getSearchLinkCollection() {
        return "collection?searchBy=" + selection + "&query=" + query + "&faces-redirect=true";
    }

}
