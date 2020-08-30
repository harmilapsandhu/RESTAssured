package pojo;

public class MainPOJO {
	//We will need Json code to write POJO classes
	//List all the keys as Strings and press Alt+Shift+S and click Generate Getters and Setters	
	private String instructor;
	private String url;
	private String services;
	private String expertise;
	private PojoCourses courses; //Because this is a Nested Json, we will create another pojo class for this and change the return type to that class name.
	private String linkedIn;

	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public PojoCourses getCourses() {  //Change return type to PojoCourses
		return courses; 
	}
	public void setCourses(PojoCourses courses) {  //Change String to PojoCourses
		this.courses = courses;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	
	
}
