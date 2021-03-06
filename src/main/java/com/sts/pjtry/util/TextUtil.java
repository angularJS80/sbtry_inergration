package com.sts.pjtry.util;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TextUtil {
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
	
	public static Map<String, Object> readXml(MultipartFile file) {
		Map<String, Object> rtnmap = new HashMap<>();
		Scanner sc = null;
		String parseResult = "";
		ArrayList<HashMap> list = new ArrayList<>();
		
	    try {
			sc = new Scanner(file.getInputStream(), "UTF-8");
			Boolean lineaddOn = false;
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        
		        
		        if(line.indexOf("<page>")>0) {	
		        		lineaddOn = true;
		        }
		        if(lineaddOn) {
		        	parseResult = parseResult + line;
		        }
		        
				if("</page>".equals(line.trim())) {										
					JSONObject xmlJSONObj = XML.toJSONObject(parseResult);
					//System.out.println(xmlJSONObj.toString());
					HashMap<String,Object> hashMap = (HashMap<String,Object>)gson.fromJson(xmlJSONObj.toString(), HashMap.class);
					list.add(hashMap);
					parseResult= new String();
					System.out.println(hashMap);
					lineaddOn = false;
				}
				
		        
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {			   
		    if (sc != null) {
		        sc.close();
		    }
		}
	    
		rtnmap.put("rtnList", list);
		return rtnmap;
	}
}

