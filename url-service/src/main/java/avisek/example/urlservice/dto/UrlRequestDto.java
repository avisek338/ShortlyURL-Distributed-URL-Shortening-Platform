package avisek.example.urlservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UrlRequestDto {
    @NotBlank
    private String longUrl;
    private String alias;
}
