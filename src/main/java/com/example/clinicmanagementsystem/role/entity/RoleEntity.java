package com.example.clinicmanagementsystem.role.entity;

import com.example.clinicmanagementsystem.role.Role;
import com.example.clinicmanagementsystem.user.entity.UserEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private Role roleName;

    //MANY-TO-MANY RELATIONSHIP WITH USERS
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> userEntitySet = new HashSet<>();


    public RoleEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRoleName() {
        return roleName;
    }

    public void setRoleName(Role roleName) {
        this.roleName = roleName;
    }
}
