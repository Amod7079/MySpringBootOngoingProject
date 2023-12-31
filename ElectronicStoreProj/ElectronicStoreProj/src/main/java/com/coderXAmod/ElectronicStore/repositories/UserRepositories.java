package com.coderXAmod.ElectronicStore.repositories;

import com.coderXAmod.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositories extends JpaRepository <User,String>{
Optional<User>findByEmail(String email);
       Optional<User> findByEmailAndPassword(String email, String password);
       List<User> findByNameContaining(String keyword);

}
