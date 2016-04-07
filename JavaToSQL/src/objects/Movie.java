package objects;

import java.util.List;

public class Movie {
	
	int id = 0;
	String title = "";
	int year = 0;
	String director = "";
	String banner = "";
	String trailer = "";
	String genre = "";
	List<String> stars = null;
	
	
	
	/**
	 * @param id
	 * @param title
	 * @param year
	 * @param director
	 * @param banner
	 * @param trailer
	 * @param genre
	 */
	public Movie(int id, String title, int year, String director, String banner, String trailer, String genre) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner = banner;
		this.trailer = trailer;
		this.genre = genre;
	}
	
	public List<String> getStars() {
		return stars;
	}

	public void setStars(List<String> stars) {
		this.stars = stars;
	}

	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public int getYear() {
		return year;
	}
	public String getDirector() {
		return director;
	}
	public String getBanner() {
		return banner;
	}
	public String getTrailer() {
		return trailer;
	}
	
	

}
