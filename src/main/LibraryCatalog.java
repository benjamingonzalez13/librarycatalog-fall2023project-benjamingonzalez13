/**
 * This class represents a library department. In this class there is two ArrayList representing the Users(Users) and the Books(Catalog).
 * This class contain a variety of methods that perform different operations on both the ArrayLists to get some desired results.
 */
package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
	/**
	 *	This class has 3 main fields two ArrayList and a date. The ArrayList are used to store the Users and the Books because of the advantages of knowing the positions for the elements.
	 *	ArrayList are also efficient in this implementation because in this Library Department new books are added to the back. 
	 */
	private List<Book> Catalog = new ArrayList<>();
	private List<User> Users = new ArrayList<>();
	private LocalDate currentDate = LocalDate.of(2023, 9, 15);
	
	
	/**
	 * Constructor for Catalog and User ArrayList. Initialize them by calling functions getBooksFromFiles() and getUsersFromFiles().
	 * @throws IOException Handle case where improper data is loaded into program
	 */
	public LibraryCatalog() throws IOException {
		Catalog = getBooksFromFiles();
		Users = getUsersFromFiles();	
	}
	/**
	 * This method consist of using the BufferedReader class to read a .csv file so books can be created from this data.
	 * This method takes advantage of the commas separating the values, using the split function a list with each individual value of the line is created. A book is created by indexing the list
	 * obtained from the line and the book is added onto the Catalog.Important to skip the first lien since its the header. 
	 * 
	 * @return ArrayList filled with books created from extracted data from .csv file
	 * @throws IOException Handle case where improper data is loaded into program
	 */
	private List<Book> getBooksFromFiles() throws IOException {
		String line = "";
		String path = "data/catalog.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        	br.readLine();
            while ((line = br.readLine()) != null) {
                String[] catalog = line.split(",");
                Book book = new Book(
                    Integer.parseInt(catalog[0]),
                    catalog[1],
                    catalog[2],
                    catalog[3],
                    LocalDate.parse(catalog[4]),
                    Boolean.parseBoolean(catalog[5])
                );
                Catalog.add(book);
            }
        }
        return Catalog;
    }
	/**
	 * This method consist of using the BufferedReader class to read a .csv file so users can be created from this data.
	 * This method takes advantage of the commas separating the values, using the split function a list with each individual value of the line is created. A user is created by indexing the list
	 * obtained from the line if the length of the line list is less or equals to two it means the User don't have any book checked out. If he does have books checked out then 
	 * the list is taken and since it in a string the curly braces are removed and the Ids are added to the list.  Important to skip the first lien since its the header. 
	 * 
	 * @return ArrayList filled with Users created from extracted data from .csv file
	 * @throws IOException Handle case where improper data is loaded into program
	 */
	private List<User> getUsersFromFiles() throws IOException {
		String line = "";
		String path2 = "data/user.csv";
		try (BufferedReader br = new BufferedReader(new FileReader(path2))) {
			br.readLine();
            while ((line = br.readLine()) != null) {
            	List<Book> bookIds = new ArrayList<>();
                String[] users = line.split(",");
                if(users.length <= 2) {
                User user = new User( Integer.parseInt(users[0]),users[1],bookIds);
                Users.add(user);
                }
                else {
                	String[] Ids = users[2].substring(1, users[2].length() - 1).split(" ");
                	for(int i = 0; i < Ids.length; i++) {
                		if(getBook(Integer.parseInt(Ids[i])) != null) {
                			bookIds.add(getBook(Integer.parseInt(Ids[i])));
                		}	
                	}
                	User user = new User(Integer.parseInt(users[0]),users[1],bookIds);
                	Users.add(user);
                	
                }
                
            }
        }
        return Users;
    }
	/**
	 * Get Library's books
	 * 
	 * @return List of the books currently in the Library 
	 */
	public List<Book> getBookCatalog() {
		return Catalog;
	}
	/**
	 * Get Library's Users
	 * 
	 * @return List of the Users currently in the Library registry
	 */
	public List<User> getUsers() {
		return Users;
	}
	/**
	 * This method adds a book to the Library it creates a book with the parameters given and known variables.
	 * 
	 * @param title 	Title of book to be added
	 * @param author	Author of book to be added
	 * @param genre		Genre of book to be added
	 */
	public void addBook(String title, String author, String genre) {
		 Book book = new Book(Catalog.size() +1 , title, author, genre, currentDate, false);
		 Catalog.add(book);
		 return;
	}
	/**
	 * This method takes in the Identifier of a book and using a for loop to go thru Catalog removes it. If Identifier not found do a empty return.
	 * 
	 * @param id Identifier of book to be removed
	 */
	public void removeBook(int id) {
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).getId() == id){
				Catalog.remove(i);
			}
		}
		return;
	}
	/**
	 * This method uses a for loop to go thru Catalog and look for the matching Identifier, if found check if it hasn't been checked out. If it hasen't
	 * Then check it out, update the date to the current date, and return true. Else it has been checked out already or it doesn't exist return false.
	 * 
	 * @param id Identifier of book to be checked out
	 * @return True if book was able to be checked out or False if book was already checked out.
	 */
	
	public boolean checkOutBook(int id) {
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).getId() == id && !Catalog.get(i).isCheckedOut()){
				Catalog.get(i).setCheckedOut(true);
				Catalog.get(i).setLastCheckOut(currentDate);
				return true;
			}
		}
		return false;
	}
	/**
	 * This method uses a for loop to go thru Catalog and look for the matching Identifier, if found check if it has been checked out. If it has been
	 * Then change check out to false ,and return true. Else it hasn't been check out or it doesn't exist return false.
	 * 
	 * @param id Identifier of book to be returned 
	 * @return True if book was able to be returned or False if book was already returned.
	 */
	public boolean returnBook(int id) {
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).getId() == id && Catalog.get(i).isCheckedOut()){
				Catalog.get(i).setCheckedOut(false);
				return true;
			}
		}
		return false;
	}
	/**
	 * Method that uses a for loop to go thru the Catalog and look for a matching Identifier if found return the book, if not return null.
	 * 
	 * @param Id Identifier of book to be found
	 * @return	If found return the book or if not return null
	 */
	public Book getBook(int Id) {
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).getId() == Id){
				return Catalog.get(i);
			}
		}
		return null;
		
	}
	/**
	 * This method uses the the getBook(id) to check if book exist and to get if it does, after this it checks if it has been checked out. 
	 * 
	 * @param id Identifier of book to be checked if available
	 * @return True if book is available to be checked out or False if book doesn't exist or is already checked out
	 */
	public boolean getBookAvailability(int id){
		Book  book = getBook(id);
		if(book == null){
			return false;
		}
		else if(book.isCheckedOut()){
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * This method uses a for loop to go around Catalog and count how many book have an specific title.
	 * 
	 * @param title Title of book to be counted
	 * @return	How many books have that title
	 */
	public int bookCount(String title) {
		int count = 0;
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	/**
	 * This method uses a for loop to go around Catalog and count how many book have an specific genre.
	 * 
	 * @param genre Genre of book to be counted
	 * @return	How many books have that genre
	 */
	public int genreCount(String genre) {
		int count = 0;
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).getGenre().equals(genre)) {
				count++;
			}
		}
		return count;
	}
	/**
	 * This method uses a for loop to go around Catalog and count how many book have been checked out.
	 * 
	 * @return	How many books have been checked out
	 */
	public int checkedOutCount() {
		int count = 0;
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).isCheckedOut()) {
				count++;
			}
		}
		return count;
		
	}
	/**
	 * This method utilizes a variety of functions and the BufferedWriter to generate a report that indicates the amount of books of a specific genre. It does this by
	 * calling on the genreCount(genre) function. The report also displays how many book are currently check out, it does this by calling on the checkedOutCount() function, and finally
	 * the report also displays all the fees of the clients and how much is the library owed in total. It does this by using the List of books that the users have checked out and
	 * calculating their fees. 
	 * 
	 * @throws IOException Case in which there is a problem when writing the report file
	 */
	public void generateReport() throws IOException {
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + (genreCount("Adventure")) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (genreCount("Fiction")) + "\n";
		output += "Classics\t\t\t\t\t" + (genreCount("Classics")) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (genreCount("Mystery")) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (genreCount("Science Fiction")) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (Catalog.size()) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		
				
		
		for(int i = 0; i < Catalog.size(); i++) {
			if(Catalog.get(i).isCheckedOut()) {
				output += Catalog.get(i) + "\n";
			}
		}
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (checkedOutCount()) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		float totalOwed =0;
		for(int i = 0; i < Users.size(); i++) {
			float lateFees = 0;
			User user = Users.get(i);
			for(int j = 0; j < user.getCheckedOutList().size(); j++) {
				lateFees+= user.getCheckedOutList().get(j).calculateFees();
				totalOwed+= user.getCheckedOutList().get(j).calculateFees();
			}
			if(lateFees > 0) {
				output += String.format("%s\t\t\t\t\t$%.2f%n", user.getName(), (float) lateFees);
				
			}
		}

			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (totalOwed) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
		    writer.write(output);
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	/**
	 * This method takes as a parameter a lambda function that decides how are books going to be filtered
	 * 
	 * @param func A lambda function specifying what type of filter is going to take place
	 * @return	list of filtered books
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		List<Book> specialBooks = new ArrayList<>();
		for(int i = 0; i < Catalog.size(); i++) {
			if(func.filter(Catalog.get(i))) {
				specialBooks.add(Catalog.get(i));	
			}
		}
		return specialBooks;
	}
	/**
	 * This method takes as a parameter a lambda function that decides how are Users going to be filtered
	 * 
	 * @param func A lambda function specifying what type of filter is going to take place
	 * @return	list of filtered Users
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		List<User> specialUsers = new ArrayList<>();
		for(int i = 0; i < Users.size(); i++) {
			if(func.filter(Users.get(i))) {
				specialUsers.add(Users.get(i));	
			}
		}
		return specialUsers;
	}
	
}
