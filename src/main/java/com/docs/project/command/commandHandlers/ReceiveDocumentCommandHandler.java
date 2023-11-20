package com.docs.project.command.commandHandlers;

import com.docs.project.command.ReceiveDocumentCommand;
import com.docs.project.command.SearchAndReplaceTextCommand;
import com.docs.project.event.TextSearchAndReplaceEvent;
import com.docs.project.event.eventHandlers.DocumentReceivedEventHandler;
import com.docs.project.event.eventHandlers.TextSearchAndReplaceEventHandler;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.service.FileDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class ReceiveDocumentCommandHandler {

    private final FileDataService fileDataService;
    private final DocumentReceivedEventHandler documentReceivedEventHandler;


    @Autowired
    public ReceiveDocumentCommandHandler(FileDataService fileDataService,DocumentReceivedEventHandler documentReceivedEventHandler
                ) {
        this.fileDataService = fileDataService;
        this.documentReceivedEventHandler=documentReceivedEventHandler;
    }
    /**
     * Handles the received document command.
     *
     * @param command The command containing the received document.
     */
    public void handle(ReceiveDocumentCommand command) {
        MultipartFile file = command.getFile();
        FileData fileData = fileDataService.createFileDataFromFile(file);
        String fileType = fileData.getFileType();

        switch (fileType) {
            case "xml":
            case "txt":
                fileDataService.saveFileData(fileData);
                documentReceivedEventHandler.handleFileImported(fileData.getFileName(),fileData);
                log.info("File '{}' successfully processed and saved.", fileData.getFileName());
                break;
            default:
                log.warn("Unsupported file type: {}", fileType);
        }


    }


}
