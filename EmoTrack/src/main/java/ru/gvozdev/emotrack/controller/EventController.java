package ru.gvozdev.emotrack.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gvozdev.emotrack.dto.EventDto;
import ru.gvozdev.emotrack.service.EventService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {

    private EventService eventService;


    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventDto EventDto) throws IOException, InterruptedException {
        return ResponseEntity.ok(eventService.createEvent(EventDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @GetMapping("/{userId}/{date}")
    public ResponseEntity<?> getEvents(@PathVariable Long userId,
                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return ResponseEntity.ok(eventService.getUserEventsByDay(userId, date));
    }

    @GetMapping("/{userId}/")
    public ResponseEntity<?> getEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getUserEvents(userId));
    }
}
