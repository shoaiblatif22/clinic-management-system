package com.example.clinicmanagementsystem.user.repository;
import com.example.clinicmanagementsystem.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
    UserEntity save(UserEntity user);
    UserEntity findByFirstName(String firstName);
    void deleteById(int id);

}
