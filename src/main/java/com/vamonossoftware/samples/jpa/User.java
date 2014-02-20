package com.vamonossoftware.samples.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role")
    @Column(name = "role")
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static class Builder {
        private User instance;

        public Builder() {
            instance = new User();
        }

        public Builder(User instance) {
            this.instance = instance;
        }

        public Builder withUserName(String userName1) {
            instance.setUserName(userName1);
            return this;
        }

        public Builder withRole(Role role) {
            if (instance.getRoles() == null) {
                instance.setRoles(new ArrayList<Role>());
            }
            instance.getRoles().add(role);
            return this;
        }

        public User build() {
            return instance;
        }


    }
}
