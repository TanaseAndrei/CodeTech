package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.*;
import com.ucv.codetech.controller.model.output.PreviewCourseDto;
import com.ucv.codetech.controller.model.output.PreviewFullCourseDto;
import com.ucv.codetech.facade.converter.CommentConverter;
import com.ucv.codetech.facade.converter.CourseConverter;
import com.ucv.codetech.facade.converter.LectureConverter;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Facade
@AllArgsConstructor
@Slf4j
public class CourseFacade {

    private final CourseService courseService;
    private final LectureService lectureService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final CourseConverter courseConverter;
    private final LectureConverter lectureConverter;
    private final QuizConverter quizConverter;
    private final CommentConverter commentConverter;
    private final UserService userService;
    private final QuizService quizService;
    private final MediaRestClientService mediaRestClientService;

    @Transactional
    public Long createCourse(CourseDto courseDto, String username) {
        log.info("Instructor {} is creating a new course", username);
        if (courseService.courseExistsByName(courseDto.getName())) {
            log.warn("Course with name {} already exists", courseDto.getName());
            throw new AppException("The course already exists with this name!",
                    HttpStatus.BAD_REQUEST);
        }
        Instructor instructor = userService.getInstructor(username);
        Category category = categoryService.findById(courseDto.getCategoryId());
        Course course = courseConverter.dtoToEntity(courseDto);
        instructor.addCourse(course);
        course.setInstructor(instructor);
        course.setCategory(category);
        String folder = mediaRestClientService.createFolder(courseDto.getName());
        course.setFolderName(folder);
        userService.saveInstructor(instructor);
        log.info("Course with name {} was created", courseDto.getName());
        return courseService.saveOrUpdate(course).getId();
    }

