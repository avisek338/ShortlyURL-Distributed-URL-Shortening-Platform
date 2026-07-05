package avisek.example.redirectionservice.service;


import avisek.example.redirectionservice.Entity.Url;
import avisek.example.redirectionservice.exception.ResourceNotFoundException;
import avisek.example.redirectionservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedirectionService {
    private final UrlLookupService urlLookupService;
    private final KafkaProducerService kafkaProducerService;
  //  private final UrlRepository urlRepository;

//    @Cacheable(value = "url",key = "#shortUrlCode")
//    public String getOriginalUrl(String shortUrlCode) {
//       Url url =  urlRepository.findByShortUrlCode(shortUrlCode)
//               .orElseThrow(()-> new ResourceNotFoundException("No url found"));
//       return url.getOriginalUrl();
//
//    }

    public String redirect(String shorturlCode){
        String originalurl = urlLookupService.getOriginalUrl(shorturlCode);
        // kafka producer
        kafkaProducerService.publish(shorturlCode,"url clicked");
        return originalurl;
    }
}
