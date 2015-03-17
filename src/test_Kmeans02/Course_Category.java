package test_Kmeans02;


public class Course_Category {

	private int Course_id;
	private int Category_Cd;
	
	public Course_Category(int Course_id, int Category_Cd){
		this.Course_id = Course_id;
		this.Category_Cd = Category_Cd;		
	}

	public int getCourse_id() {
		return Course_id;
	}

	public void setCourse_id(int course_id) {
		Course_id = course_id;
	}

	public int getCategory_Cd() {
		return Category_Cd;
	}

	public void setCategory_Cd(int category_Cd) {
		Category_Cd = category_Cd;
	}
	
	
}
