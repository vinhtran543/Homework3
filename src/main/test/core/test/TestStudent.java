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

public class TestStudent
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
	
	/////////////// UNIT TEST for registerForClass() //////////////////////////////
	//Test if it student is able to register for a class before max capacity if reached
	@Test
	public void testRegisterForClassSuccess1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
        this.student.registerForClass("Johnson Chu", "ECS122B", 2017);
        this.student.registerForClass("Brian Tseng", "ECS122B", 2017);
		assertTrue(this.student.isRegisteredFor("Brian Tseng", "ECS122B", 2017));
	}
	
	//Test for empty class name when registering for class
	@Test
	public void testRegisterForClassSuccess2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("Vinh Tran", "", 2017);
		assertFalse(this.student.isRegisteredFor("Vinh Tran", "", 2017));
	}
	
	//Test if student is able to register for a class that has already reached max capacity
	@Test
	public void testRegisterForClassFail1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
        this.student.registerForClass("Johnson Chu", "ECS122B", 2017);
        this.student.registerForClass("Brian Tseng", "ECS122B", 2017);
        this.student.registerForClass("Bob Smith", "ECS122B", 2017);
		assertFalse(this.student.isRegisteredFor("Bob Smith", "ECS122B", 2017));
	}
	
	//Test for empty student name when registering for class
	@Test
	public void testRegisterForClassFail2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("", "ECS122B", 2017);
		assertFalse(this.student.isRegisteredFor("", "ECS122B", 2017));
	}
	
	//Test for registering for past class (invalid year in the past)
	@Test
	public void testRegisterForClassFail3() 
	{
		this.admin.createClass("ECS122B", 1980, "Rob Gysel", 10);
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("Vinh Tran", "ECS122B", 1980);
		assertFalse(this.student.isRegisteredFor("Vinh Tran", "ECS122B", 1980));
	}
	/////////////// END UNIT TEST for registerForClass() //////////////////////////////
	
	/////////////// UNIT TEST for dropClass() //////////////////////////////
	//Test for empty student name when dropping class
	@Test
	public void testDropClassSuccess1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
        this.student.dropClass("", "ECS122B", 2017);
		assertFalse(this.student.isRegisteredFor("", "ECS122B", 2017));
	}
	
	//Test if student is able to drop a class that he isn't registered for
	@Test
	public void testDropClassSuccess2() 
	{
		this.admin.createClass("ECS122B", 1980, "Rob Gysel", 3);
        this.student.dropClass("Vinh Tran", "ECS122B", 2017);
		assertFalse(this.student.isRegisteredFor("Vinh Tran", "ECS122B", 2017));
	}
	
	//Test if student is able to drop a class even though he is not registered
	//system drops student regardless if he is registered or not which is fine
	@Test
	public void testDropClassSuccess3() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.dropClass("Vinh Tran", "ECS122B", 2017);
		assertFalse(this.student.isRegisteredFor("Vinh Tran", "ECS122B", 2017));
	}
	
	//Test if student is able to drop a class that he took previously in the past (class has ended)
	@Test
	public void testDropClassFail1() 
	{
		int currentYear = 2017;
		this.admin.createClass("ECS122B", currentYear, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", currentYear);
		currentYear = 2020;
		this.admin.createClass("New Class", 2020, "Sean Davis", 100);
        this.student.dropClass("Vinh Tran", "ECS122B", 2017);
		assertTrue(this.student.isRegisteredFor("Vinh Tran", "ECS122B", 2017));
	}
	/////////////// END UNIT TEST for dropClass() //////////////////////////////
	
	/////////////// UNIT TEST for submitHomework() //////////////////////////////
	//Test if submit homework correctly
	@Test
	public void testSubmitHomeworkSuccess1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1 + 1 = 2", "ECS122B", 2017);
		assertTrue(this.student.hasSubmitted("Vinh Tran", "HW1", "ECS122B", 2017));
	}
	
	//Test if submit homework with empty string for class name
	@Test
	public void testSubmitHomeworkSuccess2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1 + 1 = 2", "", 2017);
		assertFalse(this.student.hasSubmitted("Vinh Tran", "HW1", "", 2017));
	}
	
	//Test if student can submit homework when homework does not exist
	@Test
	public void testSubmitHomeworkSuccess3() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.student.submitHomework("Vinh Tran", "HW1", "1 + 1 = 2", "ECS122B", 2017);
		assertFalse(this.student.hasSubmitted("Vinh Tran", "HW1", "ECS122B", 2017));
	}
	
	//Test if submit homework with empty string for homework name
	@Test
	public void testSubmitHomeworkSuccess4() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "", "1 + 1 = 2", "ECS122B", 2017);
		assertFalse(this.student.hasSubmitted("Vinh Tran", "", "ECS122B", 2017));
	}
	
	//Test if submit homework with empty string for student name
	@Test
	public void testSubmitHomeworkFail1() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("", "HW1", "1 + 1 = 2", "ECS122B", 2017);
		assertFalse(this.student.hasSubmitted("", "HW1", "ECS122B", 2017));
	}
	
	//Test if student can submit homework for a class even though he is not registered for it
	@Test
	public void testSubmitHomeworkFail2() 
	{
		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2017, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1 + 1 = 2", "ECS122B", 2017);
		assertFalse(this.student.hasSubmitted("Vinh Tran", "HW1", "ECS122B", 2017));
	}
	
	//Test if student can submit homework for a class for the wrong year (non-current year)
	//bug if student is able to submit homework for future class
	@Test
	public void testSubmitHomeworkFail3() 
	{
		int currentYear = 2017;
		this.admin.createClass("Current Class", currentYear, "Sean Davis", 50);
		this.admin.createClass("ECS122B", 2020, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 2020);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 2020, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1 + 1 = 2", "ECS122B", 2020);
		assertFalse(this.student.hasSubmitted("Vinh Tran", "HW1", "ECS122B", 2020));
	}
	
	//Test if student can submit homework for a class for the wrong year (non-current year)
	//bug if student is able to submit homework for past class
	@Test
	public void testSubmitHomeworkFail4() 
	{
		int currentYear = 2017;
		this.admin.createClass("Current Class", currentYear, "Sean Davis", 50);
		this.admin.createClass("ECS122B", 1980, "Rob Gysel", 3);
		this.student.registerForClass("Vinh Tran", "ECS122B", 1980);
		this.instructor.addHomework("Rob Gysel", "ECS122B", 1980, "HW1");
		this.student.submitHomework("Vinh Tran", "HW1", "1 + 1 = 2", "ECS122B", 1980);
		assertFalse(this.student.hasSubmitted("Vinh Tran", "HW1", "ECS122B", 1980));
	}
	/////////////// END UNIT TEST for submitHomework() //////////////////////////////
}
