package com.ucv.codetech.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class LectureFilesDto {

    private List<MultipartFile> lectureFiles;
}
