package test_Kmeans02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DBmgr_CourseRecommendTree {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	private static DBmgr_CourseRecommendTree instance = null;
	
	public DBmgr_CourseRecommendTree(){

	}
	public synchronized static DBmgr_CourseRecommendTree getInstance(){
        if(instance == null){
            instance = new DBmgr_CourseRecommendTree();
        }
        return instance;
	}
	
	public void connect(Connection conn){
		
		try {
			this.conn = conn;
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete_Course_Recommend_Tree(){
		try {
			String sql = "delete from Course_Recommend_Tree";

			st.executeUpdate(sql); 
			
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->delete_Course_Recommend_Tree()"+e.getMessage() + " 이미 존재합니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert_Course_Recommend_Tree(CourseRecommendTree courspick){
		
		try{
			String sql = "insert into Course_Recommend_Tree(User_Id, size, Tree_pid, Course_Title, Course_Url, Course_Id) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, courspick.getUser_Id());
			pstmt.setDouble(2, courspick.getSize());
			pstmt.setInt(3, courspick.getTree_pid());
			pstmt.setString(4, courspick.getCourse_Title());
			pstmt.setString(5, courspick.getCourse_Url());
			pstmt.setInt(6, courspick.getCourse_Id());
			
			int result = pstmt.executeUpdate();
		    if(result ==1){
			    System.out.println("DataBaseManager(insert into Cluster_Course) 성공 ");
		    }
		    else{
		     	System.out.println("DataBaseManager(insert into insert_Course_Recommend_Tree) 실패 ");
		    }
			
		} catch (MySQLIntegrityConstraintViolationException e) {
	        System.out.println("DataBaseManager->insert_Course_Recommend_Tree()"+e.getMessage() +  " 이미 존재합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
		

}
