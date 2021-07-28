package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.CourseLectureDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.repository.CategoryRepositoryGateway;
import com.ucv.codetech.repository.CourseLectureRepositoryGateway;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import com.ucv.codetech.service.converter.CourseConverter;
import com.ucv.codetech.service.converter.CourseLectureConverter;
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

    private final CategoryRepositoryGateway categoryRepositoryGateway;
    private final CourseRepositoryGateway courseRepositoryGateway;
    private final CourseLectureRepositoryGateway courseLectureRepositoryGateway;
    private final CourseConverter courseConverter;
    private final CourseLectureConverter courseLectureConverter;
    private final FileService fileService;
    private final ZipService zipService;

    @Transactional
    public Long createCourse(CourseDto courseDto) {
        try {
            if(courseRepositoryGateway.existsByName(courseDto.getName())) {
                throw new AppException("The course already exists with this name!", HttpStatus.BAD_REQUEST);
            }
            Category category = categoryRepositoryGateway.findById(courseDto.getCategoryId())
                    .orElseThrow(() -> new AppException("The selected category does not exist!", HttpStatus.NOT_FOUND));
            String courseFolder = fileService.createCourseFolder(courseDto.getName());
            Course course = courseConverter.courseDtoToCourse(courseDto);
            course.setCategory(category);
            course.setFolder(courseFolder);
            return courseRepositoryGateway.save(course).getId();
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void addCourseCover(MultipartFile multipartFile, Long id) {
        try {
            Course course = courseRepositoryGateway.findById(id)
                    .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
            course.setCoverImageName(fileService.moveFile(multipartFile, course.getFolder()));
            courseRepositoryGateway.save(course);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public void enableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        course.setAvailable(true);
        courseRepositoryGateway.save(course);
    }

    public void disableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        course.setAvailable(false);
        courseRepositoryGateway.save(course);
    }

    //TODO here the returned Course represents a full course
    public FullDisplayCourseDto getById(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        return courseConverter.courseToFullDisplayCourseDto(course);
    }

    //TODO paginatation and filtering
    public List<DisplayCourseDto> getAll() {
        List<Course> courses = courseRepositoryGateway.findAll();
        return courseConverter.courseListToDisplayCourseDtoList(courses);
    }

    @Transactional
    public void deleteCourse(Long id) {
        try {
            Course course = courseRepositoryGateway.findById(id)
                    .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
            List<String> videoNames = courseLectureRepositoryGateway.getCourseLectureVideos(id);
            List<Lecture> lectures = courseLectureRepositoryGateway.getCourseLecturesByCourseId(course.getId());
            List<String> lectureFiles = collectAllLectureFiles(lectures);
            List<String> allFileNames = collectAllFilesToDelete(course.getCoverImageName(), videoNames, lectureFiles);
            fileService.deleteCourseFilesData(allFileNames, course.getFolder());
            courseRepositoryGateway.deleteById(id);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void createCourseLecture(Long courseId, CourseLectureDto courseLectureDto) {
        try {
            Course course = courseRepositoryGateway.findById(courseId)
                    .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
            String videoName = fileService.moveFile(courseLectureDto.getLectureVideo(), course.getFolder());
            Lecture lecture = courseLectureConverter.courseLectureDtoToCourseLecture(courseLectureDto);
            lecture.setLectureVideoName(videoName);
            lecture.setCourse(course);
            course.getLectures().add(lecture);
            courseRepositoryGateway.save(course);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void addCourseLectureFiles(Long courseId, Long courseLectureId, MultipartFile[] multipartFiles) {
        try {
            Lecture lecture = courseLectureRepositoryGateway.findByCourseLectureIdAndCourseId(courseLectureId, courseId)
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
            Lecture lecture = courseLectureRepositoryGateway.findByCourseLectureIdAndCourseId(courseId, courseLectureId)
                    .orElseThrow(() -> new AppException("The course lecture does not exist", HttpStatus.NOT_FOUND));
            if(!lecture.getLectureFileNames().contains(fileName)) {
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
            Lecture lecture = courseLectureRepositoryGateway.findByCourseLectureIdAndCourseId(courseId, courseLectureId)
                    .orElseThrow(() -> new AppException("The course lecture does not exist", HttpStatus.NOT_FOUND));
            String folderName = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist", HttpStatus.NOT_FOUND));
            String zipFilePath = zipService.zipFiles(lecture.getLectureFileNames(), folderName);
            return new UrlResource(Paths.get(zipFilePath).toUri());
        } catch (IOException ioException) {
            throw new AppException("The files could not have been zipped", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Resource getMediaAsResource(String courseName, String fileName) {
        try {
            String folderName = courseRepositoryGateway.getCourseFolderName(courseName)
                    .orElseThrow(() -> new AppException("The course folder does not exist", HttpStatus.NOT_FOUND));
            return fileService.getFileAsResource(folderName, fileName);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
