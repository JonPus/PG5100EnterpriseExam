package no.enterprise.exam.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = Users.GET_BY_EMAIL, query = "select u from Users u where u.email = :email"),
        @NamedQuery(name = Users.GET_BY_NAME, query = "select u from Users u where u.name = u.userID and u.name is not null")
})

@Entity
public class Users {

    public static final String GET_BY_EMAIL = "GET_BY_EMAIL";
    public static final String GET_BY_NAME = "GET_BY_NAMES";

    @Id
    @NotBlank
    private String userID;

    @NotBlank
    @Size(max = 128)
    private String name;

    @Size(max = 128)
    private String lastName;

    @NotNull
    private Long currency;

    @NotNull
    private int availableBoxes;

    /*@OneToMany(mappedBy = "ownedBy")
    private List<Copy> copies;*/

    @NotBlank
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

    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    public void setOwnedItems(List<Item> ownedItems) {
        this.ownedItems = ownedItems;
    }

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

    public int getAvailableBoxes() {
        return availableBoxes;
    }

    public void setAvailableBoxes(int availableBoxes) {
        this.availableBoxes = availableBoxes;
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

    /*public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
}*/


}
