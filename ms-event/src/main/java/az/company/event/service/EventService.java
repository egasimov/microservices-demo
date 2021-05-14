package az.company.event.service;

import az.company.event.error.exception.RecordNotFoundException;
import az.company.event.model.Event;
import az.company.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author QasimovEY on 13.05.21
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventProducer eventProducer;
    private final EventRepository eventRepository;

    public List<Event> getAllEvents(String operationId) {
        return eventRepository.loadEvents(operationId);
    }

    public Event getLastEvent(String operationId) {
        Event event = eventRepository.loadLastEvent(operationId);
        if (event == null) {
            throw RecordNotFoundException.of("EVENT NOT FOUND FOR OPERATION, %s".formatted(operationId));
        }
        return event;
    }

    public void placeEvent(String operationId, Event event) {
        boolean isPublished = eventProducer.publishEvent(operationId, event);

        if (!isPublished) {
            throw new RuntimeException("Unable to send Kafka, for operation Id %s".formatted(operationId));
        }

    }
}
