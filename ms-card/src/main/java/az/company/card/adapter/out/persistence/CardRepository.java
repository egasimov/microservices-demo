package az.company.card.adapter.out.persistence;

import az.company.card.application.out.CreateCardPort;
import az.company.card.application.out.LoadCardInfoPort;
import az.company.card.domain.CardEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CardRepository implements CreateCardPort, LoadCardInfoPort {

    private final MongoTemplate mongoTemplate;
    private final String collectionName = "cards";

    @Override
    public boolean save(CardEntity cardEntity) {
        try {
            mongoTemplate.save(cardEntity, collectionName);
        } catch (Exception ex) {
            log.info("Unable to save data into MongoDB, for {}", cardEntity);
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public CardEntity load(String operationId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("operationId").is(operationId));

        List<CardEntity> cardEntities = mongoTemplate.find(query, CardEntity.class, collectionName);
        return cardEntities.isEmpty() ? null : cardEntities.get(0);
    }
}
