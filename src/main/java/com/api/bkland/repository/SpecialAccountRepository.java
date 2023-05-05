package com.api.bkland.repository;

import com.api.bkland.entity.SpecialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialAccountRepository extends JpaRepository<SpecialAccount, String> {
}
