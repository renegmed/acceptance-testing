package com.nature.testing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.nature.testing.stepdefinitions.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Result result = JUnitCore.runClasses(
	    		 ProductOntologyTest.class);
	    for (Failure failure : result.getFailures()) {
	      System.out.println(failure.toString());
	    }		 

	}

}
