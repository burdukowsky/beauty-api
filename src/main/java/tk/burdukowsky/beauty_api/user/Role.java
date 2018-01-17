package tk.burdukowsky.beauty_api.user;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Enumerated(value = EnumType.STRING)
    private RoleEnum name;

    public Role() {
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public String authority() {
        return "ROLE_" + this.name.name();
    }
}
