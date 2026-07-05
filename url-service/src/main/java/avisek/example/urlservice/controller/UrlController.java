package avisek.example.urlservice.controller;

import avisek.example.urlservice.dto.UrlRequestDto;
import avisek.example.urlservice.dto.UrlResponseDto;
import avisek.example.urlservice.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    ResponseEntity<UrlResponseDto> createShortUrl(@Valid @RequestBody UrlRequestDto urlRequestDto) {
        return ResponseEntity.ok(urlService.createShortUrl(urlRequestDto));
    }
}
