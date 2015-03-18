package test_Kmeans02;

public class Course_Category {

	private int Course_id;
	private int Category_Cd;
	private int Course_Category_Cd;

	public Course_Category(){
		this.Course_id = 0;
		this.Category_Cd = 0;
	}
	
	public Course_Category(int Course_id, int Category_Cd, int Course_Category_Cd){
		this.Course_id = Course_id;
		this.Category_Cd = Category_Cd;
		this.Course_Category_Cd= Course_Category_Cd;
	}

	public void settingInfo(int Course_id, int Category_Cd, int Course_Category_Cd){
		this.Course_id = Course_id;
		this.Category_Cd = Category_Cd;
		this.Course_Category_Cd= Course_Category_Cd;
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
	public int getCourse_Category_Cd() {
		return Course_Category_Cd;
	}

	public void setCourse_Category_Cd(int course_Category_Cd) {
		Course_Category_Cd = course_Category_Cd;
	}	
	
}
