package com.library.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admins")
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="admin_id")
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "admin_roles",
            joinColumns= @JoinColumn(name="admin_id",referencedColumnName = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private Collection<Role> roles;

}
