package tk.burdukowsky.beauty_api.category;

import org.hibernate.annotations.GenericGenerator;
import tk.burdukowsky.beauty_api.service.Service;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categories")
@GenericGenerator(
        name = "categoriesSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "initial_value", value = "10")}
)
public class Category {
    @Id
    @GeneratedValue(generator = "categoriesSequenceGenerator")
    private long id;

    @NotNull
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Service> services;

    public Category() {
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

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
