package com.gopang.oauth2server.repository;


import com.gopang.oauth2server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 권한을 권한 이름으로 찾는다
     * @param name
     * @return Optional&lt;Role&gt;
     */
    Optional<Role> findByName(String name);
}
