package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CourseTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllCourses() {

        Course course1 = new Course(1L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 60, Collections.emptySet());
        Course course2 = new Course(2L, 2, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 150.0f, 90, Collections.emptySet());

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        Mockito.when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(course1));
        assertTrue(result.contains(course2));
    }

    @Test
    public void testAddCourse() {

        Course course = new Course(1L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 60, Collections.emptySet());

        Mockito.when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.addCourse(course);

        assertNotNull(result);
        assertEquals(course, result);
    }

    @Test
    public void testUpdateCourse() {

        Course course = new Course(1L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 60, Collections.emptySet());

        Mockito.when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.updateCourse(course);

        assertNotNull(result);
        assertEquals(course, result);
    }

    @Test
    public void testRetrieveCourse() {

        Long courseNumber = 1L;
        Course course = new Course(1L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 60, Collections.emptySet());

        Mockito.when(courseRepository.findById(courseNumber)).thenReturn(Optional.of(course));

        Course result = courseServices.retrieveCourse(courseNumber);

        assertNotNull(result);
        assertEquals(course, result);
    }





}
