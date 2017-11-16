package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.impl.Admin;

import core.api.IInstructor;
import core.api.impl.Instructor;

import core.api.IStudent;
import core.api.impl.Student;

public class TestInstructor
{
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	@Before
	public void setup()
	{
		this.admin = new Admin();
		this.instructor = new Instructor();
		this.student = new Student();
	}
	
	/////////////// UNIT TEST for addHomework() //////////////////////////////
	//Test if it add homework correctly for existing class
	@Test
	public void testAddHomeworkSuccess1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		assertTrue(this.instructor.homeworkExists("ECS122B", 2017, "HW1"));
	}
	
	//Test for wrong class name
	@Test
	public void testAddHomeworkSuccess2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("Rob Gysel", "English 101", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS122B", 2017, "HW1"));
	}
	
	//Test for empty class name
	@Test
	public void testAddHomeworkSuccess3() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("Rob Gysel", "", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("", 2017, "HW1"));
	}
	
	//Test for wrong year
	@Test
	public void testAddHomeworkSuccess4() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 1980, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS122B", 2017, "HW1"));
	}
	
	//Test for homework is made for a class that does not exist, the homework should not pass
	@Test
	public void testAddHomeworkSuccess5() 
	{
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "");
		assertFalse(this.instructor.homeworkExists("ECS122B", 2017, ""));
	}
	
	//Test for incorrect instructor/person add homework to class
	@Test
	public void testAddHomeworkFail1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("Devanbu", "ECS122B", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS122B", 2017, "HW1"));
	}
	
	//Test empty string for instructor name
	@Test
	public void testAddHomeworkFail2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("", "ECS122B", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS122B", 2017, "HW1"));
	}
	
	//Test for empty string homework name
	@Test
	public void testAddHomeworkFail3() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "");
		assertFalse(this.instructor.homeworkExists("ECS122B", 2017, ""));
	}
	/////////////// END UNIT TEST for addHomework() //////////////////////////////
		
	///////////// CORNER CASE ///////////////////////////
	//Test if correctly assign 100% to student grade
	@Test
	public void testAssignGradeSuccess1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", 100);
		assertTrue((this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran") >= 0));
	}
	
	//Test if correctly assign student 0% to student grade
	@Test
	public void testAssignGradeSuccess2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", 0);
		assertTrue((this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran") >= 0));
	}
	///////////// END CORNER CASE ///////////////////////////
	
	/////////////// UNIT TEST for assignGrade() //////////////////////////////
	//Test if instructor assigned grade to non-existent homework, returns null if HW grade doesn't exist
	@Test
	public void testAssignGradeSuccess3() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW9", "Vinh Tran", 90);
		assertNull(this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran"));
	}
	
	//Test if extra credit allows student grade to be over 100%. It should.
	@Test
	public void testAssignGradeSuccess4() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", 110);
		assertTrue((this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran") > 100));
	}
	
	//Test if instructor creates 2 homework with same name then assigns grade to them
	@Test
	public void testAssignGradeSuccess5() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.student.registerForClass("Fred Smith", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", 90);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Fred Smith", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Fred Smith", 90);
		assertNotNull(this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran"));
		assertNotNull(this.instructor.getGrade("ECS122B", 2017, "HW1", "Fred Smith"));
	}
	
	//Test if assigned negative grade
	@Test
	public void testAssignGradeFail1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", -1);
		assertFalse(this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran") < 0);
	}
	
	//Test if instructor assigns grade to homework for a wrong class that he is not teaching
	//Should not return a grade, fails if it does
	@Test
	public void testAssignGradeFail2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.admin.createClass("Calcalus 101", 2017, "Prem Devanbu", 50);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.instructor.addHomework("Prem Devanbu", "Calcalus 101", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1+1 = 2", "ECS122B", 2017);
		this.instructor.assignGrade("Rob Gysel", "Calcalus 101", 2017, "HW1", "Vinh Tran", 90);
		assertNull(this.instructor.getGrade("Calcalus 101", 2017, "HW1", "Vinh Tran"));
	}
	
	//Test if instructor assigns grade even though student did not submit anything
	//Student should not have a grade because he did not submit the homework
	@Test
	public void testAssignGradeFail3() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", 90);
		assertNull(this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran"));
	}
	
	//teacher assigning grade to student when he is not registered for the class
	@Test
	public void testAssignGradeFail4() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
		this.student.registerForClass("Vinh Tran", "Statistics 101", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.instructor.assignGrade("Rob Gysel", "ECS122B", 2017, "HW1", "Vinh Tran", 90);
		assertNull(this.instructor.getGrade("ECS122B", 2017, "HW1", "Vinh Tran"));
	}
	/////////////// END UNIT TEST for assignGrade() //////////////////////////////
}
