package wak.practice.jwtpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wak.practice.jwtpractice.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
