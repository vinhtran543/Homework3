package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.impl.Admin;

import core.api.IStudent;
import core.api.impl.Student;

public class TestAdmin {
	private IAdmin admin;
	private IStudent student;

    @Before
    public void setup() 
    {
        this.admin = new Admin();
        this.student = new Student();
    }
    
    //////////////// UNIT TEST for createClass() /////////////////////////////////
    //Test if instructor name exists
  	@Test
  	public void testCreateClassSuccess1() 
  	{
  	    this.admin.createClass("Class1", 2017, "Rob Gysel", 200);
  	    assertTrue(this.admin.getClassInstructor("Class1", 2017) == "Rob Gysel");
  	}
    
    //Test if instructor is teaching 2 courses with unique name/year pair. 
  	@Test
  	public void testCreateClassSuccess2() 
  	{
  	    this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
  	    this.admin.createClass("Statistics 101", 2017, "Rob Gysel", 300);
  	    assertTrue(this.admin.classExists("ECS122B", 2017));
  	    assertTrue(this.admin.classExists("Statistics 101", 2017));
  	}
  	
  	//Test if class year isn't in the past (present/future dates okay)
	@Test
  	public void testCreateClassSuccess3() 
  	{
  	    this.admin.createClass("Class1", 2020, "Rob Gysel", 200);
  	    this.admin.createClass("Class1", 2020, "Rob Gysel", 200);
  	    assertTrue(this.admin.classExists("Class1", 2020));
  	}
	
    //Test if class name matches with actual class name
	@Test
	public void testCreateClassSuccess4() 
	{
        this.admin.createClass("ECS122B", 2017, "Rob Gyssel", 200);
        assertTrue(this.admin.classExists("ECS122B", 2017));
    }
	
	//Test if class name doesn't match with actual name
	@Test 
	public void testCreateClassSuccess5() 
	{
	    this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
	    assertFalse(this.admin.classExists("English 101", 2017));
	}
	
	//Test if class year doesn't match with actual year
	@Test
	public void testCreateClassSuccess6() 
	{
	    this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
	    assertFalse(this.admin.classExists("ECS122B", 2020));
	}
	
    //Test if instructor name is not empty, instructor should have a name
  	@Test
  	public void testCreateClassFail1() 
  	{
  	    this.admin.createClass("Class1", 2017, "", 200);
  	    assertTrue(this.admin.getClassInstructor("Class1", 2017) == null);
  	}
    
    //Test if instructor is teaching 2 courses with non-unique name/year pair. 
  	@Test
  	public void testCreateClassFail2() 
  	{
  	    this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
  	    this.admin.createClass("ECS122B", 2017, "Sean Davis", 200);
  	    assertTrue((this.admin.getClassInstructor("ECS122B", 2017) == "Rob Gysel"));
  	}
  	
  	//Test if created class has negative capacity
  	@Test 
  	public void testCreateClassFail3() 
  	{
  	    this.admin.createClass("Class1", 2017, "Rob Gysel", -1);
  	    assertTrue((this.admin.getClassCapacity("Class1", 2017) >= 0));
  	}
  
  	//Test if class year is in the past (present/future dates okay)
	@Test
  	public void testCreateClassFail4() 
  	{
  	    this.admin.createClass("Class1", 1995, "Rob Gysel", 200);
  	    this.admin.createClass("Class1", 1995, "Rob Gysel", 200);
  	    assertFalse(this.admin.classExists("Class1", 1995));
  	}
	
	//Test if class year is in the past (negative year) (present/future dates okay)
	@Test
  	public void testCreateClassFail5() 
  	{
  	    this.admin.createClass("Class1", -1, "Rob Gysel", 200);
  	    this.admin.createClass("Class1", -1, "Rob Gysel", 200);
  	    assertFalse(this.admin.classExists("Class1", -1));
  	}
	
	//Test for empty string class name, class should have a name
	@Test 
	public void testCreateClassFail6() 
	{
	    this.admin.createClass("", 2017, "Rob Gysel", 200);
	    assertFalse(this.admin.classExists("", 2017));
	}
	//////////////// END UNIT TEST for createClass() /////////////////////////////////
	
