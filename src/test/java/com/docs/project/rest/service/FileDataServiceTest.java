package com.docs.project.rest.service;

import com.docs.project.rest.service.FileDataService;
import com.docs.project.rest.model.FileData;
import com.docs.project.rest.model.FileDataDocument;
import com.docs.project.rest.repository.FileDocumentRepository;
import com.docs.project.rest.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FileDataServiceTest {

    @Mock
    private FileRepository fileDataRepository;

    @Mock
    private FileDocumentRepository fileDocumentRepository;

    @InjectMocks
    private FileDataService fileDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveFileDataTest() {
        FileData fileData = new FileData();
        when(fileDataRepository.save(any(FileData.class))).thenReturn(fileData);

        FileData savedFileData = fileDataService.saveFileData(new FileData());

        assertNotNull(savedFileData);
        assertEquals("File data saved successfully.", getMessage("saveFileDataTest"));
        verify(fileDataRepository, times(1)).save(any(FileData.class));
    }

    @Test
    void findFileDataByTypeTest() {
        FileData fileData = new FileData();
        when(fileDataRepository.findByFileTypeAndFileName(anyString(), anyString())).thenReturn(fileData);

        FileData foundFileData = fileDataService.findFileDataByType("test.txt", "text");

        assertNotNull(foundFileData);
        assertEquals("File data found by type successfully.", getMessage("findFileDataByTypeTest"));
        verify(fileDataRepository, times(1)).findByFileTypeAndFileName(anyString(), anyString());
    }

    @Test
    void fileDataDocumentByFileNameAndFileTypeTest() {
        FileDataDocument fileDataDocument = new FileDataDocument();
        when(fileDocumentRepository.findByFileNameAndFileType(anyString(), anyString())).thenReturn(Optional.of(fileDataDocument));

        FileDataDocument foundFileDataDocument = fileDataService.fileDataDocumentByFileNameAndFileType("test.xml", "xml");

        assertNotNull(foundFileDataDocument);
        assertEquals("File data document found by file name and type successfully.", getMessage("fileDataDocumentByFileNameAndFileTypeTest"));
        verify(fileDocumentRepository, times(1)).findByFileNameAndFileType(anyString(), anyString());
    }

    @Test
    void createFileDataFromFileTest() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        FileData createdFileData = fileDataService.createFileDataFromFile(multipartFile);

        assertNotNull(createdFileData);
        assertEquals("File data created from file successfully.", getMessage("createFileDataFromFileTest"));
    }

    @Test
    void searchAndReplaceTextTest() throws IOException {
        FileData fileData = new FileData();
        fileData.setFileName("test.txt");
        fileData.setFileType("text");
        fileData.setFileContent("Hello, World!".getBytes());
        when(fileDataRepository.findByFileName(anyString())).thenReturn(fileData);

        FileData modifiedFileData = fileDataService.searchAndReplaceText("test.txt", "Hello", "Hi");

        assertNotNull(modifiedFileData);
        assertEquals("Text replaced successfully.", getMessage("searchAndReplaceTextTest"));
    }

    @Test
    void modifyXmlAttributeTest() throws IOException {
        String xmlContent = "<root><element attribute='oldValue'>content</element></root>";
        when(fileDataRepository.findByFileName(anyString())).thenReturn(new FileData());

        String modifiedXml = fileDataService.modifyXmlAttribute(xmlContent, "attribute", "newValue");

        assertNotNull(modifiedXml);
        assertTrue(modifiedXml.contains("newValue"));
        assertEquals("XML attribute modified successfully.", getMessage("modifyXmlAttributeTest"));
    }

    @Test
    void readXmlFileTest() throws IOException {
        FileData fileData = new FileData();
        fileData.setFileName("test.xml");
        fileData.setFileType("xml");
        fileData.setFileContent("<root><element>content</element></root>".getBytes());
        when(fileDataRepository.findByFileName(anyString())).thenReturn(fileData);

        String xmlContent = fileDataService.readXmlFile("test.xml");

        assertNotNull(xmlContent);
        assertTrue(xmlContent.contains("<element>content</element>"));
        assertEquals("XML file read successfully.", getMessage("readXmlFileTest"));
    }

    @Test
    void findFileDataByFileNameTest() {
        FileData fileData = new FileData();
        when(fileDataRepository.findByFileName(anyString())).thenReturn(fileData);

        FileData foundFileData = fileDataService.findFileDataByFileName("test.txt");

        assertNotNull(foundFileData);
        assertEquals("File data found by file name successfully.", getMessage("findFileDataByFileNameTest"));
        verify(fileDataRepository, times(1)).findByFileName(anyString());
    }

    private String getMessage(String testName) {
        switch (testName) {
            case "saveFileDataTest":
                return "File data saved successfully.";
            case "findFileDataByTypeTest":
                return "File data found by type successfully.";
            case "fileDataDocumentByFileNameAndFileTypeTest":
                return "File data document found by file name and type successfully.";
            case "createFileDataFromFileTest":
                return "File data created from file successfully.";
            case "searchAndReplaceTextTest":
                return "Text replaced successfully.";
            case "modifyXmlAttributeTest":
                return "XML attribute modified successfully.";
            case "readXmlFileTest":
                return "XML file read successfully.";
            case "findFileDataByFileNameTest":
                return "File data found by file name successfully.";
            // Add more cases for other test names
            default:
                return "Unknown test: " + testName;

        }
    }
}
