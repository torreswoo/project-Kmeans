package test_Kmeans02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DBmgr_UserCluster {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	private static DBmgr_UserCluster instance = null;
	public DBmgr_UserCluster(){

	}
	public synchronized static DBmgr_UserCluster getInstance(){
        if(instance == null){
            instance = new DBmgr_UserCluster();
        }
        return instance;
  }

	
	public void connect(Connection conn){
		this.conn = conn;
	}

	public void _saving_User_Interest(List<User_Interest> user){
		
		//update User_Interest set Cluster_Id=? where User_Id=?
    	try {
    		
    		String sql = "update User_Interest set Cluster_Id=? where User_Id=?";
			pstmt=conn.prepareStatement(sql);

			for(int i = 0 ; i < user.size() ; i++){
				pstmt.setInt(1, user.get(i).getCluster_Id() );		
				pstmt.setString(2, user.get(i).getUser_Id() );
						         
			    int result = pstmt.executeUpdate();
			    if(result ==1){
			//      System.out.println("DataBaseManager(insert into cluster_centroid) 성공 "+ Course_Title);
			    }
			    else{
			      	System.out.println("DataBaseManager(update into _saving_User_Interest) 실패 ");
			    }
			}
			
    	} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("DataBaseManager->insert_cluster_centroid()"+e.getMessage() +  " 이미 존재합니다.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<User_Interest> _making_User_Interest(){

		List<User_Interest> result_dataset = new ArrayList<User_Interest>(); 	
		
	    try {
	    	
	    	String sql = "select * from User_Interest";
	    	st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				User_Interest data = new User_Interest();
				data.clear();
				data.setUser_Id( rs.getString("User_Id") );
			
				for(int idx = 0 ; idx<26 ; idx++ ){
					String str = "i"+String.valueOf(idx+1);
					data.setFeatureIdx_val( idx+1, rs.getDouble( str));		
				}
				
				result_dataset.add(data);
			//	System.out.println("user_id: " + data.getUser_Id() );
			}
			rs.close();       //resultset 닫음
			
			return result_dataset;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("DataBaseManager->making_CourseData()"+e.getMessage() +  " 이미 존재합니다.");
	
	    }catch(Exception e){
	     
	      e.printStackTrace();
	    }
	    
	    return null;
	}		
}
