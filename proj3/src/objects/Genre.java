package objects;

import java.util.*;

public class Genre {
	int id = 0;
	String genre = "";
	Set<Movie> movies = null;
	
	/**
	 * @param id
	 * @param genre
	 */
	public Genre(int id, String genre) {
		this.id = id;
		this.genre = genre;
		movies = new HashSet<Movie>();
		
	}
	
	public void addMovie(Movie m){
		movies.add(m);
	}

	public Set<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	public int getId() {
		return id;
	}
	public String getGenre() {
		return genre;
	}
	@Override
	public String toString() {
		return "Genre [id=" + id + ", genre=" + genre + ", \n\tmovies=" + movieNames() + "]";
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
		if(!(obj instanceof Genre)) return false;
		Genre rhs = (Genre) obj;
		if (this.id != rhs.id) return false;
		
		
		
		return true;
	}
	@Override
	public int hashCode() {
		return id;
		//return super.hashCode();
	}
	
}
