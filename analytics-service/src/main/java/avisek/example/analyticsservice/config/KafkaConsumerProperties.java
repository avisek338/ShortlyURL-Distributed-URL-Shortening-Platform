package avisek.example.analyticsservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka")
public class KafkaConsumerProperties {

    private String bootstrapServers;
    private Consumer consumer = new Consumer();

    @Getter
    @Setter
    public static class Consumer{
        private String groupId;

        private String autoOffsetReset;

        private boolean enableAutoCommit;

        private int maxPollRecords;

        private int fetchMinBytes;

        private int fetchMaxWaitMs;
    }
}