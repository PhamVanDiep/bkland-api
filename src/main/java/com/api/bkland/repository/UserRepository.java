package com.api.bkland.repository;

import com.api.bkland.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "insert into user_role(user_id, role_id) values ( :userId , 2)", nativeQuery = true)
    void agencyRegister(@PathVariable("userId") String userId);

    @Modifying
    @Query(value = "insert into agency_district(user_id, district_code) values ( :userId, :districtCode );", nativeQuery = true)
    void agencyDistrictInsert(@PathVariable("userId") String userId, @PathVariable("districtCode") String districtCode);

    @Modifying
    @Query(value = "delete from agency_district where user_id = :userId ;", nativeQuery = true)
    void agencyDistrictDeleteByUserId(@PathVariable("userId") String userId);
}
