package objects;

import java.util.List;

public class Star {
	
	int id = 0;
	String first = "";
	String last = "";
	String dob = "1-1-1";
	String photo = "";
	List<String> movies = null;

	public Star(int id, String first, String last, String dob, String photo) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.dob = dob;
		this.photo = photo;
	}
	
	public List<String> getMovies() {
		return movies;
	}

	public void setMovies(List<String> movies) {
		this.movies = movies;
	}
	
	public int getId() {
		return id;
	}
	public String getFirst() {
		return first;
	}
	public String getLast() {
		return last;
	}
	public String getDob() {
		return dob;
	}
	public String getPhoto() {
		return photo;
	}

}
