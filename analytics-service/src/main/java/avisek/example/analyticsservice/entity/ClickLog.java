package avisek.example.analyticsservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "click_logs",
        indexes = {
                @Index(
                        name = "idx_short_code_clicked_at",
                        columnList = "short_url_code, clicked_at"
                ),
                @Index(
                       name = "idx_event_id",
                        columnList = "event_id"
                )
        }
)
@Getter
@Setter
public class ClickLog {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "event_id",unique = true,nullable = false)
   private UUID eventId;

   @Column(name = "short_url_code",nullable = false)
   private String shortUrlCode;

   @Column(name = "clicked_at",nullable = false)
   private Instant clickedAt;

}
