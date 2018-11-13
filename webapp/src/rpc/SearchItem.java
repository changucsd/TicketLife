package rpc;

import java.io.IOException;
//import java.io.PrintWriter;
import java.util.*;

import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.json.JSONObject;

import database.DBConnection;
import database.DBConnectionFactory;
import entity.Item;
//import external.TicketMasterAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SearchItem() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//response.setContentType("text/html");
		
		
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		
		
	
		// term can be empty
		String term= request.getParameter("term");
		
		String userId = request.getParameter("user_id");
		
        DBConnection connection = DBConnectionFactory.getConnection();
           try {
        	   List<Item> items = connection.searchItems(lat, lon, term);
        	   
        	   Set<String> favoriteItemIds = connection.getFavoriteItemIds(userId);
        	   
        	   JSONArray array = new JSONArray();
        	   for (Item item : items) {
        		   
        		   JSONObject obj = item.toJSONObject();
        		   obj.put("favorite", favoriteItemIds.contains(item.getItemId()));
        		   array.put(obj);
        	   }
        	   RpcHelper.writeJsonArray(response, array);
 
          } catch (Exception e) {
        	  e.printStackTrace();
          } finally {
        	  connection.close();
          }
		
           
//		TicketMasterAPI tmAPI = new TicketMasterAPI();  remove because we move tickmasterapi to sql connection
//		List<Item> items = tmAPI.search(lat, lon, term);
//		
//		JSONArray array = new JSONArray();
//		try {
//			for (Item item : items) {
//				JSONObject obj = item.toJSONObject();
//				array.put(obj);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		RpcHelper.writeJsonArray(response, array);
	
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
