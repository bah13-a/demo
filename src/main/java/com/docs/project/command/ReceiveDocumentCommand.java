package com.docs.project.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Command class representing the request to receive a document.
 * This class encapsulates information about the document being received.
 */
@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveDocumentCommand {

    /**
     * Represents the file to be received.
     */
    private MultipartFile file;
}
