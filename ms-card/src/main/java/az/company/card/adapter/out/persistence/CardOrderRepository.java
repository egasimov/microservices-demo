package az.company.card.adapter.out.persistence;

import az.company.card.application.out.CheckOrderExistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author QasimovEY on 14.05.21
 */
@Repository
@RequiredArgsConstructor
public class CardOrderRepository implements CheckOrderExistencePort {

    private final MongoTemplate mongoTemplate;
    private final String collectionName = "cardOrders";

    @Override
    public boolean exist(String uuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(uuid));

        long count = mongoTemplate.count(query, collectionName);
        return count > 0;
    }
}
