package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.service.LectureService;
import com.ucv.codetech.service.file.FileService;
import com.ucv.codetech.service.file.ZipService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Facade
@AllArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final FileService fileService;
    private final ZipService zipService;

    @Transactional
    public void delete(Long id) {
        try {
            List<String> filesToDelete = new ArrayList<>(lectureService.getLectureFiles(id));
            filesToDelete.add(lectureService.getLectureVideo(id));
            String folder = lectureService.getAssociatedCourseFolder(id);
            fileService.deleteFiles(filesToDelete, folder);
            lectureService.deleteLecture(id);
        } catch (IOException ioException) {
            throw new AppException("An error occurred while trying to delete the files from a lecture", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void uploadFiles(Long id, MultipartFile[] multipartFiles) {
        try {
            Lecture lecture = lectureService.getById(id);
            String folder = lectureService.getAssociatedCourseFolder(id);
            lecture.setLectureFileNames(getLectureFileNames(multipartFiles, folder));
        } catch (IOException ioException) {
            throw new AppException("Error occurred while trying to move the lecture files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Resource zipFiles(Long lectureId) {
        try {
            String folder = lectureService.getAssociatedCourseFolder(lectureId);
            List<String> lectureFilesNames = lectureService.getLectureFiles(lectureId);
            String zipPath = zipService.zipFiles(lectureFilesNames, folder);
            return new UrlResource(Paths.get(zipPath).toUri());
        } catch (IOException ioException) {
            throw new AppException("There was a problem while zipping the files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Resource downloadFile(Long lectureId, String fileName) {
        try {
            String folder = lectureService.getAssociatedCourseFolder(lectureId);
            if (!lectureService.fileExistsInLecture(fileName, lectureId)) {
                throw new AppException("The file does not exist in the lecture", HttpStatus.NOT_FOUND);
            }
            return fileService.getFileAsResource(folder, fileName);
        } catch (IOException ioException) {
            throw new AppException("There was a problem while downloading the file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteFile(Long lectureId, String fileName) {
        try {
            String folder = lectureService.getAssociatedCourseFolder(lectureId);
            Lecture lecture = lectureService.getById(lectureId);
            if (!lecture.containsLectureFile(fileName)) {
                throw new AppException("The file doesn't exist in the lecture", HttpStatus.NOT_FOUND);
            }
            fileService.deleteFile(fileName, folder);
            lecture.deleteLectureFile(fileName);
            lectureService.saveOrUpdate(lecture);
        } catch (IOException ioException) {
            throw new AppException("Error occurred while deleting the lecture file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> getLectureFileNames(MultipartFile[] multipartFiles, String courseFolder) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            fileNames.add(fileService.moveFile(multipartFile, courseFolder));
        }
        return fileNames;
    }
}
