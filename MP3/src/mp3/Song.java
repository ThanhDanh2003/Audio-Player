
package mp3;

public class Song {
	private String Title;
	private double length;
	private boolean Favourite;
	private String Path;
	
	public Song() {
		
	}
	
	public void setTitle(String t) {
		Title = t;
	}
	public String getTitle() {
		return Title;
	}
	
	public void setLength(double l) {
		length = l;
	}
	public double getLenght()
	{
		return length;
	}
	
	public void setFavourite(boolean fav) {
		Favourite = fav;
	}
	public boolean getFavourite() {
		return Favourite;
	}
	
	public void setPath(String p) {
		Path = p;
	}
	public String getPath() {
		return Path;
	}
}
