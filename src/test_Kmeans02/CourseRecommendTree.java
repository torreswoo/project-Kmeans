package test_Kmeans02;

public class CourseRecommendTree {

	private String Course_Title;
	private String Course_Url;
	private String User_Id;
	private int Course_Id;	
	private double size;
	private int Tree_pid;
	
	public CourseRecommendTree(){
		
	}
	
	public void setting(String Course_Title, String Course_Url, String User_Id, int Course_Id, double size, int Tree_Id){
		this.Course_Id = Course_Id;
		this.Course_Title = Course_Title;
		this.Course_Url =Course_Url;
		this.User_Id =User_Id;
		this.size = size;
		this.Tree_pid = Tree_pid;
	}
	
	public String getUser_Id() {
		return User_Id;
	}
	public void setUser_Id(String user_Id) {
		User_Id = user_Id;
	}
	public int getTree_pid() {
		return Tree_pid;
	}
	public void setTree_pid(int tree_pid) {
		Tree_pid = tree_pid;
	}
	public String getCourse_Title() {
		return Course_Title;
	}

	public void setCourse_Title(String course_Title) {
		Course_Title = course_Title;
	}

	public String getCourse_Url() {
		return Course_Url;
	}

	public void setCourse_Url(String course_Url) {
		Course_Url = course_Url;
	}

	public int getCourse_Id() {
		return Course_Id;
	}

	public void setCourse_Id(int course_Id) {
		Course_Id = course_Id;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	
	
	
}
