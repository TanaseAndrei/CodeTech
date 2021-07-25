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
import com.ucv.codetech.service.file.ZipService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Paths;
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
                    .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
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

    @Transactional
    public void deleteCourse(Long id) {
        try {
            Course course = courseRepositoryGateway.findById(id)
                    .orElseThrow(() -> new AppException("The selected course doesn't exist!", HttpStatus.NOT_FOUND));
            List<String> courseLectureVideos = courseLectureRepositoryGateway.getCourseLectureVideos(id);
            List<CourseLecture> courseLectures = courseLectureRepositoryGateway.getCourseLecturesByCourseId(course.getId());
            List<String> courseLectureFiles =
                    courseLectures.stream().map(CourseLecture::getLectureFilePaths).flatMap(List::stream).collect(Collectors.toList());
            fileService.deleteCover(course.getCoverImagePath(), course.getFolder());
            fileService.deleteCourseLectureFiles(courseLectureFiles, course.getFolder());
            fileService.deleteCourseLectureVideos(courseLectureVideos, course.getFolder());
            fileService.deleteBaseFolder(course.getFolder());
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
                    .orElseThrow(() -> new AppException("The selected course lecture does not exist!", HttpStatus.NOT_FOUND));
            String courseFolder = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist!", HttpStatus.BAD_REQUEST));
            courseLecture.setLectureFilePaths(getLectureFileNames(multipartFiles, courseLecture.getName(), courseFolder));
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public Resource downloadFile(Long courseId, Long courseLectureId, String fileName) {
        try {
            CourseLecture courseLecture = courseLectureRepositoryGateway.findByCourseLectureIdAndCourseId(courseId, courseLectureId)
                    .orElseThrow(() -> new AppException("The course lecture does not exist", HttpStatus.NOT_FOUND));
            if(!courseLecture.getLectureFilePaths().contains(fileName)) {
                throw new AppException("The file does not exist!", HttpStatus.NOT_FOUND);
            }
            String folderName = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist", HttpStatus.NOT_FOUND));
            return fileService.downloadFile(folderName, fileName);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public Resource zipLectureFiles(Long courseId, Long courseLectureId) {
        try {
            CourseLecture courseLecture = courseLectureRepositoryGateway.findByCourseLectureIdAndCourseId(courseId, courseLectureId)
                    .orElseThrow(() -> new AppException("The course lecture does not exist", HttpStatus.NOT_FOUND));
            String folderName = courseRepositoryGateway.getCourseFolderName(courseId)
                    .orElseThrow(() -> new AppException("The course folder does not exist", HttpStatus.NOT_FOUND));
            String zipFilePath = zipService.zipFiles(courseLecture.getLectureFilePaths(), folderName);
            return new UrlResource(Paths.get(zipFilePath).toUri());
        } catch (IOException ioException) {
            throw new AppException("The files could not have been zipped", HttpStatus.INTERNAL_SERVER_ERROR);
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
