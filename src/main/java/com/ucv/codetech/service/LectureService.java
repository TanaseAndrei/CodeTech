package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.repository.LectureRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepositoryGateway lectureRepositoryGateway;

    public Lecture saveOrUpdate(Lecture lecture) {
        return lectureRepositoryGateway.saveOrUpdate(lecture);
    }

    public void deleteLecture(Long id) {
        lectureRepositoryGateway.deleteById(id);
    }

    public Lecture getById(Long id) {
        return lectureRepositoryGateway.getById(id)
                .orElseThrow(() -> new AppException("The lecture does not exist!", HttpStatus.NOT_FOUND));
    }

    public String getAssociatedCourseFolder(Long lectureId) {
        return lectureRepositoryGateway.getAssociatedCourseFolder(lectureId)
                .orElseThrow(() -> new AppException("The lecture does not have an associated folder or the course does not exist!",
                        HttpStatus.NOT_FOUND));
    }

    public List<String> getLectureFiles(Long id) {
        Lecture lecture = lectureRepositoryGateway.getById(id)
                .orElseThrow(() -> new AppException("The lecture does not exist", HttpStatus.NOT_FOUND));
        return lecture.getLectureFileNames();
    }

    public String getLectureVideo(Long id) {
        Lecture lecture = lectureRepositoryGateway.getById(id)
                .orElseThrow(() -> new AppException("The lecture does not exist", HttpStatus.NOT_FOUND));
        return lecture.getLectureVideoName();
    }

    public boolean fileExistsInLecture(String fileName, Long id) {
        Lecture lecture = getById(id);
        return lecture.containsLectureFile(fileName);
    }
}
