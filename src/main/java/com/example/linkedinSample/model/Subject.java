package com.example.linkedinSample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subject",schema = "public",catalog = "linkedinSample")
@Getter
@Setter
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EType type;
    @Column(name = "company")
    private String company;
    @Column(name = "language")
    private String language;
    @Column(name = "version")
    private double version;
    public Subject(EType type, String company, String language, double version) {
        this.type = type;
        this.company = company;
        this.language = language;
        this.version = version;
    }

    public Subject(EType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id && type == subject.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
