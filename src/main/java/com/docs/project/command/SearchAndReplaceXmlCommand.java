package com.docs.project.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Command class representing the request to search and replace text in an XML document.
 * This class encapsulates information about the XML document and the replacement.
 */
@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class SearchAndReplaceXmlCommand {
    String filename;
    String textSearched;
    String newText;
}
