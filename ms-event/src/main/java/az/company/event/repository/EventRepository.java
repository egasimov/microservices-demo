package az.company.event.repository;

import az.company.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author QasimovEY on 13.05.21
 */
@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final MongoTemplate mongoTemplate;

    private final String collectionName = "events";

    public List<Event> loadEvents(String operationId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("operationId").is(operationId));

        return mongoTemplate.find(query, Event.class, collectionName);
    }

    public Event loadLastEvent(String operationId) {
        //db.events.find({"operationId":"1"}).projection({}).sort({"generationTime" : -1})
        Query query = new Query();
        query.addCriteria(Criteria.where("operationId").is(operationId))
                .with(Sort.by(Sort.Direction.DESC, "generationTime"))
                .limit(1);
        System.out.println(query.getQueryObject());
        List<Event> result = mongoTemplate.find(query, Event.class, collectionName);
        return result.isEmpty() ? null : result.get(0);
    }

}
