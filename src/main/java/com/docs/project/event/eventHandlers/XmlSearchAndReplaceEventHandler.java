package com.docs.project.event.eventHandlers;

import com.docs.project.event.TextSearchAndReplaceEvent;
import com.docs.project.event.XmlSearchAndReplaceEvent;
import com.docs.project.rest.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class XmlSearchAndReplaceEventHandler {
    private final KafkaTemplate<String, XmlSearchAndReplaceEvent> kafkaTemplate;
    private static final String TOPIC = "File-Xml-Updated-Topic";

    @Autowired
    public XmlSearchAndReplaceEventHandler(KafkaTemplate<String, XmlSearchAndReplaceEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void handleUpdateXmlEvent(String fileName, FileData fileData) {

        XmlSearchAndReplaceEvent event = new XmlSearchAndReplaceEvent (fileName,fileData);
        kafkaTemplate.send(TOPIC, event);
    }

}
