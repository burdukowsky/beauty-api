package tk.burdukowsky.beauty_api.category;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import tk.burdukowsky.beauty_api.service.Service;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categories")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category {
    @Id
    @SequenceGenerator(name = "categories_sequence", sequenceName = "categories_sequence", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_sequence")
    private long id;

    @NotNull
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("name")
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
