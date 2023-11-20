package com.docs.project.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "fileData")
public class FileDataDocument {

    @Id
    private String id;
    private String fileName;

    private String fileType;

    private byte[] fileContent;

    private LocalDateTime uploadDateTime;

}
