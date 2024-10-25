package com.ms.myShop.repository;

import com.ms.myShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long > {

    User findByEmailAndAndPasswordAndActive(String email, String password, Integer active);


}
