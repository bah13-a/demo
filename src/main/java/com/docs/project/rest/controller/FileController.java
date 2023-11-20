package com.docs.project.rest.controller;

import com.docs.project.command.ReceiveDocumentCommand;
import com.docs.project.command.SearchAndReplaceTextCommand;
import com.docs.project.command.SearchAndReplaceXmlCommand;
import com.docs.project.command.commandHandlers.ReceiveDocumentCommandHandler;
import com.docs.project.command.commandHandlers.SearchAndReplaceTextCommandHandler;
import com.docs.project.command.commandHandlers.SearchAndReplaceXmlCommandHandler;
import com.docs.project.query.SearchTextQuery;
import com.docs.project.query.queryHandlers.SearchTextQueryHandler;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.reponse.ImportResponseDTO;
import com.docs.project.rest.reponse.ReplaceTextResponseDTO;
import com.docs.project.rest.reponse.ReplaceXmlResponseDTO;
import com.docs.project.rest.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ReceiveDocumentCommandHandler receiveDocumentCommandHandler;
    private final SearchTextQueryHandler   searchTextQueryHandler;
    private final SearchAndReplaceTextCommandHandler searchAndReplaceTextCommandHandler;
    private final SearchAndReplaceXmlCommandHandler searchAndReplaceXmlCommandHandler;

    @Autowired
    public FileController(ReceiveDocumentCommandHandler receiveDocumentCommandHandler,SearchTextQueryHandler  searchTextQueryHandler
    ,SearchAndReplaceTextCommandHandler searchAndReplaceTextCommandHandler,SearchAndReplaceXmlCommandHandler searchAndReplaceXmlCommandHandler) {
        this.receiveDocumentCommandHandler = receiveDocumentCommandHandler;
        this.searchTextQueryHandler =searchTextQueryHandler;
        this.searchAndReplaceTextCommandHandler=searchAndReplaceTextCommandHandler;
        this.searchAndReplaceXmlCommandHandler =searchAndReplaceXmlCommandHandler;
    }

    /**
     * Imports a file using a multipart file.
     *
     * @param file The multipart file to import.
     * @return ResponseEntity indicating the success of the file import.
     */
    @PostMapping("/import")
    public ResponseEntity<String> importFile(@RequestParam("file") MultipartFile file) {
        ReceiveDocumentCommand receiveDocumentCommand = new ReceiveDocumentCommand(file);
        receiveDocumentCommandHandler.handle(receiveDocumentCommand);

        ImportResponseDTO responseDTO = new ImportResponseDTO(file.getOriginalFilename());
        return ResponseEntity.ok(responseDTO.toString());
    }
    /**
     * Replaces a phrase in a text file.
     *
     * @param filename The name of the file.
     * @param oldText  The old text to replace.
     * @param newText  The new text to replace with.
     * @return Object representing the result of the operation.
     * @throws IOException If an I/O error occurs.
     */
    @PostMapping("/replace/text")
    public ResponseEntity<String> replacePhraseText(@RequestParam("filename") String filename,
                                                                    @RequestParam("oldText") String oldText,
                                                                    @RequestParam("newText") String newText) throws IOException {
        SearchAndReplaceTextCommand searchAndReplaceTextCommand = new SearchAndReplaceTextCommand(filename, oldText, newText);
        FileData result = searchAndReplaceTextCommandHandler.handle(searchAndReplaceTextCommand);

        ReplaceTextResponseDTO responseDTO = new ReplaceTextResponseDTO(filename, oldText, newText);
        return ResponseEntity.ok(responseDTO.toString());
    }

    /**
     * Replaces a phrase in an XML file.
     *
     * @param filename The name of the file.
     * @param oldText  The old text to replace.
     * @param newText  The new text to replace with.
     * @return FileData representing the updated file data.
     * @throws IOException If an I/O error occurs.
     */

    @PostMapping("/replace/xml")
    public ResponseEntity<String>  replacePhraseXml(@RequestParam("filename") String filename,@RequestParam("oldText") String oldText,
                                @RequestParam("newText") String newText) throws IOException {
        SearchAndReplaceXmlCommand searchAndReplaceXmlCommand = new SearchAndReplaceXmlCommand(filename, oldText, newText);
        FileData result = searchAndReplaceXmlCommandHandler.handle(searchAndReplaceXmlCommand);

        ReplaceXmlResponseDTO responseDTO = new ReplaceXmlResponseDTO(filename, oldText, newText);
        return ResponseEntity.ok(responseDTO.toString());
    }
}