package com.gailo22.mongo.hello;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldSparkStyle {

	public static void main(String[] args) {
		
		Spark.get(new Route("/hello") {
			
			@Override
			public Object handle(Request request, Response response) {
				return "Hello World from Spark";
			}
		});
		
	}
}
