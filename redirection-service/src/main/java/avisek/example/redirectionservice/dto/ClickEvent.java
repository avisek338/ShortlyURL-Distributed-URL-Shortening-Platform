package avisek.example.redirectionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickEvent {

    private String eventId;
    private String message;
    private String shortCode;
    private Instant timestamp;

    public static ClickEvent of(String shortCode, String message) {
        return ClickEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .shortCode(shortCode)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }
}