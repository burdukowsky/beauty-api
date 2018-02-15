package tk.burdukowsky.beauty_api.service;

import org.hibernate.annotations.GenericGenerator;
import tk.burdukowsky.beauty_api.category.Category;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "services")
@GenericGenerator(
        name = "servicesSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "initial_value", value = "10")}
)
public class Service {
    @Id
    @GeneratedValue(generator = "servicesSequenceGenerator")
    private long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Service() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
