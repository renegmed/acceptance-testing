Feature: As a user I want to retrieve the product ontology representation 
         for a given pcode so that I can check the ontology set for that product

    
	Scenario: Getting ontology product from pset 
		Given ontology existing product code "${ontology.product.to.get}"
		And using pset url "${ontology.pset.url}"       
     	When pset calls api to get the product details 
       	Then user should receive the following data        
       	${ontology.product.expected.data}
       	
    Scenario: Getting non-existent ontology product from pset    	 
    	Given ontology existing product code "${ontology.product.nonexistent.to.get}"
		And using pset url "${ontology.pset.url}"       
     	When pset calls api to get the product details 
       	Then user should receive error message "${ontology.product.nonexistent.error.data}"  	
       	
    Scenario: Failure to communicate with api when getting ontology product from pset


          	