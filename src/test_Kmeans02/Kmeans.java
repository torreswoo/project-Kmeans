package test_Kmeans02;

import java.util.ArrayList;
import java.util.List;

class Kmeans extends Thread{

	private int CLURSTER =10; 
	private int COUNT =5;
	
	private DataBaseManager db =null;
	private CourseData[] centroids = null; // 각각의 클러스터의 중심점이 되는 강의정보 
	private List<Integer>[] clusteredDataSet = null; // 클러스터된 강의들의 id와 매칭됨 			
	private List<CourseData> dataset = new ArrayList<CourseData>(); // 전체 데이타 코스 
	
	
	public Kmeans(){
		
	}
	
	public void run(){
		db = DataBaseManager.getInstance();
		
		//1. DB에연결하여 대이터 전처리  SELECT ( DB -> local)
		this.makeDataSet();
	
		//2.클러스터알고리즘
		this.cluseterAlgorithm(this.COUNT);// 데이터를 local에서 계산
		this.__printCheck(this.clusteredDataSet, this.dataset);
		
		
//		int size = this.centroids.length;
//		System.out.print("centroid: ");
//		for(int i = 0 ; i < size ; ++i){
//			for(int idx = 0 ; idx < 26 ; idx++){
//				System.out.print(centroids[i].getFeature()[idx+1]+",");
//			}
//			System.out.println();
//		}
		

		//3. DB에 연결하여 결과를 INSERT( local -> DB )
		this.saveDataSet();
		
	}
	
	public void makeDataSet(){  		//1. DB에연결하여 대이터 전처리 
		
		this.dataset = this.db.making_CourseData(); //DB정보로 코스정보를 세팅
												// dataset이 메모리에 올라옴!
//		for(int j = 0 ; j < this.dataset.size() ; j++){
//			System.out.println("id:"+dataset.get(j).getCourse_id()+", title: "+dataset.get(j).getCourse_title() );
//			for(int i = 0 ; i < 27 ; i++){
//				System.out.print(dataset.get(j).getFeatureIdx(i)+ ", ");
//			}
//			System.out.println();
//		}		
//		System.out.println(this.dataset.size());
	}
	
	
	public void saveDataSet(){
		db.saving_Cluster(centroids, clusteredDataSet, dataset);		
	}
	
	
	//*  main 알고리즘  *//					//2.클러스터알고리즘
	public void cluseterAlgorithm(int cnt){
		
		//main-1.초기에 중심설정 
		this.centroids = this.firstCentroid(this.dataset, this.CLURSTER);
		//main-2.클러스터!!
		this.clusteredDataSet = this.nearestIds(this.dataset, this.centroids);
	//	this.__printCheck(this.clusteredDataSet, this.dataset);
		
		//main-3 for문을 이용하여 반복적으로 새로운 중심점을 찾기
		for(int i = 0 ; i<this.COUNT ; i++){
			this.centroids = this.newCentroid(clusteredDataSet, this.dataset);
			this.clusteredDataSet = this.nearestIds(this.dataset, this.centroids);
	//		System.out.println("\r\n\r\n"+(i+1)+"th Clusterting==============");			
	//		this.__printCheck(this.clusteredDataSet, this.dataset);
		}
	}
	

	//main-1.초기에 중심설정 	
	public CourseData[] firstCentroid(List<CourseData> dataset, int cnt){
		CourseData[] centroid = new CourseData[cnt];
		int n = (int)Math.floor(dataset.size()/(cnt+2)); //간격띄우기
		
		int index = 0;
		for( int i =0 ; i<dataset.size() ; i+=n){
			if(index >= cnt) break;
			centroid[index] = dataset.get(i);
			index++;
		}
		return centroid;
	}

	//main-3 for문을 이용하여 반복적으로 새로운 중심점을 찾기
	// newCentroid() => sumVectorEachElement() / divideVectorEachElement()
	public CourseData[] newCentroid(List<Integer>[] clustered, List<CourseData> data) {
		int idx=0;
		int size = clustered.length;
		CourseData [] centroid = new CourseData[clustered.length];
		
		for( List<Integer>list :clustered){
			CourseData sum = new CourseData();
			for(Integer index : list){
				this.sumVectorEachElement(sum, data.get(index));
			}
			this.divideVectorEachElement(sum, list.size());
			centroid[idx++] = sum;
		}
		return centroid;
	}
	public static void divideVectorEachElement( CourseData divided, double val ){
		int size = divided.getFeature().length;//??
		for(int i =0 ; i< size ; i++){
			double v = (double)divided.getFeature()[i];
			divided.getFeature()[i] =v/val;
		}
	}
	
	public static void sumVectorEachElement( CourseData added, CourseData val){
		int size = val.getFeature().length;//??
		for(int i = 0 ; i<(size) ; i++){
			double sum = added.getFeature()[i];
			double v = val.getFeature()[i];
			added.getFeature()[i] = v+sum;
		}		
		added.setCourse_title("clustered");
	}	
	
	//main-2.클러스터!!  
	// nearestIds() -> _nearestCluster() -> _similarity() -> _cosineDistance
	public List<Integer>[] nearestIds(List<CourseData> dataset, CourseData[] centroids){
		int clustered = centroids.length;
		List<Integer>[] clusteredDataSet = new ArrayList[clustered];

		for(int i = 0 ; i <clustered ; i++){
			clusteredDataSet[i] = new ArrayList<Integer>();
		}
		for(int i = 0 ; i <dataset.size() ; i++){
			int nearClusterId = this._nearestCluster(dataset.get(i), centroids);
			clusteredDataSet[nearClusterId].add(i);
		}
		return clusteredDataSet;
	}

	//
	public int _nearestCluster(CourseData item, CourseData[] centroids){
		double distance=0, temp=0;
		int index=0, pos=0;
		
		for(int i = 0 ; i <centroids.length ; i++){
			temp = this._similarity(item, centroids[i]);// 유사도 비교!
			if(temp > distance){
				distance = temp; 	
				pos = index;
			}
			index++;
		}
		return pos;
	}
	
	public double _similarity(CourseData data, CourseData center){
		double similarity = this._cosineDistance(data, center);
		return similarity;
	}
	
	private double _cosineDistance(CourseData data, CourseData center){
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
	
/*	///////////////////////////////////////////////////////////////////////
	public double _distance(CourseData data, CourseData center){
	//	double distance = DistanceMeasure.measureCosine(data, center);
	//	return distance;
		
		double distance = cosineDistance(data, center);
		return distance;
	}
	*/	
	
	public void __printCheck(List<Integer>[] clustered, List<CourseData> dataset){
		
		for( List<Integer>list :clustered){
			System.out.print("=clustered: "+list.size() +"  \t=> ");
			for(Integer index : list){
		//		System.out.print(dataset.get(index).getCourse_title() +" | ");
				System.out.print(dataset.get(index).getCourse_id() +" | ");
		//		System.out.print("index:"+ index+", ");
		//		for(int idx = 0 ; idx < 26 ; idx++){
		//			System.out.print(dataset.get(index).getFeatureIdx(idx+1) +" | ");
		//		}
			}
			System.out.println();
		}
	}

}
