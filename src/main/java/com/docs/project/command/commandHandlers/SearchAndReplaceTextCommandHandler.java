package com.docs.project.command.commandHandlers;

import com.docs.project.command.SearchAndReplaceTextCommand;
import com.docs.project.event.eventHandlers.DocumentReceivedEventHandler;
import com.docs.project.event.eventHandlers.TextSearchAndReplaceEventHandler;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SearchAndReplaceTextCommandHandler {
    private final FileDataService fileDataService;
    private final TextSearchAndReplaceEventHandler textSearchAndReplaceEventHandler;

    @Autowired
    public SearchAndReplaceTextCommandHandler(FileDataService fileDataService, TextSearchAndReplaceEventHandler textSearchAndReplaceEventHandler) {
        this.fileDataService = fileDataService;
        this.textSearchAndReplaceEventHandler = textSearchAndReplaceEventHandler;
    }
    /**
     * Handles the search and replace text command.
     *
     * @param command The command containing the details for search and replace.
     * @return The FileData after the search and replace operation.
     * @throws IOException If an error occurs during the search and replace operation.
     */
    public FileData handle(SearchAndReplaceTextCommand command) throws IOException {
        FileData fileData= fileDataService.searchAndReplaceText(command.getFilename(),command.getTextSearched(), command.getNewText());

        fileDataService.saveFileData(fileData);
        textSearchAndReplaceEventHandler.handleUpdateTextEvent(fileData.getFileName(),fileData);
        return fileData;
    }
}
