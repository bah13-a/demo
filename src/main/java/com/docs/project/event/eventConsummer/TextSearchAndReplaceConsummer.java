package com.docs.project.event.eventConsummer;

import com.docs.project.event.DocumentReceivedEvent;
import com.docs.project.event.TextSearchAndReplaceEvent;
import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.repository.FileDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class TextSearchAndReplaceConsummer {
    private final FileDocumentRepository fileDataDocument;

    @Autowired
    public TextSearchAndReplaceConsummer(FileDocumentRepository fileDataDocument) {
        this.fileDataDocument = fileDataDocument;
    }

    @KafkaListener(topics = "File-Text-Updated-Topic", groupId = "kafka-id")
    public void consumeTextSearchAndReplaceEvent(TextSearchAndReplaceEvent textSearchAndReplaceEvent) {
        System.out.println("Received FileData event: " + textSearchAndReplaceEvent);
        FileDataDocument fileData= new FileDataDocument();
        fileData.setFileName(textSearchAndReplaceEvent.getFileName());
        fileData.setFileContent(textSearchAndReplaceEvent.getFileData().getFileContent());
        fileData.setId(textSearchAndReplaceEvent.getFileData().getId().toString());
        fileData.setFileType(textSearchAndReplaceEvent.getFileData().getFileType());
        fileDataDocument.save(fileData);
    }
}
