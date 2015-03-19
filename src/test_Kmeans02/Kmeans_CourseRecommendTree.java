package test_Kmeans02;

import java.util.ArrayList;
import java.util.List;

public class Kmeans_CourseRecommendTree {

	private DataBaseManager db =null;
	private CourseData[] centroids = null; // 각각의 클러스터의 중심점이 되는 강의정보 
	private List<Integer>[] clusteredDataSet = null; // 클러스터된 강의들의 id와 매칭됨 			
	private List<CourseData> dataset = new ArrayList<CourseData>(); // 전체 데이타 코스 
	private List<User_Interest> user = new ArrayList<User_Interest>();
	
	public Kmeans_CourseRecommendTree(CourseData[] centroids, List<Integer>[] clusteredDataSet, List<CourseData> dataset, List<User_Interest> user){
		this.centroids  = centroids;
		this.clusteredDataSet = clusteredDataSet;
		this.dataset = dataset;
		this.user = user;
	}
	
	public void start(){
		db = DataBaseManager.getInstance();
		db.clear_Course_Recommend_TreeTable();
		
		for(int i = 0 ; i < user.size() ; i++){ // 모든 유저에 대해 반목적인 작업을 수
			
			int min_cluster = 0;
			//1. 유사도가 가장 낮은 군집을 찾기
			double min_sim = 999;
			for(int idx = 0; idx < centroids.length ; idx++ ){ //군집의 중심과 비교 
				double sim = _similarity( user.get(i), centroids[idx] ); 
				if(min_sim > sim){
					min_sim = sim;
					min_cluster = idx;
				}
			}
			
			CourseRecommendTree courspick_root = new CourseRecommendTree();
			courspick_root.setting(centroids[min_cluster].getCourse_title() ,
								  centroids[min_cluster].getCourse_Url(),
								  user.get(i).getUser_Id(),
								  dataset.get(min_cluster).getCourse_id(),
								  min_sim*5100,
								  0); courspick_root.setTree_pid(0);
		//	System.out.println("courspick_root : "+courspick_root.getTree_pid());
			db.saving_Course_Recommend_Tree(courspick_root);
			
			//이로서 min_cluster은 사용자와 가장 멀리 떨어진 군집 됨!..
			//2.전체데이터에서 min_cluster에 속한 강좌만 유사도를 비교해서 상위 3개만 선택!
			double max_sim[] = new double[3];	max_sim[0] = 0; max_sim[1] = 0; max_sim[2] = 0;
			int max_sim_course[] = new int[3];
			for(int idx = 0 ; idx < dataset.size() ; idx++){ //상위3개만선
				if(dataset.get(idx).getCluster_id()== min_cluster ){// 클러스터가 가장 먼강좌들만 
					double sim = _similarity( user.get(i), dataset.get(idx) );
					if( max_sim[0] < sim){
						if( max_sim[1] < sim){
							if(max_sim[2] < sim){
								max_sim[2] = sim;
								max_sim_course[2] = idx;
							}
							else{
								max_sim[1] = sim;
								max_sim_course[1] = idx;
							}
						}
						else{
							max_sim[0] = sim;
							max_sim_course[0] = idx;
						}
					}
				}																																																																											
			}
			
			//3.선택된 3개의 강좌를 쏘아줘!
			for(int idx = 0 ; idx < 3 ; idx++){
				CourseRecommendTree courspick_lv1 = new CourseRecommendTree();
				courspick_lv1.setting(dataset.get(max_sim_course[idx]).getCourse_title() ,
									  dataset.get(max_sim_course[idx]).getCourse_Url(),
									  user.get(i).getUser_Id(),
									  dataset.get(max_sim_course[idx]).getCourse_id(),
									  max_sim[idx]*2800,
									  1);	courspick_lv1.setTree_pid(1);
				db.saving_Course_Recommend_Tree(courspick_lv1);
			//	System.out.println("courspick_lv1 : "+courspick_lv1.getTree_pid());
				
				//4. 각 강좌를 기반으로 2단계를 진행... courspick_lv1 
				// min_cluster에 속한 강좌만 유사도를 비교해서 상위 3개만 선택!
				double max_sim_lv1[] = new double[3];	max_sim_lv1[0] = 0;max_sim_lv1[1] = 0; max_sim_lv1[2] = 0;
				int max_sim_course_lv1[] = new int[3];
				for(int idx_lv1 = 0 ; idx_lv1 < dataset.size() ; idx_lv1++){ //상위3개만선
					if(dataset.get(idx_lv1).getCluster_id()== min_cluster ){// 클러스터가 가장 먼강좌들만 
						double sim = _similarity( user.get(i), dataset.get(idx_lv1) );
						if( max_sim_lv1[0] < sim){
							if( max_sim_lv1[1] < sim){
								if(max_sim_lv1[2] < sim){
									max_sim_lv1[2] = sim;
									max_sim_course_lv1[2] = idx_lv1;
								}
								else{
									max_sim_lv1[1] = sim;
									max_sim_course_lv1[1] = idx_lv1;
								}
							}
							else{
								max_sim_lv1[0] = sim;
								max_sim_course_lv1[0] = idx_lv1;
							}
						}
					}																																																																											
				}
				for(int idx_lv1 = 0 ; idx_lv1 < 3 ; idx_lv1++){
					CourseRecommendTree courspick_lv2 = new CourseRecommendTree();
					courspick_lv2.setting(dataset.get(max_sim_course_lv1[idx_lv1]).getCourse_title() ,
										  dataset.get(max_sim_course_lv1[idx_lv1]).getCourse_Url(),
										  user.get(i).getUser_Id(),
										  dataset.get(max_sim_course_lv1[idx_lv1]).getCourse_id(),
										  max_sim_lv1[idx_lv1]*2800,
										  2);	courspick_lv2.setTree_pid(2);
					db.saving_Course_Recommend_Tree(courspick_lv2);
					
					//
					//5. 각 강좌를 기반으로 2단계를 진행... courspick_lv1 
					// min_cluster에 속한 강좌만 유사도를 비교해서 상위 3개만 선택!
					double max_sim_lv2[] = new double[3];	max_sim_lv2[0] = 0;max_sim_lv2[1] = 0; max_sim_lv2[2] = 0;
					int max_sim_course_lv2[] = new int[3];
					for(int idx_lv2 = 0 ; idx_lv2 < dataset.size() ; idx_lv2++){ //상위3개만선
						if(dataset.get(idx_lv2).getCluster_id()== min_cluster ){// 클러스터가 가장 먼강좌들만 
							double sim = _similarity( user.get(i), dataset.get(idx_lv2) );
							if( max_sim_lv2[0] < sim){
								if( max_sim_lv2[1] < sim){
									if(max_sim_lv2[2] < sim){
										max_sim_lv2[2] = sim;
										max_sim_course_lv2[2] = idx_lv2;
									}
									else{
										max_sim_lv2[1] = sim;
										max_sim_course_lv2[1] = idx_lv2;
									}
								}
								else{
									max_sim_lv2[0] = sim;
									max_sim_course_lv2[0] = idx_lv2;
								}
							}
						}																																																																											
					}
					for(int idx_lv2 = 0 ; idx_lv2 < 3 ; idx_lv2++){
						CourseRecommendTree courspick_lv3 = new CourseRecommendTree();
						courspick_lv3.setting(dataset.get(max_sim_course_lv2[idx_lv2]).getCourse_title() ,
											  dataset.get(max_sim_course_lv2[idx_lv2]).getCourse_Url(),
											  user.get(i).getUser_Id(),
											  dataset.get(max_sim_course_lv2[idx_lv2]).getCourse_id(),
											  max_sim_lv2[idx_lv2]*2800,
											  3);	courspick_lv3.setTree_pid(3);
						db.saving_Course_Recommend_Tree(courspick_lv3);
					}
					
					
				}
			}
		}
		
	}
	
	
	public double _similarity(User_Interest data, CourseData center){
		double similarity = this._cosineDistance(data, center);
		return similarity;
	}
	
	private double _cosineDistance(User_Interest data, CourseData center){
		int size = data.getFeature().length;
		double normA=0, normB=0, scla=0; 
		for(int i = 0 ; i < size ; i++){
			normA += (data.getFeature()[i]*data.getFeature()[i]);
			normB += (center.getFeature()[i]*center.getFeature()[i]);
			scla += (data.getFeature()[i]*center.getFeature()[i]); 
		}
		double similarity = scla / ( Math.sqrt(normA) * Math.sqrt(normB)  );
		return similarity;
	}
	
	
	
}
