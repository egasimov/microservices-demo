package az.company.card.adapter.out.stream;

import az.company.card.application.out.EnqueueOrderPort;
import az.company.card.config.properties.KafkaProperties;
import az.company.card.domain.CardOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author QasimovEY on 14.05.21
 */
@RequiredArgsConstructor
@Repository
public class CardOrderStreamAdapter implements EnqueueOrderPort {

    private final KafkaTemplate<String, CardOrderEntity> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    @Override
    @Transactional
    public boolean enqueue(CardOrderEntity entity) {

        //should be in single transaction
        try {
            kafkaTemplate.send(kafkaProperties.getTopicCardOrderStorge(),
                    entity.getOperationId(), entity);
            kafkaTemplate.send(kafkaProperties.getTopicCardOrderProcessing(),
                    entity.getOperationId(), entity);

            kafkaTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
