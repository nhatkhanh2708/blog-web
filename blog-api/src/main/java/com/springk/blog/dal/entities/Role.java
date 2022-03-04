package com.springk.blog.dal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "roles",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    @Size(max = 50)
    private String name;
}