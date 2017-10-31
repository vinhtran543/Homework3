package core.api.impl;

import core.api.IInstructor;

/**
 * Created by Vincent on 21/2/2017.
 */
public class Instructor implements IInstructor {

	@Override
	public void addHomework(String instructorName, String className, int year, String homeworkName) {
		Course course = DataManager.findCourse(className, year);
		if (course == null)
			return;
		if (!DataManager.courseInstructors.get(course).equals(instructorName))
			return;
		course.addHomework(new Homework(homeworkName));
	}

	@Override
	public void assignGrade(String instructorName, String className, int year, String homeworkName, String studentName,
			int grade) {
		if (grade < 0)
			return;
		Course course = DataManager.findCourse(className, year);
		if (course == null)
			return;
		Enrollee enrollee = DataManager.findStudent(studentName);
		if (enrollee == null)
			return;
		Homework homework = course.getHomework(homeworkName);
		if (homework == null)
			return;
		String submission = homework.getSubmission(enrollee);
		if (submission == null)
			return;
		homework.gradeStudent(enrollee, grade);
	}

	@Override
	public boolean homeworkExists(String className, int year, String homeworkName) {
		Course course = DataManager.findCourse(className, year);
		if (course == null)
			return false;
		return course.getHomework(homeworkName) != null;
	}

	@Override
	public Integer getGrade(String className, int year, String homeworkName, String studentName) {
		Course course = DataManager.findCourse(className, year);
		if (course == null)
			return null;
		Enrollee student = DataManager.findStudent(studentName);
		if (student == null)
			return null;
		Homework hw = course.getHomework(homeworkName);
		if (hw == null)
			return null;
		return hw.getGrade(student);
	}
}
