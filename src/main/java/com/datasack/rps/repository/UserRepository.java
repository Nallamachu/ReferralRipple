package com.datasack.rps.repository;

import com.datasack.rps.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "from User where referredByCode=?1")
    User findByReferredByCode(String referredByCode);
}
