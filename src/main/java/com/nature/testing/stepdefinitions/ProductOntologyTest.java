package com.nature.testing.stepdefinitions;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(
  features={"target/classes/product_ontology.feature"},
//  tags = {"@wip"},
  format = "html:reports")
public class ProductOntologyTest {	 

}
