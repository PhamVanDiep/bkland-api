package com.api.bkland.repository;

import com.api.bkland.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByIdNot(String id);
    @Modifying
    @Query(value = "insert into user_role(user_id, role_id) values ( :userId , 2)", nativeQuery = true)
    void agencyRegister(@Param("userId") String userId);

    @Modifying
    @Query(value = "insert into agency_district(user_id, district_code) values ( :userId, :districtCode );", nativeQuery = true)
    void agencyDistrictInsert(@Param("userId") String userId, @Param("districtCode") String districtCode);

    @Query(value = "select r.name\n" +
            "from role r inner join user_role ur on ur.role_id = r.id\n" +
            "where ur.user_id = :userId", nativeQuery = true)
    List<String> findRolesByUserId(String userId);
}
