package org.example.portfolio.repos;

import org.example.portfolio.entities.JwtTokenObject;
import org.example.portfolio.entities.Owner;
import org.example.portfolio.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface JwtTokenRepo  extends JpaRepository<JwtTokenObject, Long> {



    JwtTokenObject findByValue(String value);

    @Modifying
    @Transactional
    @Query("UPDATE JwtTokenObject t SET t.expired = true, t.revoked = true WHERE t.owner = :owner AND t.tokenType= :tokenType")
    void revokeRefreshTokens(Owner owner, TokenType tokenType);


    @Modifying
    @Transactional
    @Query("DELETE FROM JwtTokenObject t WHERE t.uselessIn < CURRENT_TIMESTAMP")
    void deleteTokensBeforeCurrentTime();


    @Modifying
    @Transactional
    @Query("UPDATE JwtTokenObject t SET t.expired = true, t.revoked = true WHERE t.owner.email = :email")
    void revokeTokenByUserEmail(String email);





}
