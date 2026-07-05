package avisek.example.urlservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UrlResponseDto {
    @NotBlank
    private String shortUrl;
    @NotBlank
    private String shortUrlCode;
}
