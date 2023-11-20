package com.docs.project.event.eventHandlers;

import com.docs.project.event.DocumentReceivedEvent;
import com.docs.project.rest.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DocumentReceivedEventHandler {
    private final KafkaTemplate<String, DocumentReceivedEvent> kafkaTemplate;
    private static final String TOPIC = "File-Added-Topic";

    @Autowired
    public DocumentReceivedEventHandler(KafkaTemplate<String, DocumentReceivedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void handleFileImported(String fileName, FileData fileData) {

        DocumentReceivedEvent event = new DocumentReceivedEvent (fileName,fileData);
        kafkaTemplate.send(TOPIC, event);
    }
}
