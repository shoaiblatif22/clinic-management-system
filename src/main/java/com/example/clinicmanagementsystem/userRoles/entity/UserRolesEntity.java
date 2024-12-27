package com.example.clinicmanagementsystem.userRoles.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generate the ID
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    // Constructors
    public UserRolesEntity() {}

    public UserRolesEntity(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRolesEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                "}";
    }
}
