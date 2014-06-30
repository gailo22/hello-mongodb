package com.gailo22.mongo.hello;

import java.io.StringWriter;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldMongoDBSparkFreemarkerStyle {

	public static void main(String[] args) throws UnknownHostException {

		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreemarkerStyle.class, "/");

		MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

		DB database = client.getDB("test");
		final DBCollection collection = database.getCollection("hello");

		DBObject document = collection.findOne();
		System.out.println(document);

		Spark.get(new Route("/hello") {

			@Override
			public Object handle(Request request, Response response) {
				StringWriter writer = new StringWriter();
				try {
					Template helloTemplate = configuration.getTemplate("hello.ftl");
					
					DBObject document = collection.findOne();

					helloTemplate.process(document, writer);

					System.out.println(writer);

				} catch (Exception e) {
					halt(500);
					e.printStackTrace();
				}

				return writer;
			}
		});

	}
}
