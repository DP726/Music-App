import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists;
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		//Checks if the content is a song
		if(content.getType().equals(Song.TYPENAME)){
			Song song = (Song) content;
			if(!songs.contains(song)){
				System.out.println("SONG " + song.getTitle() + " Added to Library");
				songs.add(song);
			} else {
				throw new AudioContentAlreadyExistsException("SONG " + song.getTitle() + " already downloaded");
			}
		//Checks if the content is an audiobook
		} else if(content.getType().equals(AudioBook.TYPENAME)){
			AudioBook audiobook = (AudioBook) content;
			if(!audiobooks.contains(audiobook)){
				audiobooks.add(audiobook);
				System.out.println("AUDIOBOOK " + audiobook.getTitle() + " Added to Library");
			} else {
				throw new AudioContentAlreadyExistsException("AUDIOBOOK " + audiobook.getTitle() + " already downloaded");
			}
		}
	}

	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". " + playlists.get(i).getTitle());
			System.out.println();
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names

		ArrayList<String> artists = new ArrayList<String>();
		for(int i = 0; i < songs.size(); i++){
			if(!artists.contains(songs.get(i).getArtist())){
				artists.add(songs.get(i).getArtist());
			}
		}

		for(int i = 0; i < artists.size(); i++){
			System.out.println(i+1 + ". " + artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		
		for(int i = 0; i < playlists.size(); i++){
			delContentFromPlaylist(playlists.get(i).getContent().indexOf(songs.get(index-1))+1, playlists.get(i).getTitle());
		}
		songs.remove(index-1);
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort()
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song firstSong, Song secondSong){
			return firstSong.getYear()-secondSong.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
		Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song firstSong, Song secondSong){
			return firstSong.getLength()-secondSong.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		songs.get(index-1).play();

	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public void playPodcast(int index, int season, int episode)
	{

	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public void printPodcastEpisodes(int index, int season)
	{

	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		//Checks if the index and chapter are valid and then selects the appropriate chapter and plays the audiobook specified by the index.
		if(index < audiobooks.size()+1 && index > 0 && chapter < audiobooks.get(index-1).getNumberOfChapters()+1 && chapter > 0) {
			audiobooks.get(index-1).selectChapter(chapter);
			audiobooks.get(index-1).play();
		} else {
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if(index < audiobooks.size()+1 && index > 0){
			audiobooks.get(index-1).printTOC();
		} else {
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title);
		if(!playlists.contains(newPlaylist)){
			playlists.add(newPlaylist);
		} else {
			throw new AudioContentAlreadyExistsException("Playlist " + title + " Already Exists");
		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title);
		//Checks if the title of the playlist exists in the list of playlists
		if(playlists.contains(newPlaylist)){
			//Goes through the content of the playlist and prints each one out
			playlists.get(playlists.indexOf(newPlaylist)).printContents();
		} else {
			throw new AudioContentNotFoundException("Playlist Not Found");
		}
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		for(int i = 0; i < playlists.size(); i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){
				playlists.get(i).playAll();
				break;
			}
		}
		throw new AudioContentNotFoundException("Playlist Not Found");
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		System.out.println(playlistTitle);
		for(int i = 0; i < playlists.size(); i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){
				playlists.get(i).play(indexInPL-1);
				break;
			}
		}
		throw new AudioContentNotFoundException("Playlist Not Found");
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		Playlist newPlaylist = new Playlist(playlistTitle);
		//Checks if the playlistTitle exists in the playlists array
		if(playlists.contains(newPlaylist)){
			//Checks if the type is a song and the index is valid
			if(Song.TYPENAME.equalsIgnoreCase(type) && index > 0 && index < songs.size() + 1){
				//Loops through the playlist
				for(int i = 0; i < playlists.get(playlists.indexOf(newPlaylist)).getContent().size(); i++){
					//Checks if the selected playlist content is a song and if that song matches with the song that the user requests to add
					if(playlists.get(playlists.indexOf(newPlaylist)).getContent().get(i).getType().equalsIgnoreCase(type) && playlists.get(playlists.indexOf(newPlaylist)).getContent().get(i).equals(songs.get(index - 1))){
						throw new AudioContentAlreadyExistsException("Song Already Exists In Playlist");
					}
				}
				//Adds the song to the playlist
				playlists.get(playlists.indexOf(newPlaylist)).addContent(songs.get(index - 1));
			//Checks if the type is an audiobook and the index is valid
			} else if(AudioBook.TYPENAME.equalsIgnoreCase(type) && index > 0 && index < audiobooks.size() + 1){
				//Loops through the playlist
				for(int i = 0; i < playlists.get(playlists.indexOf(newPlaylist)).getContent().size(); i++){
					//Checks if the selected playlist content is an audiobook and if that audiobook matches with the audiobook that the user requests to add
					if(playlists.get(playlists.indexOf(newPlaylist)).getContent().get(i).getType().equalsIgnoreCase(type) && playlists.get(playlists.indexOf(newPlaylist)).getContent().get(i).equals(audiobooks.get(index - 1))){
						throw new AudioContentAlreadyExistsException("AudioBook Already Exists In Playlist");
					}
				}
				//Adds the audiobook to the playlist
				playlists.get(playlists.indexOf(newPlaylist)).addContent(audiobooks.get(index - 1));
			} else{
				throw new InvalidTypeException("Invalid Type");
			}
		} else {
			throw new AudioContentNotFoundException("Playlist Not Found");
		}

	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		Playlist newPlaylist = new Playlist(title);
		if(playlists.contains(newPlaylist)){
			if(playlists.get(playlists.indexOf(newPlaylist)).contains(index)){
				playlists.get(playlists.indexOf(newPlaylist)).deleteContent(index);
			} else {
				throw new AudioContentNotFoundException("Content Not Found");
			}
		} else {
			throw new AudioContentNotFoundException("Playlist Not Found");
		}
	}
	
}

class AudioContentNotFoundException extends RuntimeException
{
	public AudioContentNotFoundException(){}

	public AudioContentNotFoundException(String message)
	{
		super(message);
	}
}

class AudioContentAlreadyExistsException extends RuntimeException
{
	public AudioContentAlreadyExistsException(){}

	public AudioContentAlreadyExistsException(String message){super(message);}
}

class InvalidTypeException extends RuntimeException
{
	public InvalidTypeException(){}

	public InvalidTypeException(String message){super(message);}
}