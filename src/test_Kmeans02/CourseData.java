package test_Kmeans02;

public class CourseData {

	private String Course_title;
	private int Course_id;
	private double[] feature = new double[27];
	
	public CourseData(){
		for(int i = 0 ; i < 27 ; i++){
			this.feature[i] = 0;
		}
	}
	public void clear(){
		for(int i = 0 ; i < 27 ; i++){
			this.feature[i] = 0;
		}
	}
	public String getCourse_title() {
		return Course_title;
	}

	public void setCourse_title(String course_title) {
		Course_title = course_title;
	}

	public int getCourse_id() {
		return Course_id;
	}

	public void setCourse_id(int course_id) {
		Course_id = course_id;
	}

	public double[] getFeature() {
		return feature;
	}

	public void setFeature(double[] feature) {
		this.feature = feature;
	}
	public void setFeatureIdx(int idx){
		this.feature[idx]=1.0;
	}
	public double getFeatureIdx(int idx){
		return this.feature[idx];
	}
}