	//////////////////// UNIT TEST for changeCapacity() //////////////////////
	//Test if class capacity is able to change even if specified to change to same capacity size
	@Test 
	public void testChangeCapacitySuccess1() 
	{
        this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
        this.admin.changeCapacity("ECS122B", 2017, 200);
	    assertEquals(admin.getClassCapacity("ECS122B", 2017), 200);
	}
	
	//Test if class capacity changed (doubled)
	@Test
	public void testChangeCapacitySuccess2() 
	{
        this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
	    this.admin.changeCapacity("ECS122B", 2017, 400);
	    assertEquals(admin.getClassCapacity("ECS122B", 2017), 400);
	}
	
	//Test if class capacity when changed is less than number of enrolled students
	@Test
	public void testChangeCapacityFail1() 
	{
        this.admin.createClass("ECS122B", 2017, "Rob Gysel", 3);
        this.student.registerForClass("Vinh Tran", "ECS122B", 2017);
        this.student.registerForClass("Johnson Chu", "ECS122B", 2017);
        this.student.registerForClass("Brian Tseng", "ECS122B", 2017);
	    this.admin.changeCapacity("ECS122B", 2017, 1);
	    assertFalse((admin.getClassCapacity("ECS122B", 2017) <= 3));
	}
	
	//Test if class capacity was changed to -1 (less than zero)
	@Test
	public void testChangeCapacityFail2() 
	{
        this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
	    this.admin.changeCapacity("ECS122B", 2017, -1);
	    assertTrue((admin.getClassCapacity("ECS122B", 2017) > 0));
	}
	
	//Test if class capacity was changed to 0 (less than zero)
	//Class should not have zero capacity. Defeats the purpose of a instructor teaching # of students
	@Test
	public void testChangeCapacityFail3() 
	{
       this.admin.createClass("ECS122B", 2017, "Rob Gysel", 200);
	   this.admin.changeCapacity("ECS122B", 2017, 0);
	   assertTrue((admin.getClassCapacity("ECS122B", 2017) > 0));
	}
	
	//Test if class in the past changed capacity
	@Test
	public void testChangeCapacityFail4() 
	{
	   this.admin.createClass("ECS122B", 1980, "Rob Gysel", 200);
	   this.admin.changeCapacity("ECS122B", 1980, 10);
	   assertFalse((admin.getClassCapacity("ECS122B", 2017) <= 200));
	}
	//////////////////// END UNIT TEST for changeCapacity() //////////////////////
	
	//Test if instructor did not violate course limit
	@Test
	public void testInstructorCourseLimitSuccess1() 
	{
	    this.admin.createClass("Class1", 2017, "Rob Gysel", 200);
	    assertTrue(this.admin.classExists("Class1", 2017));
	}
	
	///////////// CORNER CASE ///////////////////////////
	//Test if instructor did not violate course limit. Zero courses made
	@Test
	public void testInstructorCourseLimitSuccess2() 
	{
	    assertFalse(this.admin.classExists("Class1", 2017));
	}
	
	//Test if instructor did not violate course limit, reached max course limit
	@Test
	public void testInstructorCourseLimitSuccess3() 
	{
	    this.admin.createClass("Class1", 2017, "Rob Gysel", 200);
	    this.admin.createClass("Class2", 2017, "Rob Gysel", 200);
	    assertTrue(this.admin.classExists("Class2", 2017));
	}
	///////////// END CORNER CASE ///////////////////////////
	
	//Test if instructor DID violate course limit (more than 2)
	@Test
	public void testInstructorCourseLimitViolation() 
	{
	    this.admin.createClass("Class1", 2017, "Rob Gysel", 200);
	    this.admin.createClass("Class2", 2017, "Rob Gysel", 200);
	    this.admin.createClass("Class3", 2017, "Rob Gysel", 200);
	    assertFalse(this.admin.classExists("Class3", 2017));
	}
}