    @Transactional
    public void addCourseCover(MultipartFile cover, Long id) {
        log.info("Adding a cover for course with id {}", id);
        if (courseService.containsCourseCover(id)) {
            log.warn("The course with id {} already has a cover", id);
            throw new AppException("The course with id " + id + " already has a cover", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(id);
        String filename = mediaRestClientService.addFileToFolder(course.getFolderName(), cover);
        course.setCoverImageName(filename);
        courseService.saveOrUpdate(course);
    }

    @Transactional
    public void enableCourse(Long id) {
        courseService.enableCourse(id);
        log.info("Enabled course with id {}", id);
    }

    @Transactional
    public void disableCourse(Long id) {
        courseService.disableCourse(id);
        log.info("Disabled course with id {}", id);
    }

    public PreviewFullCourseDto getById(Long id) {
        log.info("Getting full preview for course with id {}", id);
        Course course = courseService.findById(id);
        return courseConverter.entityToFullDisplayCourseDto(course);
    }

    public List<PreviewCourseDto> getAll(String username) {
        log.info("Getting the preview courses for the student {}", username);
        Student student = userService.getStudent(username);
        List<Course> courses = courseService.getAll();
        return courseConverter.courseListToDisplayCourseDtoList(courses, student);
    }

    @Transactional
    public Long createLecture(Long courseId, LectureDto lectureDto) {
        log.info("Creating new lecture for course with id {}", courseId);
        if (lectureService.lectureExistsInCourse(lectureDto.getName(), courseId)) {
            log.warn("A lecture with name {} already exists in the current course", lectureDto.getName());
            throw new AppException("A lecture with the name " + lectureDto.getName() + " already exists in the course",
                    HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(courseId);
        String videoName = mediaRestClientService.addFileToFolder(course.getFolderName(), lectureDto.getLectureVideo());
        Lecture lecture = lectureConverter.dtoToEntity(lectureDto);
        lecture.setLectureVideoName(videoName);
        course.addLecture(lecture);
        courseService.saveOrUpdate(course);
        log.info("Created new lecture for course with id {}", courseId);
        return lecture.getId();
    }

    @Transactional
    public void deleteCourse(Long id) {
        String courseFolderName = courseService.getCourseFolderName(id);
        courseService.deleteById(id);
        mediaRestClientService.deleteFolder(courseFolderName);
        log.info("Deleted course with id {}", id);
    }

    @Transactional
    public Long addComment(Long id, CommentDto commentDto, String username) {
        Student student = userService.getStudent(username);
        Course course = courseService.findById(id);
        Comment comment = commentConverter.dtoToEntity(commentDto);
        comment.setComment(student, course);
        courseService.saveOrUpdate(course);
        log.info("Student {} added a comment to the course {}", username, id);
        return commentService.saveOrUpdate(comment);
    }

    @Transactional
    public Long createQuiz(Long id, QuizDto quizDto, String username) {
        log.info("Creating a new quiz for course {}", id);
        Instructor instructor = userService.getInstructor(username);
        if (courseService.hasQuiz(id)) {
            log.warn("A quiz already exists for course with id {}", id);
            throw new AppException("The course already has an associated quiz", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(id);
        Quiz quiz = quizConverter.quizDtoToEntity(quizDto);
        course.setQuiz(quiz);
        instructor.addQuiz(quiz);
        quizService.saveOrUpdate(quiz);
        log.info("Created the quiz for course with id {}", id);
        return quiz.getId();
    }

    @Transactional
    public void enrollToCourse(Long id, String username) {
        log.info("Student {} is enrolling to course with id {}", username, id);
        Course course = courseService.findById(id);
        if (!course.isAvailable()) {
            log.warn("The course {} is not available", id);
            throw new AppException("You can't enroll into this course because is not available", HttpStatus.BAD_REQUEST);
        }
        Student student = userService.getStudent(username);
        if (course.containsStudent(student)) {
            log.warn("Student {} is already enrolled in course {}", username, id);
            throw new AppException("Student " + username + " is already enrolled in the course " + course.getName(),
                    HttpStatus.BAD_REQUEST);
        }
        course.enrollStudent(student, courseToEnrolledCourse(course, student));
        userService.saveStudent(student);
        courseService.saveOrUpdate(course);
        log.info("Student {} enrolled in the course {}", username, id);
    }

    @Transactional
    public void deleteCourseCover(Long id) {
        String courseFolderName = courseService.getCourseFolderName(id);
        Course course = courseService.findById(id);
        String coverImage = course.getCoverImageName();
        mediaRestClientService.deleteFile(courseFolderName, coverImage);
        course.setCoverImageName(null);
        courseService.saveOrUpdate(course);
        log.info("Delete cover of course {}", id);
    }

    @Transactional
    public void updateCourse(Long courseId, UpdateCourseDto updateCourseDto) {
        log.info("Updating course {}", courseId);
        Course course = courseService.findById(courseId);
        course.setAvailable(updateCourseDto.isAvailable());
        course.setDescription(updateCourseDto.getDescription());
        course.setDifficulty(Difficulty.getByName(updateCourseDto.getDifficultyDto().name()));
        if (!course.getName().equals(updateCourseDto.getName())) {
            log.info("Updating the folder of the course {} in the file system", courseId);
            String oldFolderName = course.getFolderName();
            course.setName(updateCourseDto.getName());
            course.setFolderName(updateCourseDto.getName());
            mediaRestClientService.updateCourseFolder(oldFolderName, updateCourseDto.getName());
        }
        courseService.saveOrUpdate(course);
    }

    private EnrolledCourse courseToEnrolledCourse(Course course, Student student) {
        EnrolledCourse enrolledCourse = new EnrolledCourse();
        enrolledCourse.setCourse(course);
        enrolledCourse.setStudent(student);
        List<LectureWrapper> lectureWrappers = lecturesToLectureWrappers(course.getLectures());
        lectureWrappers.forEach(enrolledCourse::addLectureWrapper);
        enrolledCourse.setLectureWrappers(lectureWrappers);
        return enrolledCourse;
    }

    private List<LectureWrapper> lecturesToLectureWrappers(List<Lecture> lectures) {
        return lectures
                .stream()
                .map(this::lectureToLectureWrapper)
                .collect(Collectors.toList());
    }

    private LectureWrapper lectureToLectureWrapper(Lecture lecture) {
        LectureWrapper lectureWrapper = new LectureWrapper();
        lectureWrapper.setLecture(lecture);
        return lectureWrapper;
    }
}
