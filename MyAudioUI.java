import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
Name: Dev Patel
Student ID: 501174911
*/

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();

		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals(""))
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll();
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs();
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks();
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts();
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists();
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists();
			}
			// Download audiocontent (song/audiobook/podcast) from the store
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD"))
			{
				//Gets the first index using a scanner
				System.out.print("From Store Content #: ");
				Scanner scannerIndex1 = new Scanner(System.in);
				int index1 = scannerIndex1.nextInt();
				scannerIndex1.nextLine();

				//Gets the second index using a scanner
				System.out.print("To Store Content #: ");
				Scanner scannerIndex2 = new Scanner(System.in);
				int index2 = scannerIndex2.nextInt();
				scannerIndex2.nextLine();

				//Loops through all numbers between (and including) the first index and second index
				for(int i = index1; i < index2+1; i++) {
					AudioContent content = store.getContent(i);
					if (content == null)
						System.out.println("Content Not Found in Store");
					else{
						try {
							mylibrary.download(content);
						} catch(AudioContentAlreadyExistsException exception){
							System.out.println(exception.getMessage());
						}
					}
				}
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song
			else if (action.equalsIgnoreCase("PLAYSONG"))
			{
				//Gets the index using a scanner
				Scanner index = new Scanner(System.in);
				System.out.print("Song Number: ");
				int intIndex = index.nextInt();

				try {
					mylibrary.playSong(intIndex);
				}
				catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}

			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC"))
			{
				//Gets the index using a scanner
				System.out.print("Audio Book Number: ");
				Scanner index = new Scanner(System.in);
				int bookIndex = index.nextInt();

				try {
					mylibrary.printAudioBookTOC(bookIndex);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK"))
			{
				//Gets the index using a scanner
				System.out.print("Audio Book Number: ");
				Scanner index = new Scanner(System.in);
				int bookIndex = index.nextInt();

				//Gets the chapter using a scanner
				System.out.print("Chapter: ");
				Scanner chapter = new Scanner(System.in);
				int bookChapter = chapter.nextInt();

				try {
					mylibrary.playAudioBook(bookIndex, bookChapter);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}

			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts,
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC"))
			{

			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts,
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD"))
			{

			}
			// Specify a playlist title (string)
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL"))
			{
				//Gets the title using a scanner
				System.out.print("Playlist Title: ");
				Scanner title = new Scanner(System.in);
				String playlistTitle = title.nextLine();

				try{
					mylibrary.playPlaylist(playlistTitle);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}

			}
			// Specify a playlist title (string)
			// Read the index of a song/audiobook/podcast in the playist from the keyboard
			// Play all the audio content
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL"))
			{
				//Gets the title using a scanner
				System.out.print("Playlist Title: ");
				Scanner title = new Scanner(System.in);
				String playlistTitle = title.nextLine();

				//Gets the index using a scanner
				System.out.print("Content Number: ");
				Scanner index = new Scanner(System.in);
				int contentIndex = index.nextInt();


				try {
					mylibrary.playPlaylist(playlistTitle, contentIndex);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG"))
			{
				//Gets the index using a scanner
				System.out.print("Library Song #: ");
				Scanner songIndex = new Scanner(System.in);
				int index = songIndex.nextInt();

				try {
					mylibrary.deleteSong(index);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL"))
			{
				//Gets the title using a scanner
				Scanner title = new Scanner(System.in);
				System.out.print("Playlist Title: ");

				try {
					mylibrary.makePlaylist(title.next());
				} catch(AudioContentAlreadyExistsException exception){
					System.out.println(exception.getMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				//Gets the title using a scanner
				Scanner title = new Scanner(System.in);
				System.out.print("Playlist Title: ");

				try{
					mylibrary.printPlaylist(title.nextLine());
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL"))
			{
				//Gets the title using a scanner
				System.out.print("Playlist Title: ");
				Scanner title = new Scanner(System.in);
				String playlistTitle = title.nextLine();

				//Gets the content type using a scanner
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				Scanner type = new Scanner(System.in);
				String contentType = type.nextLine();

				//Gets the index using a scanner
				System.out.print("Library Content #: ");
				Scanner index = new Scanner(System.in);
				int contentIndex = index.nextInt();

				try{
					mylibrary.addContentToPlaylist(contentType, contentIndex, playlistTitle);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				} catch(AudioContentAlreadyExistsException exception){
					System.out.println(exception.getMessage());
				} catch (InvalidTypeException exception){
					System.out.println(exception.getMessage());
				}

			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL"))
			{
				//Gets the title using a scanner
				System.out.print("Playlist Title: ");
				Scanner title = new Scanner(System.in);
				String playlistTitle = title.nextLine();

				//Gets the index using a scanner
				System.out.print("Playlist Content #: ");
				Scanner index = new Scanner(System.in);
				int contentIndex = index.nextInt();

				try{
					mylibrary.delContentFromPlaylist(contentIndex, playlistTitle);
				} catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}
			else if (action.equalsIgnoreCase("SEARCH")) // sort songs by length
			{
				//Gets the title using a scanner
				System.out.print("Title: ");
				Scanner title = new Scanner(System.in);
				store.search(title.nextLine());
			}
			else if (action.equalsIgnoreCase("SEARCHA")) // sort songs by length
			{
				//Gets the artist using a scanner
				System.out.print("Artist: ");
				Scanner artist = new Scanner(System.in);
				store.searcha(artist.nextLine());
			}
			else if (action.equalsIgnoreCase("SEARCHG")) // sort songs by length
			{
				//Gets the genre using a scanner
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				Scanner genre = new Scanner(System.in);
				store.searchg(genre.nextLine());
			}
			else if (action.equalsIgnoreCase("DOWNLOADA")) // sort songs by length
			{
				//Gets the artist name using a scanner
				System.out.print("Artist Name: ");
				Scanner artist = new Scanner(System.in);

				//Gets an arraylist that contains integers of songs with the specified artist name with respect to the content arraylist
				ArrayList<Integer> artistContentIntegers = store.downloada(artist.nextLine());

				//Loops through the arraylist of integers
				for(int i = 0; i < artistContentIntegers.size(); i++){
					try{
						mylibrary.download(store.getContent(artistContentIntegers.get(i)+1));
					}catch(AudioContentAlreadyExistsException exception){
						System.out.println(exception.getMessage());
					}
				}
			}
			else if (action.equalsIgnoreCase("DOWNLOADG")) // sort songs by length
			{
				//Gets the genre using a scanner
				System.out.print("Genre: ");
				Scanner genre = new Scanner(System.in);

				//Gets an arraylist that contains integers of songs with the specified genre name with respect to the content arraylist
				ArrayList<Integer> genreContentIntegers = store.downloadg(genre.nextLine());

				//Loops through the arraylist of integers
				for(int i = 0; i < genreContentIntegers.size(); i++){
					try{
						mylibrary.download(store.getContent(genreContentIntegers.get(i)+1));
					}catch(AudioContentAlreadyExistsException exception){
						System.out.println(exception.getMessage());
					}
				}
			}

			System.out.print("\n>");
		}
	}
}
