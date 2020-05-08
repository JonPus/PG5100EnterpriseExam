package no.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//This code is adjusted and edited to fit the project from Andrea Arcuri's repository on github - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/entity/Quiz.java'

@Entity
public class Copy {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private Users ownedBy;

    @ManyToOne
    @NotNull
    private Item itemInformation;

    public Copy() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getOwnedBy() {
        return ownedBy;
    }

    public Item getItemInformation() {
        return itemInformation;
    }

    public void setOwnedBy(Users boughtBy) {
        this.ownedBy = boughtBy;
    }


    public void setItemInformation(Item monsterInformation) {
        this.itemInformation = monsterInformation;
    }
}
