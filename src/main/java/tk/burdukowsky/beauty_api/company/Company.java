package tk.burdukowsky.beauty_api.company;

import org.hibernate.annotations.GenericGenerator;
import tk.burdukowsky.beauty_api.user.ApplicationUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "companies")
@GenericGenerator(
        name = "usersSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "initial_value", value = "10")}
)
public class Company {
    @Id
    @GeneratedValue(generator = "usersSequenceGenerator")
    private long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private ApplicationUser owner;

    public Company() {
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

    public ApplicationUser getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUser owner) {
        this.owner = owner;
    }
}
