package com.example.clinicmanagementsystem.repository;
import com.example.clinicmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
    UserEntity save(UserEntity user);
    UserEntity findByFirstName(String firstName);
//    UserEntity update(UserEntity user);
    void deleteById(int id);

}
