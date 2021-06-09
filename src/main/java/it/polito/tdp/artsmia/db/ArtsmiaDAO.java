package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenti;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setVertici(Map<Integer, Artist> idMap, String ruolo) {
		String sql = "SELECT distinct a.artist_id, a.name "
				+ "FROM artists AS a, authorship AS au "
				+ "WHERE au.role = ? AND "
				+ "a.artist_id = au.artist_id ";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artist a = new Artist(res.getInt("a.artist_id"), res.getString("a.name"));
				
				if(!idMap.containsKey(a.getId())) {
					idMap.put(a.getId(), a);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> listRoles(){
		String sql = "SELECT DISTINCT role "
					+"FROM authorship "
					+"ORDER BY role";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(res.getString("role"));
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	public List<Adiacenti> getArchi(Map<Integer,Artist> mappa, String ruolo){
		String sql = "SELECT au1.artist_id, au2.artist_id, COUNT(DISTINCT e2.exhibition_id) AS peso "
				+ "FROM authorship AS au1, objects AS o1, exhibition_objects AS e1, "
				+ "authorship AS au2, objects AS o2, exhibition_objects AS e2 "
				+ "WHERE au1.role = ? "
				+ "AND au1.object_id = o1.object_id AND o1.object_id = e1.object_id "
				+ "and au1.role = au2.role "
				+ "AND au2.object_id = o2.object_id AND o2.object_id = e2.object_id "
				+ "AND e1.exhibition_id = e2.exhibition_id "
				+ "AND au1.artist_id > au2.artist_id "
				+ "GROUP BY au1.artist_id, au2.artist_id "
				+ "ORDER BY peso DESC";
		List<Adiacenti> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artist a1 = mappa.get(res.getInt("au1.artist_id"));
				Artist a2 = mappa.get(res.getInt("au2.artist_id"));
				
				if(a1 != null && a2 != null) {
					result.add(new Adiacenti(a1,a2, res.getInt("peso")));
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
