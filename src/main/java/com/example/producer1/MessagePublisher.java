package com.example.producer1;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Component
public class MessagePublisher {

    @Value(value = "${app.name}")
    private String applicationName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private boolean procude = true;

    private final NewTopic newTopic;

    public MessagePublisher(KafkaTemplate<String, String> kafkaTemplate, NewTopic newTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.newTopic = newTopic;
    }

    public void startProducing() {
        procude = true;
        new Thread(() -> {
            while (procude) {
                this.send(applicationName + " " + generateLog());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void stopProducing() {
        procude = false;
    }

    private void send(String message) {
        kafkaTemplate.send(newTopic.name(), message);
    }

    private String generateLog() {
        byte[] array = new byte[20];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }


}
