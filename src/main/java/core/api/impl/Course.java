package core.api.impl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vincent on 22/2/2017.
 */
class Course {
    private final String name;
    private final int year;
    private int capacity;

    private Set<Homework> homeworks;
    private Set<Enrollee> enrollees;

    protected Course(String name, int year) {
        this(name, year, 0);
    }


    protected Course(String name, int year, int capacity) {
        this.name = name;
        this.year = year;
        this.capacity = capacity;

        this.homeworks = new HashSet<>();
        this.enrollees = new HashSet<>();
    }

    public int getCapacity() {
        return this.capacity;
    }

    protected void addStudent(Enrollee enrollee) {
        this.enrollees.add(enrollee);
    }

    protected void removeStudent(Enrollee enrollee) {
        this.enrollees.remove(enrollee);
    }

    protected Set<Enrollee> getEnrollees() {
        return this.enrollees;
    }

    protected void addHomework(Homework homework) {
        this.homeworks.add(homework);
    }

    protected Homework getHomework(String name) {
        for (Homework hw : this.homeworks) {
            if (hw.getName().equals(name)) return hw;
        }
        return null;
    }

    protected void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int hashCode() {
        return this.name.hashCode() * 31 + Integer.hashCode(this.year);
    }

    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Course)) return false;
        Course asCourse = (Course) other;
        return asCourse.name.equals(this.name) && asCourse.year == this.year;
    }
}
