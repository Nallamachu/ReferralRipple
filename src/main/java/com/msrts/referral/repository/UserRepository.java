package com.msrts.referral.repository;

import com.msrts.referral.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query(value = "from User u where u.email=?1")
  User findByEmail(String email);

}
