package com.docs.project.event.eventConsummer;

import com.docs.project.event.TextSearchAndReplaceEvent;
import com.docs.project.event.XmlSearchAndReplaceEvent;
import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.repository.FileDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class XmlSearchAndReplaceConsummer {
    private final FileDocumentRepository fileDataDocument;

    @Autowired
    public XmlSearchAndReplaceConsummer(FileDocumentRepository fileDataDocument) {
        this.fileDataDocument = fileDataDocument;
    }

    @KafkaListener(topics = "File-Xml-Updated-Topic", groupId = "kafka-id")
    public void consumeTextSearchAndReplaceEvent(XmlSearchAndReplaceEvent xmlSearchAndReplaceEvent) {
        System.out.println("Received FileData event: " + xmlSearchAndReplaceEvent);
        FileDataDocument fileData= new FileDataDocument();
        fileData.setFileName(xmlSearchAndReplaceEvent.getFileName());
        fileData.setFileContent(xmlSearchAndReplaceEvent.getFileData().getFileContent());
        fileData.setId(xmlSearchAndReplaceEvent.getFileData().getId().toString());
        fileData.setFileType(xmlSearchAndReplaceEvent.getFileData().getFileType());
        fileDataDocument.save(fileData);
    }
}
