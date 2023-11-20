package com.docs.project.event.eventConsummer;

import com.docs.project.event.DocumentReceivedEvent;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.repository.FileDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentReceivedConsummer {


    private final FileDocumentRepository fileDataDocument;

    @Autowired
    public DocumentReceivedConsummer(FileDocumentRepository fileDataDocument) {
        this.fileDataDocument = fileDataDocument;
    }

    /**
     * Listens to the "File-Added-Topic" Kafka topic and consumes DocumentReceivedEvent.
     *
     * @param fileReceivedEvent The event containing details of the received document.
     */
    @KafkaListener(topics = "File-Added-Topic", groupId = "kafka-id")
    public void consumeFileDataEvent(DocumentReceivedEvent fileReceivedEvent) {
        System.out.println("Received FileData event: " + fileReceivedEvent);
        FileDataDocument fileData= new FileDataDocument();
        fileData.setFileName(fileReceivedEvent.getFileName());
        fileData.setFileContent(fileReceivedEvent.getFileData().getFileContent());
        fileData.setId(fileReceivedEvent.getFileData().getId().toString());
        fileData.setFileType(fileReceivedEvent.getFileData().getFileType());
        fileDataDocument.save(fileData);
    }
}
