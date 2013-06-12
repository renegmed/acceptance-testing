package com.nature.testing.stepdefinitions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Assert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import cucumber.api.java.Before;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProductOntologyDefinitions {
    String productCode = null;
	String psetUrl = null;
    List<Item> items = null;
    
    @Before(value="@InitializeItems")
    public void initializeItems() {
    	
    }
    
    @After(value="@ClearItems")
    public void clearItems() {
  
    }
    
	@Given("^ontology existing product code \"([^\"]*)\"$")
	public void ontology_existing_product_code(String productCode) {
		this.productCode = productCode;
	}
	
	@And ("^using pset url \"([^\"]*)\"$")	
	public void using_pset_url(String psetUrl) {
		this.psetUrl = psetUrl;
		System.out.println("psetUrl: " + psetUrl);
	}
	
 	@When("^pset calls api to get the product details$")
 	public void pset_calls_api_to_get_the_product_details() {
 		
 	}
 	
 	/*
 	 * {
         "bean" : {
           "id" : 1,
           "productCollection" : "crawled_content",
           "typeOfContent" : "repurposed",
           "firstPublicationDate" : "2012-12-12T05:00:00.000+0000",
           "firstVolumeNumber" : "12333",
           "firstIssueNumber" : "254",
           "ongoingPublication" : "false",
           "product" : {
              "pcode" : "testpcodeone",
              "alerts" : [ ],
              "id" : 1
            },
           "componentStatus" : "COMPLETE",
           "updated" : "2013-04-12T14:54:57.000+0000"
         },
         "code" : 200,
         "errors" : null
       }

 	 */
   	@Then("^user should receive the following data$")  
   	public void user_should_receive_the_following_data(List<Item> items) {
   	  this.items = items;
	  
   	  String result = getPsetProductDetail(productCode);  	          
      //System.out.println (result);
   	  try {
   	      //JsonFactory jsonFactory = new JsonFactory();
          //JsonParser jp = jsonFactory.createJsonParser(result);
          //JsonNode node = jp.readValueAsTree();
          //System.out.println("  Product Collection: " + node.get("bean.productCollection"));
          
          ObjectMapper mapper = new ObjectMapper();
          JsonNode obj = mapper.readTree(result);
          //System.out.println("  Product Collection: " + obj.get("bean"));
          JsonNode bean = obj.get("bean");
          for (Item item : items) {
        	  System.out.println(" Item name to find: " + mapName(item.name) + " with value " +  item.value.trim() 
        			       + "     bean item value: " + bean.findValue(mapName(item.name)).toString());
        	  Assert.assertTrue("The item " + item.name + " is not equal to " + item.value + ".", 
        			        bean.findValue(mapName(item.name)).toString().equals("\"" + item.value.trim() + "\"") );
          }
         // System.out.println("  VALUE: " + bean.findValue("productCollection"));
          
   	  } catch (Exception e) {
   		  e.printStackTrace();  
   	  }
        
   	}
   
   	@Then("^user should receive error message \"([^\"]*)\"$")
   	public void user_should_receive_error_message(String errorMessage) {
   		String result = getPsetProductDetail(productCode);  	          
        //System.out.println (result);
   		Assert.assertTrue("Result should contain '" + errorMessage + "'",result.contains(errorMessage));   		
   	}
   	
   	
   	private String mapName(String name) {
    	if (name.toLowerCase().trim().equals("product collection"))            {	return "productCollection";
   		} else if (name.toLowerCase().trim().equals("type of content"))        {    return "typeOfContent";  
   	    } else if (name.toLowerCase().trim().equals("first publication date")) {	return "firstPublicationDate"; 
   		} else if (name.toLowerCase().trim().equals("first volume number"))    {	return "firstVolumeNumber";
   		} else if (name.toLowerCase().trim().equals("first issue number"))     {	return "firstIssueNumber";
   		} else if (name.toLowerCase().trim().equals("ongoing publication"))    {	return "ongoingPublication"; 
   		} else if (name.toLowerCase().trim().equals("product id"))             {	return "id"; 
   		} else if (name.toLowerCase().trim().equals("product code"))           {	return "pcode";
   		} else if (name.toLowerCase().trim().equals("component status"))       {	return "componentStatus";   
   		} else { return name.trim(); }
    }
   	
   	private String getPsetProductDetail(String productCode) {
	  InputStreamReader reader = null;
	  BufferedReader bReader = null;	   
	  StringBuffer result = new StringBuffer();
	  
	  try { 	
	    	    
	    HttpClient client = new HttpClient();    	 
        client.getHttpConnectionManager().
		    getParams().
		    setConnectionTimeout(2000);          
        
	    GetMethod method = new GetMethod(psetUrl + productCode);		
	     
	    client.executeMethod(method);    
        reader = new InputStreamReader(new BufferedInputStream(method.getResponseBodyAsStream()));       
        bReader = new BufferedReader(reader);
        String tmpStr = null;
              
        while ( (tmpStr = bReader.readLine())!= null   ) {
     	   result.append(tmpStr + "\n");        
        }
         
        
	  } catch (Exception e) {
           e.printStackTrace();           
	  } finally {
  	     if (reader!=null) {
  		   try {
  			 reader.close(); 
  		   } catch (Exception ioe) {    			 
  		   }
  	     }             

         if (bReader!=null)
  	     try {
            bReader.close();
  	     } catch (Exception ioe) {		
		 }  
      }
	  
	  return result.toString();
   	}  
   	
    protected class Item {
    	public String name;
    	public String value; 
    }
}
