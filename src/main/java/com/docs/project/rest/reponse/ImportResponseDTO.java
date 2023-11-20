package com.docs.project.rest.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportResponseDTO {
    private String filename;

    public ImportResponseDTO(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "File " + filename + " imported successfully!";
    }
}

