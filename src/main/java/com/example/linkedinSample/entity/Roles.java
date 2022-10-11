package com.example.linkedinSample.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "roles",schema = "public",catalog = "linkedinSample")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public Roles(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Roles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return name.equals(roles.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
