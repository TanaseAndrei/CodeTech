package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.controller.model.CourseLectureDto;
import com.ucv.codetech.controller.model.DisplayCourseDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.CourseLecture;
import com.ucv.codetech.repository.CategoryRepositoryGateway;
import com.ucv.codetech.repository.CourseLectureRepositoryGateway;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import com.ucv.codetech.service.converter.CourseConverter;
import com.ucv.codetech.service.converter.CourseLectureConverter;
import com.ucv.codetech.service.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CategoryRepositoryGateway categoryRepositoryGateway;
    private final CourseRepositoryGateway courseRepositoryGateway;
    private final CourseLectureRepositoryGateway courseLectureRepositoryGateway;
    private final CourseConverter courseConverter;
    private final CourseLectureConverter courseLectureConverter;
    private final FileService fileService;

    @Transactional
    public Long createCourse(CourseDto courseDto) {
        try {
            Category category = categoryRepositoryGateway.findById(courseDto.getCategoryId())
                    .orElseThrow(() -> new AppException("The selected category doesn't exist!", HttpStatus.NOT_FOUND));
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
                    .orElseThrow(() -> new AppException("The selected course doesn't exist!", HttpStatus.NOT_FOUND));
            String coverPath = fileService.moveCoverFile(multipartFile, course.getName(), course.getFolder());
            course.setCoverImagePath(coverPath);
            courseRepositoryGateway.save(course);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public void enableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
        course.setAvailable(true);
        courseRepositoryGateway.save(course);
    }

    public void disableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
        course.setAvailable(false);
        courseRepositoryGateway.save(course);
    }

    //TODO here the returned Course represents a full course
    public Course getById(Long id) {
        return courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
    }

    public List<DisplayCourseDto> getAll() {
        List<Course> allCourses = courseRepositoryGateway.findAll();
        return allCourses.stream().map(courseConverter::courseToDisplayCourseDto).collect(Collectors.toList());
    }

    public void deleteCourse(Long id) {
        try {
            Course course = courseRepositoryGateway.findById(id)
                    .orElseThrow(() -> new AppException("The selected category doesn't exist!", HttpStatus.NOT_FOUND));
            fileService.deleteCover(course.getCoverImagePath());
            courseRepositoryGateway.deleteById(id);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void createCourseLecture(Long courseId, CourseLectureDto courseLectureDto) {
        try {
            Course course = courseRepositoryGateway.findById(courseId)
                    .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
            String videoName = fileService.moveVideoLecture(courseLectureDto.getLectureVideo(), courseLectureDto.getName(), course.getFolder());
            CourseLecture courseLecture = courseLectureConverter.courseLectureDtoToCourseLecture(courseLectureDto);
            courseLecture.setLectureVideoPath(videoName);
            courseLecture.setCourse(course);
            course.getCourseLectures().add(courseLecture);
            courseRepositoryGateway.save(course);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void addCourseLectureFiles(Long courseId, Long courseLectureId, MultipartFile[] multipartFiles) {
        try {
            CourseLecture courseLecture = courseLectureRepositoryGateway.findByCourseLectureIdAndCourseId(courseLectureId, courseId)
                    .orElseThrow(() -> new AppException("The selected course id does not exist!", HttpStatus.NOT_FOUND));
            String courseFolder = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist!", HttpStatus.BAD_REQUEST));
            courseLecture.setLectureFilePaths(getLectureFileNames(multipartFiles, courseLecture.getName(), courseFolder));
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private List<String> getLectureFileNames(MultipartFile[] multipartFiles, String lectureName, String courseFolder) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            fileNames.add(fileService.moveCourseLectureFile(multipartFile, lectureName, courseFolder));
        }
        return fileNames;
    }
}
