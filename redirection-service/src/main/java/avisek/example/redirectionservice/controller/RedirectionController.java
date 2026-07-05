package avisek.example.redirectionservice.controller;


import avisek.example.redirectionservice.service.RedirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectionController {
    private final RedirectionService redirectionService;
    @GetMapping("/{shortUrlCode}")
    public ResponseEntity<Void>redirect(@PathVariable String shortUrlCode){
        String originalUrl = redirectionService.redirect(shortUrlCode);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
