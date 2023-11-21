package com.gopang.oauth2server.repository;

import com.gopang.oauth2server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    /**
     * Client Id로 Client를 찾는다
     * @param clientId
     * @return Optional&lt;Client&gt;
     */
    Optional<Client> findByClientId(String clientId);
}
