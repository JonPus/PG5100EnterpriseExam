package no.enterprise.exam.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Users {

    @Id
    @NotBlank
    private String userID;

    @NotBlank
    @Size(max = 128)
    private String name;

    @NotBlank
    @Size(max = 128)
    private String lastName;

    private Long currency;

    @NotBlank
    private String hashedPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private Boolean enabled;

    @ManyToMany
    private List<Item> ownedItems;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getCurrency() {
        return currency;
    }

    public Long setCurrency(Long currency) {
        this.currency = currency;
        return currency;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    public void setOwnedItems(List<Item> boughtMonsters) {
        this.ownedItems = boughtMonsters;
    }


}
