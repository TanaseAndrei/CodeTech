package com.ucv.codetech.facade;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.service.CategoryService;
import com.ucv.codetech.service.CourseService;
import com.ucv.codetech.facade.converter.CourseConverter;
import com.ucv.codetech.facade.converter.LectureConverter;
import com.ucv.codetech.service.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class CourseFacade {

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final CourseConverter courseConverter;
    private final LectureConverter lectureConverter;

    @Transactional
    public Long createCourse(CourseDto courseDto) {
        try {
            if (courseService.courseExistsByName(courseDto.getName())) {
                throw new AppException("The course already exists with this name!",
                        HttpStatus.BAD_REQUEST);
            }
            Category category = categoryService.findById(courseDto.getCategoryId());
            String folderName = fileService.createCourseFolder(courseDto.getName());
            Course course = courseConverter.courseDtoToCourse(courseDto);
            course.setCategory(category);
            course.setFolderName(folderName);
            return courseService.createOrUpdate(course).getId();
        } catch (IOException ioException) {
            //TODO throw appexception
            return null;
        }
    }

    @Transactional
    public void addCourseCover(MultipartFile cover, Long id) {
        try {
            Course course = courseService.getById(id);
            String filename = fileService.moveFile(cover, course.getFolderName());
            course.setCoverImageName(filename);
            courseService.createOrUpdate(course);
        } catch (IOException ioException) {
            //TODO throw appexception
        }
    }

    @Transactional
    public void enableCourse(Long id) {
        courseService.enableCourse(id);
    }

    @Transactional
    public void disableCourse(Long id) {
        courseService.disableCourse(id);
    }

    public FullDisplayCourseDto getById(Long id) {
        Course course = courseService.getById(id);
        return courseConverter.courseToFullDisplayCourseDto(course);
    }

    public List<DisplayCourseDto> getAll() {
        List<Course> courses = courseService.getAll();
        return courseConverter.courseListToDisplayCourseDtoList(courses);
    }

    @Transactional
    public void createLecture(Long courseId, LectureDto lectureDto) {
        try {
            Course course = courseService.getById(courseId);
            String lectureVideoName = fileService.moveFile(lectureDto.getLectureVideo(), course.getFolderName());
            Lecture lecture = lectureConverter.lectureDtoToLecture(lectureDto);
            lecture.setLectureVideoName(lectureVideoName);
            lecture.setCourse(course);
            course.addLecture(lecture);
            courseService.createOrUpdate(course);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteCourse(Long id) {
        try {
            String courseFolderName = courseService.getCourseFolderName(id);
            List<String> filesToDelete = courseService.deleteCourse(id);
            fileService.deleteFiles(filesToDelete, courseFolderName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }



}
