package avisek.example.redirectionservice.config;

import avisek.example.redirectionservice.dto.ClickEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProducerProperties.class)
@EnableKafka
public class KafkaProducerConfig {
    private final KafkaProducerProperties kafkaProducerProperties;
    @Bean
    public ProducerFactory<String, ClickEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerProperties.getProducer().getRetries());
        config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaProducerProperties.getProducer().getRetryBackoffMs());
        config.put(ProducerConfig.RETRY_BACKOFF_MAX_MS_CONFIG,kafkaProducerProperties.getProducer().getRetryBackoffMaxMs());
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProducerProperties.getProducer().getEnableIdempotence());

        return new DefaultKafkaProducerFactory<>(
                config,
                new StringSerializer(),
                new JacksonJsonSerializer<>()
        );
    }

    @Bean
    public KafkaTemplate<String, ClickEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}