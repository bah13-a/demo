package com.docs.project.rest.service;

import com.docs.project.GolbalControllerAdvice;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.repository.FileDocumentRepository;
import com.docs.project.rest.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Service class for handling file-related operations.
 */
@Service
@Slf4j
public class FileDataService {

    private final FileRepository fileDataRepository;
    private final FileDocumentRepository fileDocumentRepository;
    private final static String UPLOAD_PATH = "src/main/resources";
    private static final Logger logger = LoggerFactory.getLogger(FileDataService.class);

    /**
     * Constructs the FileDataService.
     *
     * @param fileDataRepository      The repository for file data.
     * @param fileDocumentRepository The repository for file documents.
     */
    @Autowired
    public FileDataService(FileRepository fileDataRepository, FileDocumentRepository fileDocumentRepository) {
        this.fileDataRepository = fileDataRepository;
        this.fileDocumentRepository = fileDocumentRepository;
    }
    /**
     * Saves file data to the database.
     *
     * @param fileData The file data to be saved.
     * @return The saved file data.
     */
    @Transactional
    public FileData saveFileData(FileData fileData) {
        FileData savedFileData = fileDataRepository.save(fileData);
        logger.info("File data saved successfully. File name: '{}'", savedFileData.getFileName());
        return savedFileData;
    }
    /**
     * Finds file data by type.
     *
     * @param fileName The name of the file.
     * @param type     The type of the file.
     * @return The found file data.
     */
    public FileData findFileDataByType(String fileName, String type) {
        FileData fileData = fileDataRepository.findByFileTypeAndFileName(fileName, type);
        logger.info("File data found by type successfully. File name: '{}', Type: '{}'", fileName, type);
        return fileData;
    }

    /**
     * Finds file document by file name and type.
     *
     * @param fileName The name of the file.
     * @param type     The type of the file.
     * @return The found file document.
     */
    public FileDataDocument fileDataDocumentByFileNameAndFileType(String fileName, String type) {
        FileDataDocument fileDataDocument = fileDocumentRepository.findByFileNameAndFileType(fileName, type).orElse(null);
        if (fileDataDocument != null) {
            logger.info("File data document found by file name and type successfully. File name: '{}', Type: '{}'", fileName, type);
        } else {
            logger.warn("File data document not found by file name and type. File name: '{}', Type: '{}'", fileName, type);
        }
        return fileDataDocument;
    }
    /**
     * Creates file data from a MultipartFile.
     *
     * @param file The file from which to create the data.
     * @return The created file data.
     */
    public FileData createFileDataFromFile(MultipartFile file) {
        FileData fileData = new FileData();
        fileData.setFileName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));

        try {
            byte[] fileContent = file.getBytes();
            fileData.setFileContent(fileContent);
            fileData.setFileType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        } catch (IOException e) {
            logger.error("Error creating file data from MultipartFile.", e);
            throw new RuntimeException("Error creating file data from MultipartFile.", e);
        }

        logger.info("File data created from file successfully. File name: '{}'", fileData.getFileName());
        return fileData;
    }
    /**
     * Searches and replaces text in the file.
     *
     * @param fileName     The name of the file.
     * @param textSearched The text to search for.
     * @param newText      The new replacement text.
     * @return The modified file data.
     * @throws IOException In case of IO error.
     */
    @Transactional
    public FileData searchAndReplaceText(String fileName, String textSearched, String newText) throws IOException {
        FileData fileData = fileDataRepository.findByFileName(fileName);
        String fileContent = new String(fileData.getFileContent(), StandardCharsets.UTF_8);

        if (fileContent.contains(textSearched)) {
            fileContent = fileContent.replace(textSearched, newText);
        }

        fileData.setFileContent(fileContent.getBytes());
        persistFileData(fileData);

        logger.info("Text replaced successfully in file. File name: '{}'", fileName);
        return fileData;
    }
    /**
     * Modifies the XML attribute in the provided XML content.
     *
     * @param xmlContent  The XML content.
     * @param tagName     The name of the XML tag to modify.
     * @param newTagValue The new attribute value.
     * @return The modified XML content.
     * @throws GolbalControllerAdvice.XmlModificationException In case of XML modification error.
     */
    public String modifyXmlAttribute(String xmlContent, String tagName, String newTagValue) throws GolbalControllerAdvice.XmlModificationException, IOException {
        String xmlToParse = readXmlFile(xmlContent);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlToParse)));

            modifyAttribute(document, tagName, newTagValue);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(bos);
            transformer.transform(new DOMSource(document), result);

            logger.info("XML attribute modified successfully. Tag name: '{}'", tagName);
            return bos.toString();
        } catch (Exception e) {
            logger.error("Error modifying XML.", e);
            throw new GolbalControllerAdvice.XmlModificationException("Error modifying XML.", e);
        }
    }
    /**
     * Reads the content of an XML file.
     *
     * @param filename The name of the XML file.
     * @return The content of the XML file.
     * @throws IOException In case of IO error.
     */
    public String readXmlFile(String filename) throws IOException {
        FileData fileData = fileDataRepository.findByFileName(filename);
        String xmlContent = new String(fileData.getFileContent(), StandardCharsets.UTF_8);
        logger.info("XML file read successfully. File name: '{}'", filename);
        return xmlContent;
    }
    /**
     * Finds file data by file name.
     *
     * @param fileName The name of the file.
     * @return The found file data.
     */
    public FileData findFileDataByFileName(String fileName) {
        FileData fileData = fileDataRepository.findByFileName(fileName);
        if (fileData != null) {
            logger.info("File data found by file name successfully. File name: '{}'", fileName);
        } else {
            logger.warn("File data not found by file name. File name: '{}'", fileName);
        }
        return fileData;
    }
    private void modifyAttribute(Document document, String tagName, String newTagValue) {
        Element element = (Element) document.getElementsByTagName(tagName).item(0);
        if (element != null) {
            element.setTextContent(newTagValue);
        }
    }
    private void persistFileData(FileData fileData) throws IOException {
        Path filePath = Paths.get(UPLOAD_PATH, fileData.getFileName());
        Files.write(filePath, fileData.getFileContent());
    }
}