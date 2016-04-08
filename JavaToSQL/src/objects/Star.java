package objects;

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
				+ ", movies=" + movies + "]";
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
