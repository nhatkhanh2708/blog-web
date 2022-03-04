package com.springk.blog.dal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    @Size(max = 70)
    @NotBlank
    private String username;

    @Column(name = "password", nullable = false)
    @Size(max = 60)
    @NotBlank
    private String password;

    @Column(name = "email", nullable = false)
    @Size(max = 50)
    @NotBlank
    @Email
    private String email;

    @Column(name = "intro", nullable = true)
    @Size(max = 500)
    private String intro;

    @Column(name = "profile", nullable = true)
    @Size(max = 1000)
    private String profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "active", nullable = false)
    private boolean active;
}