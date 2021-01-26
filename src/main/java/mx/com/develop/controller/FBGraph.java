
package mx.com.develop.controller;

/**
 *
 * @author ESTRADA
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FBGraph {
private String accessToken; 
       
	public FBGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFBGraph() {
		String graph = null;
		try {

			//String g = "https://graph.facebook.com/me?"; 
                        String g =  "https://graph.facebook.com/v9.0/me?fields=id%2Cname%2Cemail%2Cfirst_name%2Cpicture&access_token="+accessToken;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuilder b = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine).append("\n");
			in.close();
			graph = b.toString();
			System.out.println(graph); 
                          
                        
                        
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

	public Map getGraphData(String fbGraph)  { 
                
		Map fbProfile = new HashMap();
		try {
			JSONObject json = new JSONObject(fbGraph); 
                        //Leer el objeto de picture es un json anidado
                        JSONObject objPicture = json.getJSONObject("picture");  
                        JSONObject objData = objPicture.getJSONObject("data"); 
                        /// 
			fbProfile.put("id", json.getString("id")); 
			fbProfile.put("name", json.getString("name")); 
                        fbProfile.put("url", objData.getString("url"));
			if (json.has("email"))
				fbProfile.put("email", json.getString("email")); 
                                          
                        
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		} 
                
		return fbProfile;
	}
}



