package com.docs.project.rest.repository;

import com.docs.project.rest.model.FileDataDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface FileDocumentRepository extends MongoRepository<FileDataDocument,String> {
    Optional<FileDataDocument> findByFileNameAndFileType(String fileName, String fileType);
}
