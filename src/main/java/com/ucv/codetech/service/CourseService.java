package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import com.ucv.codetech.repository.LectureRepositoryGateway;
import com.ucv.codetech.service.file.FileService;
import com.ucv.codetech.service.file.ZipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class CourseService {

    private static final String THE_SELECTED_COURSE_DOES_NOT_EXIST = "The selected course does not exist!";

    private final CourseRepositoryGateway courseRepositoryGateway;
    private final LectureRepositoryGateway lectureRepositoryGateway;
    private final FileService fileService;
    private final ZipService zipService;

    public Course createOrUpdate(Course course) {
        return courseRepositoryGateway.saveOrUpdate(course);
    }

    public void enableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        course.setAvailable(true);
        courseRepositoryGateway.saveOrUpdate(course);
    }

    public void disableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        course.setAvailable(false);
        courseRepositoryGateway.saveOrUpdate(course);
    }

    public Course getById(Long id) {
        return courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    //TODO paginatation and filtering
    public List<Course> getAll() {
        return courseRepositoryGateway.findAll();
    }

    @Transactional
    public List<String> deleteCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        List<String> videoNames = lectureRepositoryGateway.getLectureVideos(id);
        List<Lecture> lectures = lectureRepositoryGateway.getLecturesByCourseId(course.getId());
        List<String> lectureFiles = collectAllLectureFiles(lectures);
        courseRepositoryGateway.deleteById(id);
        return collectAllFilesToDelete(course.getCoverImageName(), videoNames, lectureFiles);
    }

    @Transactional
    public void addCourseLectureFiles(Long courseId, Long courseLectureId, MultipartFile[] multipartFiles) {
        try {
            Lecture lecture = lectureRepositoryGateway.findByLectureIdAndCourseId(courseLectureId, courseId)
                    .orElseThrow(() -> new AppException("The selected course lecture does not exist!", HttpStatus.NOT_FOUND));
            String courseFolder = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist!", HttpStatus.BAD_REQUEST));
            lecture.setLectureFileNames(getLectureFileNames(multipartFiles, courseFolder));
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public Resource downloadFile(Long courseId, Long courseLectureId, String fileName) {
        try {
            Lecture lecture = lectureRepositoryGateway.findByLectureIdAndCourseId(courseId, courseLectureId)
                    .orElseThrow(() -> new AppException("The course lecture does not exist", HttpStatus.NOT_FOUND));
            if (!lecture.getLectureFileNames().contains(fileName)) {
                throw new AppException("The file does not exist!", HttpStatus.NOT_FOUND);
            }
            String folderName = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist", HttpStatus.NOT_FOUND));
            return fileService.getFileAsResource(folderName, fileName);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public Resource zipLectureFiles(Long courseId, Long courseLectureId) {
        try {
            Lecture lecture = lectureRepositoryGateway.findByLectureIdAndCourseId(courseId, courseLectureId)
                    .orElseThrow(() -> new AppException("The course lecture does not exist", HttpStatus.NOT_FOUND));
            String folderName = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist", HttpStatus.NOT_FOUND));
            String zipFilePath = zipService.zipFiles(lecture.getLectureFileNames(), folderName);
            return new UrlResource(Paths.get(zipFilePath).toUri());
        } catch (IOException ioException) {
            throw new AppException("The files could not have been zipped", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean courseExistsByName(String name) {
        return courseRepositoryGateway.courseExistsByName(name);
    }

    public String getCourseFolderName(Long id) {
        return courseRepositoryGateway.getCourseFolderName(id)
                .orElseThrow(() -> new AppException("The course does not have an associated folder!", HttpStatus.NOT_FOUND));
    }

    private List<String> collectAllLectureFiles(List<Lecture> lectures) {
        return lectures.stream().map(Lecture::getLectureFileNames).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<String> collectAllFilesToDelete(String coverImageName, List<String> videoNames, List<String> lectureFiles) {
        return Stream.of(Collections.singletonList(coverImageName), lectureFiles, videoNames).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<String> getLectureFileNames(MultipartFile[] multipartFiles, String courseFolder) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            fileNames.add(fileService.moveFile(multipartFile, courseFolder));
        }
        return fileNames;
    }
}
