package test_Kmeans02;

public class User_Interest {

	private String User_Id;
	private int Cluster_Id;
	
	private double[] feature = new double[27];
	
	public User_Interest(){
		for(int i = 0 ; i < 27 ; i++){
			this.feature[i] = 0;
		}
	}
	public void clear(){
		for(int i = 0 ; i < 27 ; i++){
			this.feature[i] = 0;
		}
	}	
	
	public String getUser_Id() {
		return User_Id;
	}
	public void setUser_Id(String user_Id) {
		User_Id = user_Id;
	}
	public int getCluster_Id() {
		return Cluster_Id;
	}
	public void setCluster_Id(int cluster_Id) {
		Cluster_Id = cluster_Id;
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
	
	
}
