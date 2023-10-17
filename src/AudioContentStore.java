import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
	private ArrayList<AudioContent> contents;
	private HashMap<String, Integer> titleHashMap;
	private HashMap<String, ArrayList<Integer>> artistHashMap;
	private HashMap<String, ArrayList<Integer>> genreHashMap;

	public AudioContentStore() {
		contents = new ArrayList<AudioContent>();
		titleHashMap = new HashMap<String, Integer>();
		artistHashMap = new HashMap<String, ArrayList<Integer>>();
		genreHashMap = new HashMap<String, ArrayList<Integer>>();

		try{
			readFileInformation();
		} catch(IOException exception){
			System.out.println(exception.getMessage());
			System.exit(1);
		}

	}

	private ArrayList<AudioContent> readFileInformation() throws FileNotFoundException {

		File contentFileInformation = new File("store.txt");
		Scanner contentScannerInformation = new Scanner(contentFileInformation);

		while(contentScannerInformation.hasNextLine()){

			//Declares and Initializes all the string variables needed to create a SONG object or AUDIOBOOK object
			String typeOfContent = contentScannerInformation.nextLine(), title = "", id = "", type = "", artist = "", composer = "", file = "", author = "", narrator = "";
			//Declares all the integer variables needed to create a SONG object or AUDIOBOOK object
			int year, length, songLines, numberOfChapters, numberOfChapterLines;
			//Declares a genre variable needed to create a SONG object
			Song.Genre genre;
			//Declares and Initializes all the arraylists (of strings) that are needed to create an AUDIOBOOK object
			ArrayList<String> chapterTitles = new ArrayList<String>();
			ArrayList<String> chapters = new ArrayList<String>();

			//Checks to see if the content is a SONG
			if(typeOfContent.equals("SONG")){

				//Assigns all variables that are needed to create a SONG object with values from the file using a scanner
				type = Song.TYPENAME;
				id = contentScannerInformation.nextLine();
				title = contentScannerInformation.nextLine();
				year = Integer.parseInt(contentScannerInformation.nextLine());
				length = Integer.parseInt(contentScannerInformation.nextLine());
				artist = contentScannerInformation.nextLine();
				composer = contentScannerInformation.nextLine();
				genre = Song.Genre.valueOf(contentScannerInformation.nextLine());
				songLines = Integer.parseInt(contentScannerInformation.nextLine());

				//Loops through the lyrics of the song in the file using a scanner
				for(int i = 0; i < songLines; i++){
					file += contentScannerInformation.nextLine() + "\n";

				}

				//Creates a SONG object and adds it to the contents arraylist
				contents.add(new Song(title, year, id, type, file, length, artist, composer, genre, file));
			//Checks to see if the content is an AUDIOBOOK
			} else if(typeOfContent.equals("AUDIOBOOK")){
				//Assigns all variables that are needed to create an AUDIOBOOK object with values from the file using a scanner
				type = AudioBook.TYPENAME;
				id = contentScannerInformation.nextLine();
				title = contentScannerInformation.nextLine();
				year = Integer.parseInt(contentScannerInformation.nextLine());
				length = Integer.parseInt(contentScannerInformation.nextLine());
				author = contentScannerInformation.nextLine();
				narrator = contentScannerInformation.nextLine();
				numberOfChapters = Integer.parseInt(contentScannerInformation.nextLine());

				//Loops through all the chapter titles in the file using a scanner
				for(int i = 0; i < numberOfChapters; i++){
					//Adds the chapter title to the arraylist
					chapterTitles.add(contentScannerInformation.nextLine());
				}

				//Loops through the chapter titles in the arraylist
				for(int i = 0; i < chapterTitles.size(); i++){
					//Obtains the number of chapters using a scanner
					numberOfChapterLines = Integer.parseInt(contentScannerInformation.nextLine());
					//Loops through the chapters in the file using a scanner
					for(int j = 0; j < numberOfChapterLines; j++){
						chapters.add(contentScannerInformation.nextLine() + "\r\n");
					}
				}

				//Adds the AUDIOBOOK to the contents arraylist
				contents.add(new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters));
			}
		}

		//Closes the scanner
		contentScannerInformation.close();

		//Gets the content titles from the contents arraylist and adds it to the title map
		for(int i = 0; i < contents.size(); i++){
			titleHashMap.put(contents.get(i).getTitle(), i);
		}

		ArrayList<Integer> artistIntegers = new ArrayList<Integer>();

		//Loops through the contents arraylist
		for(int i = 0; i < contents.size(); i++){
			//Checks if the content is a SONG
			if(contents.get(i).getType().equals(Song.TYPENAME)) {
				Song song1 = (Song) contents.get(i);
				//Checks to see if the artist already exists in the map
				if (!artistHashMap.containsKey(song1.getArtist())) {
					//Loops through the contents arraylist
					for (int j = 0; j < contents.size(); j++) {
						//Checks if the content is a SONG
						if (contents.get(j).getType().equals(Song.TYPENAME)) {
							Song song2 = (Song) contents.get(j);
							//If the artists of both SONGS match add the content integer location to the arraylist
							if (song1.getArtist().equals(song2.getArtist())) {
								artistIntegers.add(j);
							}
						}
					}

					//Adds the artist and arraylist to the map
					artistHashMap.put(song1.getArtist(), new ArrayList<>(artistIntegers));
					//Clears the arraylist of integers
					artistIntegers = new ArrayList<>();

				}
			//Checks if the content is a SONG
			} else if(contents.get(i).getType().equals(AudioBook.TYPENAME)) {
				AudioBook audiobook1 = (AudioBook) contents.get(i);
				//Checks to see if the author already exists in the map
				if (!artistHashMap.containsKey(audiobook1.getAuthor())) {
					//Loops through the contents arraylist
					for (int j = 0; j < contents.size(); j++) {
						//Checks if the content is an AUDIOBOOK
						if (contents.get(j).getType().equals(AudioBook.TYPENAME)) {
							AudioBook audiobook2 = (AudioBook) contents.get(j);
							//If the authors of both AUDIOBOOKS match add the content integer location to the arraylist
							if (audiobook1.getAuthor().equals(audiobook2.getAuthor())) {
								artistIntegers.add(j);
							}
						}
					}

					//Adds the artist and arraylist to the map
					artistHashMap.put(audiobook1.getAuthor(), new ArrayList<>(artistIntegers));
					//Clears the arraylist of integers
					artistIntegers = new ArrayList<>();

				}
			}
		}

		ArrayList<Integer> genreIntegers = new ArrayList<Integer>();

		//Loops through the contents arraylist
		for(int i = 0; i < contents.size(); i++){
			//Checks if the content is a SONG
			if(contents.get(i).getType().equals(Song.TYPENAME)){
				Song song1 = (Song)contents.get(i);
				//Checks to see if the genre already exists in the map
				if(!genreHashMap.containsKey(song1.getGenre().toString())) {
					//Loops through the contents arraylist
					for (int j = 0; j < contents.size(); j++) {
						//Checks if the content is a SONG
						if(contents.get(j).getType().equals(Song.TYPENAME)) {
							Song song2 = (Song) contents.get(j);
							//If the genres of both SONGS match add the content integer location to the arraylist
							if (song1.getGenre().equals(song2.getGenre())) {
								genreIntegers.add(j);
							}
						}
					}

					//Adds the genre and arraylist to the map
					genreHashMap.put(song1.getGenre().toString(), new ArrayList<>(genreIntegers));
					//Clears the arraylist of integers
					genreIntegers = new ArrayList<>();

				}
			}
		}

		return contents;

	}

	public AudioContent getContent(int index) {
		if (index < 1 || index > contents.size()) {
			return null;
		}
		return contents.get(index-1);
	}

	public void listAll() {
		for (int i = 0; i < contents.size(); i++) {
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	public void search(String title){
		//Checks to see if the title exists in the map of titles
		if(titleHashMap.get(title) == null){
			System.out.println("No matches for " + title);
		} else {
			System.out.print(titleHashMap.get(title)+1 + ". ");
			contents.get(titleHashMap.get(title)).printInfo();
		}
	}

	public void searcha(String artist){
		//Checks to see if the title exists in the map of artists
		if(artistHashMap.get(artist) == null){
			System.out.println("No matches for " + artist);
		} else {
			for(int i = 0; i < artistHashMap.get(artist).size(); i++){
				System.out.print((artistHashMap.get(artist).get(i) +1)+ ". ");
				contents.get(artistHashMap.get(artist).get(i)).printInfo();
				System.out.println();
			}
		}
	}

	public void searchg(String genre) {
		//Checks to see if the title exists in the map of genres
		if (genreHashMap.get(genre) == null) {
			System.out.println("No matches for " + genre);
		} else {
			for (int i = 0; i < genreHashMap.get(genre).size(); i++) {
				System.out.print((genreHashMap.get(genre).get(i) + 1) + ". ");
				contents.get(genreHashMap.get(genre).get(i)).printInfo();
				System.out.println();
			}
		}
	}

	public ArrayList<Integer> downloada(String artist){
		//Returns the map of artists
		return artistHashMap.get(artist);
	}

	public ArrayList<Integer> downloadg(String genre){
		//Returns the map of genres
		return genreHashMap.get(genre);
	}

	/*
		private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		// Podcast Seasons
		// "/*" was here
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
