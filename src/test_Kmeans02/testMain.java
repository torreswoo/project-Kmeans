package test_Kmeans02;

import java.util.List;
import java.util.ArrayList;

public class testMain {

	public static void main(String [] args){
		
		
//		//1. DB에연결하여 대터 전처
//		DataBaseManager db = DataBaseManager.getInstance();
//		List<CourseData> dataset = new ArrayList<CourseData>();
//		CourseData cd = new CourseData();
//		db.setting_CourseData(dataset); //DB정보로 코스정보를 세팅
//										// dataset이 메모리에 올라옴!
//		System.out.println(dataset.size());
				
		//2. K means 알고리즘!
		Kmeans kmeans = new Kmeans();
		kmeans.start();
		
	}
}


