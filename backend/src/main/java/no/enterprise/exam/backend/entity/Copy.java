package no.enterprise.exam.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Copy {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private Users ownedBy;

    private Integer duplicates;

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

    public void setOwnedBy(Users boughtBy) {
        this.ownedBy = boughtBy;
    }

    public Integer getDuplicates() {
        return duplicates;
    }

    public void setDuplicates(Integer duplicates) {
        this.duplicates = duplicates;
    }

    public Item getItemInformation() {
        return itemInformation;
    }

    public void setItemInformation(Item monsterInformation) {
        this.itemInformation = monsterInformation;
    }
}
