package proj3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SaxParserMains {
	final String file ="standford-movies/mains243.xml";
	private static final Map<String, String> genreCodeNames = Collections.unmodifiableMap(
		    new HashMap<String, String>() {{
		        put("Susp", "thriller");
		        put("CnR", "cops and robbers");
		        put("Dram", "drama");
		        put("West", "western");
		        put("Myst", "mystery");
		        put("S.F.", "science fiction");
		        put("Advt", "adventure");
		        put("Horr", "horror");
		        put("Romt", "romantic");
		        put("Comd", "comedy");
		        put("Musc", "musical");
		        put("Docu", "documentary");
		        put("Porn", "pornography");
		        put("Noir", "black");
		        put("BioP", "biographical Picture");
		        put("TV", "TV show");
		        put("TVs", "TV series");
		        put("TVm", "TV miniseries");
		    }});

	







}
