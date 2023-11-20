package com.docs.project.rest.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplaceXmlResponseDTO {
    private String filename;
    private String oldText;
    private String newText;

    // Getter and Setter methods

    public ReplaceXmlResponseDTO(String filename, String oldText, String newText) {
        this.filename = filename;
        this.oldText = oldText;
        this.newText = newText;
    }

    @Override
    public String toString() {
        return "The content of "+ "\""+ oldText +"\"" + " is updated by "+"\"" + newText+" \" " +" successfully in " + filename;
    }
}
