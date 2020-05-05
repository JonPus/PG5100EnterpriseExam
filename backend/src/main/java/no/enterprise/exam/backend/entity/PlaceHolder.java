package no.enterprise.exam.backend.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
public class PlaceHolder {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 300)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long cost;

    @NotNull
    @Size(max = 300)
    private String placeholderName;

    @NotNull
    private LocalDate placeHolderDate;

    @ManyToMany(mappedBy = "placedPlaceholders")
    private List<Users> allPlaceholders;

    public PlaceHolder() {

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

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getPlaceholderName() {
        return placeholderName;
    }

    public void setPlaceholderName(String placeholderName) {
        this.placeholderName = placeholderName;
    }

    public LocalDate getPlaceHolderDate() {
        return placeHolderDate;
    }

    public void setPlaceHolderDate(LocalDate placeHolderDate) {
        this.placeHolderDate = placeHolderDate;
    }

    public List<Users> getAllPlaceholders() {
        return allPlaceholders;
    }

    public void setAllPlaceholders(List<Users> allPlaceholders) {
        this.allPlaceholders = allPlaceholders;
    }
}
