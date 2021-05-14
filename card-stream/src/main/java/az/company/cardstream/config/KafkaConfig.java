package az.company.cardstream.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author QasimovEY on 08.04.21
 */
@Component
@Getter
@Setter
public class KafkaConfig {
    @Value(value = "${app.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.application-id-config:card-stream}")
    private String applicationId;


    @Value("${app.kafka.topic.producer}")
    private String producerTopic;

    @Value("${app.kafka.topic.consumer}")
    private String consumerTopic;

}
