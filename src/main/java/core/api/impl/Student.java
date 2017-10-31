package core.api.impl;

import core.api.IStudent;

/**
 * Created by Vincent on 21/2/2017.
 */
public class Student implements IStudent {

    @Override
    public void registerForClass(String studentName, String className, int year) {
        Enrollee enrollee = DataManager.findStudent(studentName);
        Course course = DataManager.findCourse(className, year);
        if (course != null && enrollee != null) {
            enrollee.addCourse(course);
            course.addStudent(enrollee);
        }
    }

    @Override
    public void dropClass(String studentName, String className, int year) {
        Enrollee enrollee = DataManager.findStudent(studentName);
        Course course = DataManager.findCourse(className, year);
        if (course != null && enrollee != null) {
            enrollee.addCourse(course);
            course.removeStudent(enrollee);
        }
    }

    @Override
    public void submitHomework(String studentName, String homeworkName, String answerString, String className, int year) {
        Enrollee enrollee = DataManager.findStudent(studentName);
        Course course = DataManager.findCourse(className, year);
        if (course != null && enrollee != null) {
            Homework homework = course.getHomework(homeworkName);
            if (homework != null) {
                homework.submit(enrollee, answerString);
            }
        }
    }

    @Override
    public boolean isRegisteredFor(String studentName, String className, int year) {
        Enrollee enrollee = DataManager.findStudent(studentName);
        Course course = DataManager.findCourse(className, year);
        if (course != null && enrollee != null) {
            return course.getEnrollees().contains(enrollee);
        }
        return false;
    }

    @Override
    public boolean hasSubmitted(String studentName, String homeworkName, String className, int year) {
        Enrollee enrollee = DataManager.findStudent(studentName);
        Course course = DataManager.findCourse(className, year);
        if (course != null && enrollee != null) {
            Homework homework = course.getHomework(homeworkName);
            if (homework != null) {
                return homework.getSubmission(enrollee) != null;
            }
        }
        return false;
    }
}
