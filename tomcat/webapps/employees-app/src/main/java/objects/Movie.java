package objects;

import java.util.Set;

public class Movie {
	int id = 0;
	String title = "";
	int year = 0;
	String director = "";
	String banner = "";
	String trailer = "";
	String genre = "";
	Set<Star> stars = null;
	
	
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

	public Set<Star> getStars() {
		return stars;
	}

	public void setStars(Set<Star> stars) {
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
	
	
	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", year=" + year + ", director=" + director + ", banner="
				+ banner + ", trailer=" + trailer + ", genre=" + genre + ", stars=" + stars + "]";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Movie)) return false;
		Movie rhs = (Movie) obj;
		if (this.id != rhs.id) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		return id;
		//return super.hashCode();
	}
}
