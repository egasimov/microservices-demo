package az.company.cardstream.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @Author QasimovEY on 08.04.21
 */

@Component
@Slf4j
public class KafkaStreamsHealthIndicator implements HealthIndicator {

    private Exception caughtException = null;
    private KafkaStreams kafkaStreams = null;

    @Override
    public Health health() {

        boolean isKafkaHealthy = caughtException == null && kafkaStreams != null &&
                (kafkaStreams.state().isRunning() ||
                        kafkaStreams.state() == KafkaStreams.State.CREATED ||
                        kafkaStreams.state() == KafkaStreams.State.REBALANCING);

        if (isKafkaHealthy) {
            return Health
                    .up()
                    .withDetail("status", "Kafka Streams Running")
                    .build();
        } else {
            String errorDetails = Optional.ofNullable(caughtException)
                    .filter(Objects::nonNull)
                    .map(ex -> ex.getMessage())
                    .orElse(kafkaStreams.state().name());
            return Health
                    .down()
                    .withDetail("status", "Not Available")
                    .withDetail("error", errorDetails)
                    .build();
        }
    }

    public void setException(Exception caughtException) {
        this.caughtException = caughtException;
    }

    public void setKafkaStreams(KafkaStreams kafkaStreams) {
        this.kafkaStreams = kafkaStreams;
    }

}

