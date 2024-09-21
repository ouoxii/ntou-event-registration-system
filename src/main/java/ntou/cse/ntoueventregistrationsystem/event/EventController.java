package ntou.cse.ntoueventregistrationsystem.event;

import ntou.cse.ntoueventregistrationsystem.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<Event> getEvents() {
        return service.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<Void> postEvent(@RequestBody Event event, @AuthenticationPrincipal CustomUserDetails userDetails) {
        event.setCreatorId(userDetails.getId());
        event.setRollcallEndTime(event.getStartTime());
        service.createEvent(event);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable("id") String id) {
        return service.getEventBy(id);
    }

    @PutMapping
    public ResponseEntity<Void> putEvent(@RequestBody Event event,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId().equals(service.getEventBy(event.getId()).getCreatorId())) {
            event.setCreatorId(userDetails.getId());
            service.updateEvent(event);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/search")
    public List<Event> getEvents(@RequestParam("keyword") String keyword) {
        return service.getEventsByTitleLike(keyword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") String id,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getId().equals(service.getEventBy(id).getCreatorId())) {
            service.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/restrict/{id}")
    public void changeState(@PathVariable("id") String id) {
        service.swapState(id);
    }

    @GetMapping("/userEvent")
    public List<Event> getUserEvent(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return service.getAllEventsByCreatorId(userDetails.getId());
    }

    @PostMapping("/rollcall/{id}")
    public void startRollcall(@PathVariable("id") String id,
                              @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rollcallTime) {
        service.setRollcall(id, rollcallTime);
    }
}
