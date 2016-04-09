package com.example.employees;

import java.util.Optional;
import java.io.File;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {
	public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));
	public static final Optional<String> HOSTNAME = Optional.ofNullable(System.getenv("HOSTNAME"));

	public static void main(String[] args) throws Exception {
		String contextPath = "/";
		String appBase = "src/main/webapp/";
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(Integer.valueOf(PORT.orElse("8080")));
		tomcat.setHostname(HOSTNAME.orElse("localhost"));
		tomcat.getHost().setAppBase(appBase);
		StandardContext ctx = (StandardContext) tomcat.addWebapp(contextPath, new File(appBase).getAbsolutePath());

		File additionWebInfClasses = new File("target/classes");
		WebResourceRoot resources = new StandardRoot(ctx);
		resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", 
			additionWebInfClasses.getAbsolutePath(), "/"));
			ctx.setResources(resources);

		tomcat.start();
		tomcat.getServer().await();
	}
}
