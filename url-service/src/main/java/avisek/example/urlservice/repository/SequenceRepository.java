package avisek.example.urlservice.repository;

import avisek.example.urlservice.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SequenceRepository extends JpaRepository<Url, UUID> {

    @Query(value = "SELECT nextval('url_sequence')", nativeQuery = true)
    long getNextSequence();
}