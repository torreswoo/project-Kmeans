package test_Kmeans02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class DataBaseManager {
	
	private static DataBaseManager instance = null;
	
	private Connection conn = null;
	private String URL = "jdbc:mysql://175.126.56.188:3306/courspick";
	private String id = "nyb";
	private String pw = "Skp02466";
	
	PreparedStatement pstmt = null;
	Statement st = null;
	ResultSet rs = null;	
	
	public DataBaseManager(){
		connect();
	}
	public synchronized static DataBaseManager getInstance(){
        if(instance == null){
            instance = new DataBaseManager();
        }
        return instance;
    }
	
	public void connect(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, id, pw);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
	
	
	// save Cluster Info to =>Cluster_Centroid , => Cluster_Category
	public int saving_Cluster(CourseData[] centroids, List<Integer>[] clusteredDataSet, List<CourseData> dataset){

		this.clear_Cluster_CentroidTable();
		this.clear_Cluster_CourseTable();
		
		this.insert_cluster_centroid(centroids);
		this.insert_cluster_course(clusteredDataSet, dataset);
		
		
		return 0;
	}
	
	public void clear_Cluster_CentroidTable(){
		try {
			String sql = "delete from Cluster_Centroid";
			st.executeUpdate(sql); 
			
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->clear_Cluster_CentroidTable()"+e.getMessage() + " 이미 존재합니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void clear_Cluster_CourseTable(){
		try {
			String sql = "delete from Cluster_Course";
			st.executeUpdate(sql); 
			
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->clear_Cluster_CourseTable()"+e.getMessage() + " 이미 존재합니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert_cluster_centroid(CourseData[] centroids) {//throws SQLException {
		int size = centroids.length;
		
		for(int i = 0 ; i < size ; ++i){
			int Cluster_Id = i;
			int Course_Id = centroids[i].getCourse_id();
			String Course_Title = centroids[i].getCourse_title();
			//	double interest[] = new double[26];
			double interest[] = new double[26];
			for(int idx = 0 ; idx < 26 ; idx++){
				interest[idx] = centroids[i].getFeature()[idx+1];
			//	System.out.print(interest[idx]+",");
			}
			//System.out.println();
				
				//int User_Type_Cd;
			try {
				String sql = "INSERT INTO Cluster_Centroid( Cluster_Id, Course_Id, Course_Title,"
							+"i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, i26) "
							+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt=conn.prepareStatement(sql);

				pstmt.setInt(1, Cluster_Id);
				pstmt.setInt(2, Course_Id);				
				pstmt.setString(3, Course_Title);
				for(int idx = 0 ; idx < 26 ; idx++){
					pstmt.setDouble(idx+4, interest[idx]);
				}		         
			    int result = pstmt.executeUpdate();
			    if(result ==1){
			//      System.out.println("DataBaseManager(insert into cluster_centroid) 성공 "+ Course_Title);
			    }
			    else{
			      	System.out.println("DataBaseManager(insert into cluster_centroid) 실패 " + Course_Title);
			}
			
			} catch (MySQLIntegrityConstraintViolationException e) {
				System.out.println("DataBaseManager->insert_cluster_centroid()"+e.getMessage() +  " 이미 존재합니다.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void insert_cluster_course(List<Integer>[] clusteredDataSet, List<CourseData> dataset){
		int size = clusteredDataSet.length;
		try {

			int clusterNum=0;
			for( List<Integer>list :clusteredDataSet){
					
				for(Integer index : list){
					String sql = "INSERT INTO Cluster_Course( Cluster_Id, Course_Id, Cluster_Course_Cd) VALUES (?, ?, ?)";
					pstmt=conn.prepareStatement(sql);
					int Cluster_Id = clusterNum;
					int Course_Id = dataset.get(index).getCourse_id();						pstmt.setInt(1, Cluster_Id);
					pstmt.setInt(2, Course_Id);	
					//Cluster_Course_Cd
					int Cluster_Course_Cd = Integer.parseInt(String.valueOf(Cluster_Id) + "000"+String.valueOf(Course_Id));
					pstmt.setInt(3, Cluster_Course_Cd);	

					int result = pstmt.executeUpdate();
				    if(result ==1){
				//	    System.out.println("DataBaseManager(insert into Cluster_Course) 성공 " + Course_Id);
				    }
				    else{
				     	System.out.println("DataBaseManager(insert into Cluster_Course) 실패 " + Course_Id);
				    }
				}
				clusterNum++;
			}
		
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->insert_cluster_course()"+e.getMessage() +  " 이미 존재합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	
	// make CourseData from <= Course, <= Course_Category
	public List<CourseData> making_CourseData(){
	//	dataset.setCourse_id(123);
//		CourseData data = new CourseData();
		List<CourseData> result_dataset = new ArrayList<CourseData>(); 
	
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		int courseId =0;
	    try {
	    	
	    	String sql1 = "select * from Course";
	    	st = conn.createStatement();
			rs = st.executeQuery(sql1);
			
			while(rs.next()){
				CourseData data = new CourseData();
				data.clear();
				data.setCourse_id(rs.getInt("Course_Id"));
				data.setCourse_title(rs.getString("course_title"));
				
				String sql2 = "select * from Course_Category where Course_Id=?";
				pstmt=conn.prepareStatement(sql2);				
				pstmt.setInt(1, data.getCourse_id());
				
				rs2 = pstmt.executeQuery();
				while(rs2.next()){
					data.setFeatureIdx( rs2.getInt("Category_Cd") );
				}		
				result_dataset.add(data);
			}
			rs.close();       //resultset 닫음
			
			return result_dataset;
	    }catch(Exception e){
	     
	      e.printStackTrace();
	    }
	    
	    return null;
	}

	
	public void insert_course(Course courseInfo) {//throws SQLException {
		
		try {
			int Course_Id = courseInfo.getCourse_Id();
			int Category_Cnt = courseInfo.getCourse_Category_Cnt();
			String Course_Title = courseInfo.getCourse_Title();
			String Course_Des =courseInfo.getCourse_Des();
			String Course_Thumbnail = courseInfo.getCourse_Thumbnail();
			String Course_Url = courseInfo.getCourse_Url();
			int Course_level = courseInfo.getCourse_level();
			
			//int User_Type_Cd;
			
			String sql = "INSERT INTO Course( Course_Title, Course_Des, Course_Thumbnail, Course_Url,"
						+" User_Type_Cd, Course_Id, Category_Cnt, Course_Lv) "
						+" VALUES (?, ?, ?, ?, 0, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, Course_Title);
			pstmt.setString(2, Course_Des);
			pstmt.setString(3, Course_Thumbnail);
			pstmt.setString(4, Course_Url);
			pstmt.setInt(5, Course_Id);
			pstmt.setInt(6, Category_Cnt);
			pstmt.setInt(7, Course_level);
	         
	        int result = pstmt.executeUpdate();
	        if(result ==1){
		        System.out.println("DataBaseManager(insert into Course) 성공 "+ Course_Title);
	        }
	        else{
	        	System.out.println("DataBaseManager(insert into Course) 실패 " + Course_Title);
	        }
	        
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->insert_course()"+e.getMessage() + " : "+courseInfo.getCourse_Id()+  "가 이미 존재합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_course_category(Course_Category cc){
		
		try {
			int Course_Id = cc.getCourse_id();
			int Category_Cd = cc.getCategory_Cd();
			int Course_Category_Cd = cc.getCourse_Category_Cd();

			String sql = "INSERT INTO Course_Category( Course_Id, Category_Cd, Course_Category_Cd)"
						+" VALUES (?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, Course_Id);
			pstmt.setInt(2, Category_Cd);
			pstmt.setInt(3, Course_Category_Cd);
	         
	        int result = pstmt.executeUpdate();
	        if(result ==1){
		        System.out.println("DataBaseManager(insert into cc) 성공 "+ Course_Id +", "+Category_Cd);
	        }
	        else{
	        	System.out.println("DataBaseManager(insert into cc) 실패 " + Course_Id+", "+Category_Cd);
	        }
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->insert_course_category()"+e.getMessage() + " 이미 존재합니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}
	
	
}
