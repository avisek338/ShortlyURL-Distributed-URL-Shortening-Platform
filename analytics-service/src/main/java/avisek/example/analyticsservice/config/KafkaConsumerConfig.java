package avisek.example.analyticsservice.config;


import avisek.example.analyticsservice.dto.ClickEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaConsumerProperties.class)
public class KafkaConsumerConfig {
    private final KafkaConsumerProperties KafkaConsumerProperties;
    @Bean
    public ConsumerFactory<String, ClickEvent>consumerFactory(){
        Map<String,Object>config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,KafkaConsumerProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG,KafkaConsumerProperties.getConsumer().getGroupId());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,KafkaConsumerProperties.getConsumer().getAutoOffsetReset());
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConsumerProperties.getConsumer().isEnableAutoCommit());
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaConsumerProperties.getConsumer().getMaxPollRecords());    // max 100 messages per batch
        config.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, KafkaConsumerProperties.getConsumer().getFetchMinBytes());       // no minimum size
        config.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, KafkaConsumerProperties.getConsumer().getFetchMaxWaitMs());  // wait 5 seconds

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new JacksonJsonDeserializer<>(ClickEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,ClickEvent>kafkaListenerContainerFactory(
            ConsumerFactory<String,ClickEvent>consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String,ClickEvent>factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        ExponentialBackOff backOff = new ExponentialBackOff(2000L,2.0);
        backOff.setMaxElapsedTime(8000L);
        factory.setCommonErrorHandler(new DefaultErrorHandler(backOff));
        return factory;
    }
}
