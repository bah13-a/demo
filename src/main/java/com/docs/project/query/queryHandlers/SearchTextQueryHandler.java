package com.docs.project.query.queryHandlers;

import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.service.FileDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles the search text query.
 */
@Component
public class SearchTextQueryHandler {
    private static final Logger logger = LoggerFactory.getLogger(SearchTextQueryHandler.class);

    private final FileDataService fileDataService;
    private static final String FILE_TYPE = "txt";

    @Autowired
    public SearchTextQueryHandler(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    /**
     * Handles the search text query and returns the corresponding FileDataDocument.
     *
     * @param filename The name of the file to search.
     * @return The FileDataDocument matching the query.
     */
    public FileDataDocument handle(String filename) {
        try {
            return fileDataService.fileDataDocumentByFileNameAndFileType(filename, FILE_TYPE);
        } catch (Exception e) {
            logger.error("Error handling search text query for file: {}", filename, e);
            // Handle or rethrow the exception based on your requirements.
            throw new RuntimeException("Error handling search text query.", e);
        }
    }
}
