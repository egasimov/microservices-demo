package az.company.event.service;

import az.company.event.config.properties.KafkaProperties;
import az.company.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author QasimovEY on 13.05.21
 */
@Service
@RequiredArgsConstructor
public class EventProducer {

    @Qualifier(value = "eventKafkaTemplate")
    private final KafkaTemplate<String, Event> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public boolean publishEvent(String operationId, Event event) {
        try {
            kafkaTemplate.send(kafkaProperties.getTopicEventStorage(),
                    operationId, event);
            kafkaTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
