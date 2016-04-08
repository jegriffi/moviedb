package objects;

import java.util.*;

public class Genre {
	int id = 0;
	String genre = "";
	Set<String> movies = null;
	/**
	 * @param id
	 * @param genre
	 * @param movies
	 */
	public Genre(int id, String genre, Set<String> movies) {
		super();
		this.id = id;
		this.genre = genre;
		this.movies = movies;
	}
	/**
	 * @param id
	 * @param genre
	 */
	public Genre(int id, String genre) {
		this.id = id;
		this.genre = genre;
	}
	public Set<String> getMovies() {
		return movies;
	}
	public void setMovies(Set<String> movies) {
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
		return "Genre [id=" + id + ", genre=" + genre + ", movies=" + movies + "]";
	}
	
}
