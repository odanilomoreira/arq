package iff.tcc;

import java.util.Set;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.web.HttpOp;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;


public class MainAuth {

	public static void main(String[] args) {
		
		/*

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		Credentials credentials = new UsernamePasswordCredentials("super", "super");
		credsProvider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider)
				.build();
		HttpOp.setDefaultHttpClient(httpclient);
		
		*/
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		Credentials unscopedCredentials = new UsernamePasswordCredentials("super", "super");
		credsProvider.setCredentials(AuthScope.ANY, unscopedCredentials);
		Credentials scopedCredentials = new UsernamePasswordCredentials("super", "super");
		final String host = "http://127.0.0.1";
		final int port = 10035;
		final String realm = "teste";
		final String schemeName = "teste";
		AuthScope authscope = new AuthScope(host, port, realm, schemeName);
		credsProvider.setCredentials(authscope, scopedCredentials);
		HttpClient httpclient = HttpClients.custom()
		    .setDefaultCredentialsProvider(credsProvider)
		    .build();
		HttpOp.setDefaultHttpClient(httpclient);
		/*
		try {
			String queryString = ""
					+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>\r\n" + 
					"INSERT DATA\r\n" + 
					"{ \r\n" + 
					"  <http://example/book1> dc:title \"Livro do Jena\" ;\r\n" + 
					"                         dc:creator \"Creator do Jena\" .\r\n" + 
					"}";
			
			Query sparqlQuery = QueryFactory.create(queryString, Syntax.syntaxARQ);
			
			String dataset = host + ":" + port;
			
			QueryExecution qe = QueryExecutionFactory.sparqlService(dataset, sparqlQuery);
			qe.execConstruct();
			System.out.println("Triplas adicionadas com sucesso");
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		*/
		
		inserirTripla();
		//listarTudo("","","" );
		
		
	}
	
	private static void inserirTripla() {
			
		String q = "PREFIX dc: <http://purl.org/dc/elements/1.1/>\r\n" + 
				"INSERT DATA\r\n" + 
				"{ \r\n" + 
				"  <http://example/book4> dc:title \"Livro do Jena OKKKKK\" ;\r\n" + 
				"                         dc:creator \"Creator do Jena FUNCIONOU\" .\r\n" + 
				"}";
		
		String sparqlEndpoint = "http://localhost:10035/repositories/teste/sparql";
		
		UpdateRequest request = UpdateFactory.create(q);
		UpdateProcessor up = UpdateExecutionFactory.createRemote(request, sparqlEndpoint);
		up.execute();
		
		
		/*
		String dir = "ds2";
		
		Dataset dataset = TDBFactory.createDataset(dir);
		UpdateRequest request = UpdateFactory.create();
		request.add(q);
		
		UpdateAction.execute(request, dataset);
		
		
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:10035/repositories/teste/sparql", query);
		qexec.execConstruct(); */
		}
	
	
	
	private static void listarTudo(String s, String p, String o) {
		//logger.info("find resources matched with the pattern - <s> < " + p + "> <" + o + ">");
		
		String q = "SELECT ?subject ?predicate ?object\r\n" + 
				"WHERE {\r\n" + 
				"  ?subject ?predicate ?object\r\n" + 
				"}\r\n"+
				"LIMIT 30";
		
		//logger.info("query to dbpedia: " + q);
		
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:10035/repositories/teste/sparql", query);
		qexec.execSelect();
		ResultSet results = qexec.execSelect();	
		
		while (results.hasNext()) {
			QuerySolution qs = results.next();
			System.out.println(qs + "\n");
			}
		}
		
		//logger.info(cnt + " matched resources are added");
	}
	 



