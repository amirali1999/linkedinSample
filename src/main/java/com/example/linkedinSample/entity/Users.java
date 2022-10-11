package com.example.linkedinSample.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users",schema = "public",catalog = "linkedinSample")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;
    @Column(name = "about_me")
    private String aboutMe;
    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH}
            )
    @Fetch(FetchMode.JOIN)
    @JoinTable(	name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Roles> roles;

    public Users(String name, String username, String password, String email, EGender gender, String aboutMe) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.aboutMe = aboutMe;
    }

    public Users(String name, String username, String password, String email, EGender gender, Set<Roles> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.roles = roles;
    }

    public Users(String name, String username, String password, String email, EGender gender) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public Users(String username) {
        this.username = username;
    }

//    @Override
//    public String toString() {
//        return "Users{" +
//                "username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return username.equals(users.username) && email.equals(users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
