package core.api.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 22/2/2017.
 */
class Homework {
    private final String name;
    private final Map<Enrollee, String> studentSubmissions;
    private final Map<Enrollee, Integer> studentGrades;

    protected Homework(String name) {
        this.name = name;
        this.studentSubmissions = new HashMap<>();
        this.studentGrades = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    protected void submit(Enrollee enrollee, String solution) {
        this.studentSubmissions.put(enrollee, solution);
    }

    protected void gradeStudent(Enrollee enrollee, int grade) {
        this.studentGrades.put(enrollee, grade);
    }

    protected String getSubmission(Enrollee enrollee) {
        return this.studentSubmissions.get(enrollee);
    }

    protected Integer getGrade(Enrollee enrollee) {
        return this.studentGrades.get(enrollee);
    }

    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Homework)) return false;
        Homework asHW = (Homework) other;
        return asHW.name.equals(this.name);
    }
}
