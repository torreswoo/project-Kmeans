package test_Kmeans02;

public class CourseData {

	private String Course_title;
	private int Course_id;
	private int Course_recTree_id; // 트리를 만들기 위해 가장 멀리있는 군
	private int Cluster_id;
	private int Cluster_Course_Cnt;
	private String Course_Url;
	
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

	public String getCourse_Url() {
		return Course_Url;
	}
	public void setCourse_Url(String course_Url) {
		Course_Url = course_Url;
	}
	public int getCluster_id() {
		return Cluster_id;
	}
	public void setCluster_id(int cluster_id) {
		Cluster_id = cluster_id;
	}
	public int getCluster_Course_Cnt() {
		return Cluster_Course_Cnt;
	}
	public void setCluster_Course_Cnt(int cluster_Course_Cnt) {
		Cluster_Course_Cnt = cluster_Course_Cnt;
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
	public void setFeatureIdx_val(int idx, double val){
		this.feature[idx]=val;
	}
	public double getFeatureIdx(int idx){
		return this.feature[idx];
	}
	public int getCourse_recTree_id() {
		return Course_recTree_id;
	}
	public void setCourse_recTree_id(int course_recTree_id) {
		Course_recTree_id = course_recTree_id;
	}


}
