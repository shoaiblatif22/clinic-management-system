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

    // Many-to-many relationship with Users
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> userEntitySet = new HashSet<>();

    public RoleEntity() {}

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for roleName
    public Role getRoleName() {
        return roleName;
    }

    public void setRoleName(Role roleName) {
        this.roleName = roleName;
    }

    // Getter and Setter for userEntitySet
    public Set<UserEntity> getUserEntitySet() {
        return userEntitySet;
    }

    public void setUserEntitySet(Set<UserEntity> userEntitySet) {
        this.userEntitySet = userEntitySet;
    }

    // Optional: Override toString for debugging purposes
    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", roleName=" + roleName +
                ", userEntitySet=" + userEntitySet +
                '}';
    }
}
