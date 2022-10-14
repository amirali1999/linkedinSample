package com.example.linkedinSample.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "skill",schema = "public",catalog = "linkedinSample")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @Column(name = "verify")
    private Boolean verify;
    @Column(name = "is_delete")
    private Boolean delete;
    @Column(name = "date_of_delete")
    private Date dateOfDelete;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Skill(String name, String description, Boolean verify, Subject subject) {
        this.name = name;
        this.description = description;
        this.verify = verify;
        this.subject = subject;
    }

    public Skill(String name, String description, Subject subject) {
        this.name = name;
        this.description = description;
        this.subject = subject;
    }

    public Skill(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Skill(String name) {
        this.name = name;
    }

    public Boolean isVerifyChange(Boolean verify) {
        if(this.verify != verify){
            return true;
        } else return false;
    }

    public Boolean isDelete() {
        return delete;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", verify=" + verify +
                ", subject=" + subject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return name.equals(skill.name) && Objects.equals(subject, skill.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subject);
    }
}
