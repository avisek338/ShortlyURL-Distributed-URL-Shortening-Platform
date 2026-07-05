package avisek.example.redirectionservice.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka")
public class KafkaProducerProperties {
    private String bootstrapServers;
    private Producer producer = new Producer();
    @Getter
    @Setter
    public static class Producer{
        private Integer retries;

        private Long retryBackoffMs;

        private Long retryBackoffMaxMs;

        private Boolean enableIdempotence;
    }
}
