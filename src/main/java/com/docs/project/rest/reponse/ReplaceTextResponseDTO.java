package com.docs.project.rest.reponse;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplaceTextResponseDTO {
    private String filename;
    private String oldText;
    private String newText;

    // Getter and Setter methods

    public ReplaceTextResponseDTO(String filename, String oldText, String newText) {
        this.filename = filename;
        this.oldText = oldText;
        this.newText = newText;
    }

    @Override
    public String toString() {
        return "\""+ oldText +"\"" + " replaced by "+"\"" + newText+" \" " +" successfully in " + filename;
    }
}
