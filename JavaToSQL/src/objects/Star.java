package objects;

import java.util.HashSet;
import java.util.Set;

public class Star {
	
	int id = 0;
	String first = "";
	String last = "";
	String dob = "1-1-1";
	String photo = "";
	Set<Movie> movies = null;

	/**
	 * 
	 * @param id
	 * @param first
	 * @param last
	 * @param dob
	 * @param photo
	 */
	public Star(int id, String first, String last, String dob, String photo) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.dob = dob;
		this.photo = photo;
		this.movies = new HashSet<Movie>();
	}
	
	public void addMovie(Movie m){
		movies.add(m);
	}
	
	public void setMovies(Set<Movie> movies){
		this.movies = movies;
	}
	
	public Set<Movie> getMovies(){
		return movies;
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

	@Override
	public String toString() {
		return "Star [id=" + id + ", first=" + first + ", last=" + last + ", dob=" + dob + ", photo=" + photo
				+ ", \n\tmovies=" + movieNames() + "]";
	}
	
	private String movieNames(){
		String names = "";
		for(Movie m : movies){
			names += m.getTitle() + ", ";
		}
		return names;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Star)) return false;
		Star rhs = (Star) obj;
		if (this.id != rhs.id) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		return id;
		//return super.hashCode();
	}

}
