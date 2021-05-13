package az.company.card.application.out;

import az.company.card.adapter.out.external.model.Event;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface PublishEventPort {
    String placeEvent(String operationId, Event event);
}
