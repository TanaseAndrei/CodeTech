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

    private static final String LECTURE_WITH_ID_DOES_NOT_EXIST = "The lecture with id %d does not exist";

    private final LectureRepositoryGateway lectureRepositoryGateway;

    public Lecture saveOrUpdate(Lecture lecture) {
        return lectureRepositoryGateway.saveOrUpdate(lecture);
    }

    public void deleteLecture(Long id) {
        lectureRepositoryGateway.deleteById(id);
    }

    public Lecture findById(Long id) {
        return lectureRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(String.format(LECTURE_WITH_ID_DOES_NOT_EXIST, id), HttpStatus.NOT_FOUND));
    }

    public String getAssociatedCourseFolder(Long id) {
        return lectureRepositoryGateway.getAssociatedCourseFolder(id)
                .orElseThrow(() -> new AppException(String.format("The lecture with id %d does not have an associated folder", id),
                        HttpStatus.NOT_FOUND));
    }

    public List<String> getLectureFiles(Long id) {
        Lecture lecture = lectureRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(String.format(LECTURE_WITH_ID_DOES_NOT_EXIST, id), HttpStatus.NOT_FOUND));
        return lecture.getLectureFileNames();
    }

    public String getLectureVideo(Long id) {
        Lecture lecture = lectureRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(String.format(LECTURE_WITH_ID_DOES_NOT_EXIST, id), HttpStatus.NOT_FOUND));
        return lecture.getLectureVideoName();
    }

    public boolean lectureExistsInCourse(String lectureName, Long id) {
        return lectureRepositoryGateway.lectureExistsInCourse(lectureName, id);
    }
}
