package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.model.LectureWrapper;
import com.ucv.codetech.service.LectureService;
import com.ucv.codetech.service.LectureWrapperService;
import com.ucv.codetech.service.MediaRestClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Facade
@AllArgsConstructor
@Slf4j
public class LectureFacade {

    private final MediaRestClientService mediaRestClientService;
    private final LectureService lectureService;
    private final LectureWrapperService lectureWrapperService;

    @Transactional
    public void delete(Long id) {
        List<String> filesToDelete = new ArrayList<>(lectureService.getLectureFiles(id));
        filesToDelete.add(lectureService.getLectureVideo(id));
        String folder = lectureService.getAssociatedCourseFolder(id);
        mediaRestClientService.deleteFiles(folder, filesToDelete);
        lectureService.deleteLecture(id);
        log.info("Deleted lecture with id {}", id);
    }

    @Transactional
    public void uploadFiles(Long id, MultipartFile[] multipartFiles) {
        log.info("Uploading {} files to lecture {}", multipartFiles.length, id);
        Lecture lecture = lectureService.findById(id);
        String folder = lectureService.getAssociatedCourseFolder(id);
        lecture.setLectureFileNames(sendFilesToFolder(multipartFiles, folder));
        lectureService.saveOrUpdate(lecture);
        log.info("Uploaded {} files to lecture {}", multipartFiles.length, id);
    }

    @Transactional
    public void deleteFile(Long lectureId, String fileName) {
        log.info("Deleting the file {} from the lecture {}", fileName, lectureId);
        String folder = lectureService.getAssociatedCourseFolder(lectureId);
        Lecture lecture = lectureService.findById(lectureId);
        if (!lecture.containsLectureFile(fileName)) {
            log.warn("The file {} does not exist in the lecture", fileName);
            throw new AppException("The file doesn't exist in the lecture", HttpStatus.NOT_FOUND);
        }
        mediaRestClientService.deleteFile(folder, fileName);
        lecture.deleteLectureFile(fileName);
        lectureService.saveOrUpdate(lecture);
        log.info("Deleted the file {} from the lecture {}", fileName, lectureId);

    }

    @Transactional
    public void completeLecture(Long id) {
        log.info("Completing the lecture with id {}", id);
        LectureWrapper lectureWrapper = lectureWrapperService.findById(id);
        if(lectureWrapper.isCompletedLecture()) {
            throw new AppException(String.format("The lecture wrapper with the id %d is already completed", id), HttpStatus.BAD_REQUEST);
        }
        lectureWrapper.completeLecture();
        lectureWrapperService.saveOrUpdate(lectureWrapper);
        log.info("Completed the lecture with id {}", id);
    }

    private List<String> sendFilesToFolder(MultipartFile[] multipartFiles, String courseFolder) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = mediaRestClientService.addFileToFolder(courseFolder, multipartFile);
            fileNames.add(fileName);
        }
        return fileNames;
    }
}
