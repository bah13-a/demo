package com.docs.project.event;

import com.docs.project.rest.model.FileData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TextSearchAndReplaceEvent {
    private  String fileName;
    private FileData fileData;

}
