package com.example.linkedinSample.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jwt_blacklist",schema = "public",catalog = "linkedinSample")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "expiry_date")
    private Date expiryDate;

    public JwtBlacklist(String accessToken, Date expiryDate) {
        this.accessToken = accessToken;
        this.expiryDate = expiryDate;
    }
}
