package avisek.example.redirectionservice.service;


import avisek.example.redirectionservice.Entity.Url;
import avisek.example.redirectionservice.exception.ResourceNotFoundException;
import avisek.example.redirectionservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlLookupService {
    private final UrlRepository urlRepository;

    @Cacheable(value = "url",key = "#shortUrlCode")
    public String getOriginalUrl(String shortUrlCode) {
        Url url =  urlRepository.findByShortUrlCode(shortUrlCode)
                .orElseThrow(()-> new ResourceNotFoundException("No url found"));
        return url.getOriginalUrl();

    }
}
