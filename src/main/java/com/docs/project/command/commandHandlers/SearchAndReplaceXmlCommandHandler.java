package com.docs.project.command.commandHandlers;

import com.docs.project.command.SearchAndReplaceTextCommand;
import com.docs.project.command.SearchAndReplaceXmlCommand;
import com.docs.project.event.eventHandlers.TextSearchAndReplaceEventHandler;
import com.docs.project.event.eventHandlers.XmlSearchAndReplaceEventHandler;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SearchAndReplaceXmlCommandHandler {
    private final FileDataService fileDataService;
    private final XmlSearchAndReplaceEventHandler xmlSearchAndReplaceEventHandler;
    private final static  String UPLOAD_PATH="src/main/resources";


    @Autowired
    public SearchAndReplaceXmlCommandHandler(FileDataService fileDataService, XmlSearchAndReplaceEventHandler xmlSearchAndReplaceEventHandler) {
        this.fileDataService = fileDataService;
        this.xmlSearchAndReplaceEventHandler = xmlSearchAndReplaceEventHandler;
    }
    /**
     * Handles the search and replace XML command.
     *
     * @param command The command containing details for search and replace in XML.
     * @return The FileData after the search and replace operation.
     * @throws IOException If an error occurs during the search and replace operation.
     */
    public FileData handle(SearchAndReplaceXmlCommand command) throws IOException {
        FileData fileData= fileDataService.findFileDataByFileName(command.getFilename());
        String fileDataContent= fileDataService.modifyXmlAttribute(command.getFilename(),command.getTextSearched(), command.getNewText());
        fileData.setFileContent(fileDataContent.getBytes());

        Path filePath = Paths.get(UPLOAD_PATH, fileData.getFileName());
        Files.write(filePath, fileData.getFileContent());

        fileDataService.saveFileData(fileData);
        xmlSearchAndReplaceEventHandler.handleUpdateXmlEvent(fileData.getFileName(),fileData);
        return fileData;
    }
}
