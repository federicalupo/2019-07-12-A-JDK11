package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> listaVertici(Integer n, Map<Integer, Food> idMap){
		String sql = "select food.`food_code`, food.`display_name` " + 
				"from portion, food " + 
				"where portion.`food_code` = food.`food_code` " + 
				"group by food_code " + 
				"having count(*) <=? " + 
				"order by display_name asc";
		
		List<Food> cibi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, n);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
					Food f = new Food(res.getInt("food_code"),
							res.getString("display_name")
							);
				
					cibi.add(f);
					idMap.put(f.getFood_code(), f);	
			}
			
			conn.close();
			return cibi ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Arco> listaArchi(Map<Integer, Food> idMap){
		String sql="select f1.`food_code` as f1, f2.`food_code` as f2, avg(`condiment_calories`) as avg " + 
				"from food_condiment as f1, food_condiment as f2, condiment c " + 
				"where f1.`condiment_code` = f2.`condiment_code` " + 
				"and f1.`food_code`< f2.`food_code` " + 
				"and f1.`condiment_code`=c.`condiment_code` " + 
				"group by f1.`food_code`, f2.`food_code`";
		List<Arco> archi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Food f1 = idMap.get(res.getInt("f1"));
				Food f2 = idMap.get(res.getInt("f2"));
				
				if(f1!=null && f2!=null) { //esistono vertici
					archi.add(new Arco(f1, f2, res.getDouble("avg")));
				}
						
			}
			
			conn.close();
			return archi ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
