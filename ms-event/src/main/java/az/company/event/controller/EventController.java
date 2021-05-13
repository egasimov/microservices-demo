package az.company.event.controller;

import az.company.event.model.Event;
import az.company.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author QasimovEY on 13.05.21
 */
@RequestMapping("/api/operation")
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/{operationId}/event")
    public ResponseEntity getAllEvents(@PathVariable String operationId) {
        return ResponseEntity.ok(eventService.getAllEvents(operationId));
    }

    @PostMapping("/{operationId}/event")
    public ResponseEntity placeEvent(@PathVariable("operationId") String operationId, @RequestBody Event event) {
        eventService.placeEvent(operationId, event);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{operationId}/status")
    public ResponseEntity getOperationStatus(@PathVariable String operationId) {
        return ResponseEntity.ok(eventService.getLastEvent(operationId));
    }


}
