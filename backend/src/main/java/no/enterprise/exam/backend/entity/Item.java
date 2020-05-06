package no.enterprise.exam.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 300)
    private String title;

    @NotBlank
    private String description;


    @NotNull
    private Long value;

    @NotNull
    @Size(max = 300)
    private String itemName;


    @ManyToMany(mappedBy = "ownedItems")
    private List<Users> allOwners;

    public Item() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String monsterName) {
        this.itemName = monsterName;
    }


    public List<Users> getAllOwners() {
        return allOwners;
    }

    public void setAllOwners(List<Users> allMasters) {
        this.allOwners = allMasters;
    }
}
