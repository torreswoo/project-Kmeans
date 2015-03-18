package test_Kmeans02;

import java.util.Vector;

public class Course {

	private int Course_Id;
	private String Course_Title;
	private String Course_Des;
	private String Course_Thumbnail;
	private String Course_Url;
	private int Course_Category_Cnt;
	private int User_Type_Cd;
	private int Course_level;	
	private Vector<Long> Category_Cd  = new Vector<Long>();
	
	
	public Course(){
		this.Course_Category_Cnt = 0;
		this.Course_Des = "";
		this.Course_Id = 0;
		this.Course_Thumbnail = "";
		this.Course_Title = "";
		this.Course_Url = "";
		this.Course_level = 0;

	}

	public void settingCourseInfo(int Course_Id, String Course_Title, String Course_Des, String Course_Thumbnail, String Course_Url, int Course_Category_Cnt, int Course_level, Vector<Long> Category_Cd){
		this.Course_Category_Cnt = Course_Category_Cnt;
		this.Course_Des = Course_Des;
		this.Course_Id = Course_Id;
		this.Course_Thumbnail = Course_Thumbnail;
		this.Course_Title = Course_Title;
		this.Course_Url = Course_Url;
		this.Course_level = Course_level;
		this.Category_Cd = Category_Cd;
//		this.User_Type_Cd = User_Type_Cd;
	}
	
	public Course(int Course_Id, String Course_Title, String Course_Des, String Course_Thumbnail, String Course_Url, int Course_Category_Cnt, int Course_level, Vector<Long> Category_Cd){
		this.Course_Category_Cnt = Course_Category_Cnt;
		this.Course_Des = Course_Des;
		this.Course_Id = Course_Id;
		this.Course_Thumbnail = Course_Thumbnail;
		this.Course_Title = Course_Title;
		this.Course_Url = Course_Url;
		this.Course_level = Course_level;
		this.Category_Cd = Category_Cd;
//		this.User_Type_Cd = User_Type_Cd;
	}
	
	public void checkCourse(){
		System.out.println("Course_Id : " + this.Course_Id);
		System.out.println("Course_Title : " + this.Course_Title);
		System.out.println("Course_Des : " + this.Course_Des);
		System.out.println("Course_Thumbnail : " + this.Course_Thumbnail);
		System.out.println("Course_Url : " + this.Course_Url);
		System.out.println("Course_Category_Cnt : " + this.Course_Category_Cnt);
		System.out.println("Course_level : " + this.Course_level);
		System.out.println("Category_Cd size: " + this.Category_Cd.size());
	}
	
	public Vector<Long> getCategory_Cd() {
		return Category_Cd;
	}

	public void setCategory_Cd(Vector<Long> category_Cd) {
		Category_Cd = category_Cd;
	}

	
	public int getCourse_level() {
		return Course_level;
	}

	public void setCourse_level(int course_level) {
		Course_level = course_level;
	}
	public int getCourse_Id() {
		return Course_Id;
	}
	public void setCourse_Id(int course_Id) {
		Course_Id = course_Id;
	}
	public String getCourse_Title() {
		return Course_Title;
	}
	public void setCourse_Title(String course_Title) {
		Course_Title = course_Title;
	}
	public String getCourse_Des() {
		return Course_Des;
	}
	public void setCourse_Des(String course_Des) {
		Course_Des = course_Des;
	}
	public String getCourse_Thumbnail() {
		return Course_Thumbnail;
	}
	public void setCourse_Thumbnail(String course_Thumbnail) {
		Course_Thumbnail = course_Thumbnail;
	}
	public String getCourse_Url() {
		return Course_Url;
	}
	public void setCourse_Url(String course_Url) {
		Course_Url = course_Url;
	}
	public int getCourse_Category_Cnt() {
		return Course_Category_Cnt;
	}
	public void setCourse_Category_Cnt(int course_Category_Cnt) {
		Course_Category_Cnt = course_Category_Cnt;
	}
	public int getUser_Type_Cd() {
		return User_Type_Cd;
	}
	public void setUser_Type_Cd(int user_Type_Cd) {
		User_Type_Cd = user_Type_Cd;
	}
	
	
}
