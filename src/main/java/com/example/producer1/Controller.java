package com.example.producer1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/")
@RequestMapping("/api")
public class Controller {

    private final MessagePublisher messagePublisher;

    public Controller(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @GetMapping("/start")
    public ResponseEntity<Void> startProducingLogs() {
        messagePublisher.startProducing();
        return ResponseEntity.ok(null);
    }

    @GetMapping("/stop")
    public ResponseEntity<Void> stopProducingLogs() {
        messagePublisher.stopProducing();
        return ResponseEntity.ok(null);
    }
}
