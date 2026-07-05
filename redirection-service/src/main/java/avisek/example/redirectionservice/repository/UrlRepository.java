package avisek.example.redirectionservice.repository;

import avisek.example.redirectionservice.Entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID> {
    boolean existsByShortUrlCode(String shortUrlCode);

    Optional<Url> findByShortUrlCode(String shortUrlCode);
}
