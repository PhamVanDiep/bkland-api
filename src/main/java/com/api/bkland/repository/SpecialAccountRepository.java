package com.api.bkland.repository;

import com.api.bkland.entity.SpecialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialAccountRepository extends JpaRepository<SpecialAccount, String> {
    Optional<SpecialAccount> findByUserId(String userId);
}
