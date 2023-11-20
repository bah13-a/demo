package com.docs.project.event.eventHandlers;

import com.docs.project.event.DocumentReceivedEvent;
import com.docs.project.event.TextSearchAndReplaceEvent;
import com.docs.project.rest.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TextSearchAndReplaceEventHandler {

    private final KafkaTemplate<String, TextSearchAndReplaceEvent> kafkaTemplate;
    private static final String TOPIC = "File-Text-Updated-Topic";

    @Autowired
    public TextSearchAndReplaceEventHandler(KafkaTemplate<String, TextSearchAndReplaceEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void handleUpdateTextEvent(String fileName, FileData fileData) {

        TextSearchAndReplaceEvent event = new TextSearchAndReplaceEvent (fileName,fileData);
        kafkaTemplate.send(TOPIC, event);
    }

}
