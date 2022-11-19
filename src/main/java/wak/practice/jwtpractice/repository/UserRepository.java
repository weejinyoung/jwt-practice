package wak.practice.jwtpractice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import wak.practice.jwtpractice.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")//EAGER 조회
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}