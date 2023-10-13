/**
 *  This class represents a Library Interface where you can add and remove books, while also being able to show the users in the system and 
 *  the book in the catalog. 
 */
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import interfaces.List;

public class LibraryGUI{
	/**
	 * This statement declares the variable libraryCatalog to be an instance of the class LibraryCatalog so we have access to its methods and variables. It also initiates
	 * variables of frequent use in the class.
	 */
    static LibraryCatalog lc;
    static JTextArea textArea;
    static JScrollPane scrollPane;
    /**
     * This method is what makes the application run.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> GUI());      
    }
    /**
     * 
     * @param s String to be checked if is a valid number
     * @return
     */
    public static void GUI(){
    	/**
    	 * This statements make sure to give title to the GUI and adjust sizes and backgrounds of the respective panel. It also makes sure to 
    	 * close the GUI on clicking exit. It also displays a welcome message so the user know what can be done in the GUI.
         */
    	 JFrame frame = new JFrame();
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         JPanel panel = new JPanel();
         panel.setPreferredSize(new Dimension(400, 400));
         panel.setBackground(Color.BLUE);
         JOptionPane.showMessageDialog(frame, "Hello, welcome to our library! Here you will have acess to our list of books and current users. You will also be able to remove and add books. Enjoy your stay");
         /**
          * This statement initiates the variable libraryCatalog. Calling lc would give us access to all
          * LibraryCatalog public methods and variables.
          */
        try{
			  lc = new LibraryCatalog();
		}
        catch (IOException e){
        	// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /**
         * Create all the buttons for all the methods. 
         */
        JButton addBookToCatalog = new JButton("Add Book to our  Catalog");
        JButton displayCurrentUsers = new JButton("Display Users in system");
        JButton removeBookFromCatalog = new JButton("Remove Book from our Catalog");
        JButton displayCatalog = new JButton("Display Catalog");
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed we ask the user for the necessary
         * attributes a book has so we can add it. If null value received display error message.
         */          
        addBookToCatalog.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String inputTitle = JOptionPane.showInputDialog(frame, "What is the title for the book? ");
                String inputAuthor = JOptionPane.showInputDialog(frame, "What is the author for the book? ");
                String inputGenre = JOptionPane.showInputDialog(frame, "What is the genre for the book? ");
                if(inputTitle != null && inputAuthor != null && inputGenre != null) {
                	if(inputTitle.length() != 0 && inputAuthor.length() != 0 && inputGenre.length() != 0){
                		lc.addBook(inputTitle, inputAuthor, inputGenre);
                		JOptionPane.showMessageDialog(frame, "Just added the book " + lc.getBookCatalog().last().toString());
                		JOptionPane.showMessageDialog(frame, "Thank You!");
                	}
                	else {
                		JOptionPane.showMessageDialog(frame, "Input was not valid");
                	}
                }
                else {
                	JOptionPane.showMessageDialog(frame, "Null input received, input a valid value"); 
                }
            }
        });
        /**
         * Add the button to the panel.
         */
        panel.add(addBookToCatalog);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed go through the Users list displaying.
         * Every User present in it. It does this by using a for loop. Adding them to a string and at the end displaying that string.  
         */        
        displayCurrentUsers.addActionListener(new ActionListener(){
        	String UsersInSystem = "";
        	List<User> userList = lc.getUsers();
        	public void actionPerformed(ActionEvent e){
        		for(int i = 0; i < userList.size(); i ++){
        			UsersInSystem += userList.get(i).getName() + "\n" ;
                }
                textArea = new JTextArea(50, 50);
                textArea.setText(UsersInSystem);
                scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(frame, scrollPane, "Users in System", JOptionPane.PLAIN_MESSAGE);
        	}   	
        });
        /**
         * Add the button to the panel.
         */
        panel.add(displayCurrentUsers);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed we remove the book that the User inputed the ID.
         * We do this by making use of our remove function. Then  display if the book was able to be removed based on the  size change. If a letter was inputed give an error message.
         * If null value received display error message.
         */       
        removeBookFromCatalog.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String data = JOptionPane.showInputDialog(frame, "What is the Identifier of the book?");
                if(data != null) {
	                if(data.length() != 0){
	                	try {  
		                        int bookId = Integer.parseInt(data);
		                        int prevSize = lc.getBookCatalog().size();
		                        lc.removeBook(bookId);
		                        if(prevSize == lc.getBookCatalog().size()){
		                        	JOptionPane.showMessageDialog(frame, "Book doesn't exist");
		                        }
		                        else{
		                            JOptionPane.showMessageDialog(frame, "Book was able to be removed");
		                        }
		                	}
		                catch(NumberFormatException ex){
		                        JOptionPane.showMessageDialog(frame, "Book Identifier not valid, please verify format.");
		                	}
	                }
                }
                else {
                	 JOptionPane.showMessageDialog(frame, "Null input received, input a valid value");               	
                }
            }
        });
        /**
         * Add the button to the panel.
         */
        panel.add(removeBookFromCatalog);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed go through the Catalog list displaying the 
         * Every books present in it. It does this by using a for loop. Adding them to a string and at the end displaying that string.  
         */       
        displayCatalog.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	String BookInSystem = "";
            	List<Book> bookList = lc.getBookCatalog();
            		for(int i = 0; i < bookList.size(); i ++){
            			BookInSystem += "(ID: " + bookList.get(i).getId()+ ") Book: "+ bookList.get(i).toString() + "\n";
                    }
                textArea = new JTextArea(50, 50);
                textArea.setText(BookInSystem);
                scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(frame, scrollPane, "Library Catalog", JOptionPane.PLAIN_MESSAGE);
            }
        });
        /**
         * Add the button to the panel.
         */
        panel.add(displayCatalog);
        /**
         * This lines are to correctly display the panel and buttons. 
         */ 
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
