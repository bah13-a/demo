package com.docs.project.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Represents a command to search and replace text in a document.
 * This class encapsulates information about the document and the replacement.
 */

@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class SearchAndReplaceTextCommand {
    String filename;
    String textSearched;
    String newText;
}
