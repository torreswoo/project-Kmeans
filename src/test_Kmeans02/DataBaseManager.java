package test_Kmeans02;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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

	public void insert_course(Course courseInfo){
		
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert_cc(Course_Category cc){
		
		try {
			int Course_Id = cc.getCourse_id();
			int Category_Cd = cc.getCategory_Cd();

			String sql = "INSERT INTO Course_Category( Course_Id, Category_Cd)"
						+" VALUES (?, ?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, Course_Id);
			pstmt.setInt(2, Category_Cd);
	         
	        int result = pstmt.executeUpdate();
	        if(result ==1){
		        System.out.println("DataBaseManager(insert into cc) 성공 "+ Course_Id +", "+Category_Cd);
	        }
	        else{
	        	System.out.println("DataBaseManager(insert into cc) 실패 " + Course_Id+", "+Category_Cd);
	        }


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}
	
	public List<CourseData> setting_CourseData(){
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

	
}
