package avisek.example.urlservice.service;

import avisek.example.urlservice.dto.UrlRequestDto;
import avisek.example.urlservice.dto.UrlResponseDto;
import avisek.example.urlservice.entity.Url;
import avisek.example.urlservice.exception.ShortCodeAlreadyExistException;
import avisek.example.urlservice.repository.SequenceRepository;
import avisek.example.urlservice.repository.UrlRepository;
import avisek.example.urlservice.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final SequenceRepository sequenceRepository;

    @Value("${app.url.redirection-service-url}")
    private String baseUrl;

    public UrlResponseDto createShortUrl(UrlRequestDto urlRequestDto) {
        String shortUrlCode = resolveShortUrlCode(urlRequestDto);
        Url url = buildUrl(urlRequestDto, shortUrlCode);
        Url savedUrl = urlRepository.save(url);
        return toResponseDto(savedUrl);
    }

    private String resolveShortUrlCode(UrlRequestDto urlRequestDto) {
        String alias = urlRequestDto.getAlias();

        if (alias == null || alias.isBlank()) {
            long sequenceId = sequenceRepository.getNextSequence();
            return Base62Encoder.encode(sequenceId);
        }

        if (urlRepository.existsByShortUrlCode(alias)) {
            throw new ShortCodeAlreadyExistException("alias already exists");
        }
        return alias;
    }

    private Url buildUrl(UrlRequestDto urlRequestDto, String shortUrlCode) {
        Url url = new Url();
        url.setOriginalUrl(urlRequestDto.getLongUrl());
        url.setShortUrlCode(shortUrlCode);
        return url;
    }

    private UrlResponseDto toResponseDto(Url url) {
        UrlResponseDto response = new UrlResponseDto();
        response.setShortUrlCode(url.getShortUrlCode());
        response.setShortUrl(baseUrl + url.getShortUrlCode());
        return response;
    }
}
