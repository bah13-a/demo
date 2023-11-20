package com.docs.project.rest.repository;

import com.docs.project.rest.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileData,Long> {
    FileData findByFileTypeAndFileName(String name,String type);
    @Transactional
    FileData findByFileName(String fileName);
}
