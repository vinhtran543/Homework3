package core.api.impl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vincent on 22/2/2017.
 */
class Enrollee {
    private final String name;
    private final Set<Course> courses;

    protected Enrollee(String name) {
        this.name = name;
        this.courses = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    protected void addCourse(Course course) {
        this.courses.add(course);
    }

    protected void dropCourse(Course course) {
        this.courses.remove(course);
    }

    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Enrollee)) return false;
        Enrollee asEnrollee = (Enrollee) other;
        return asEnrollee.getName().equals(this.name);
    }
}
