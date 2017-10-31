package core.api.impl;

import core.api.IInstructor;

/**
 * Created by Vincent on 21/2/2017.
 */
public class Instructor implements IInstructor {

    @Override
    public void addHomework(String instructorName, String className, int year, String homeworkName) {
        Course course = DataManager.findCourse(className, year);
        if (course != null) {
            course.addHomework(new Homework(homeworkName));
        }
    }

    @Override
    public void assignGrade(String instructorName, String className, int year, String homeworkName, String studentName, int grade) {
        Enrollee enrollee = DataManager.findStudent(studentName);
        Course course = DataManager.findCourse(className, year);
        if (enrollee != null && course != null) {
            Homework homework = course.getHomework(homeworkName);
            if (homework != null) {
                homework.gradeStudent(enrollee, grade);
            }
        }
    }

    @Override
    public boolean homeworkExists(String className, int year, String homeworkName) {
        Course course = DataManager.findCourse(className, year);
        if (course == null) return false;
        return course.getHomework(homeworkName) != null;
    }

    @Override
    public Integer getGrade(String className, int year, String homeworkName, String studentName) {
        Course course = DataManager.findCourse(className, year);
        Enrollee student = DataManager.findStudent(studentName);
        if (course == null || student == null) return null;
        Homework hw = course.getHomework(homeworkName);
        if (hw == null) return null;
        Integer grade = hw.getGrade(student);
        return grade;
    }
}
