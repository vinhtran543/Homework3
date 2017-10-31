package core.api.impl;

import core.api.IAdmin;

/**
 * Created by Vincent on 21/2/2017.
 */
public class Admin implements IAdmin {

    public Admin() {
        DataManager.reset();
    }

    @Override
    public void createClass(String className, int year, String instructorName, int capacity) {
        if (capacity <= 0) return;
        if (year < 2017) return;
        if (DataManager.findCourse(className, year) != null) return;
        long alreadyTeachingInYear = DataManager.courseInstructors.entrySet().stream()
                .filter(e -> e.getKey().getYear() == year)
                .filter(e -> e.getValue().equals(instructorName))
                .count();
        if (alreadyTeachingInYear >= 2) return;
        Course course = new Course(className, year, capacity);
        DataManager.courseInstructors.put(course, instructorName);
    }

    @Override
    public void changeCapacity(String className, int year, int capacity) {
        Course course = DataManager.findCourse(className, year);
        if (course == null) return;
        if (capacity < course.getEnrollees().size()) return;
        course.setCapacity(capacity);
    }

    @Override
    public boolean classExists(String className, int year) {
        return DataManager.findCourse(className, year) != null;
    }

    @Override
    public String getClassInstructor(String className, int year) {
        Course course = DataManager.findCourse(className, year);
        if (course == null) return null;
        return DataManager.courseInstructors.get(course);
    }

    @Override
    public int getClassCapacity(String className, int year) {
        Course course = DataManager.findCourse(className, year);
        if (course == null) return 0;
        return course.getCapacity();
    }
}
