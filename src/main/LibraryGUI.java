/**
 *  This class represents a Library Interface where you can add and remove books, while also being able to show the users in the system and 
 *  the book in the catalog. 
 */
package main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class LibraryGUI{
	/**
	 * This statement declares the variable libraryCatalog to be an instance of the class LibraryCatalog so we have access to its methods and variables.
	 */
    static LibraryCatalog libraryCatalog;
    /**
     * This method is what makes the application run.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> GUI());
    }
    /**
     * This method initiates the variable libraryCatalog.
     */
    public static void GUI(){
        try{
			libraryCatalog = new LibraryCatalog();
		}
        catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /**
         * This statements make sure to give title to the GUI and adjust sizes and backgrounds of the respective panel.
         */
        JFrame frame = new JFrame("Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(Color.BLACK);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed we ask the user for the necessary
         * attributes a book has so we can add it. 
         */
        JButton addBookToCatalog = new JButton("Add Book to our  Catalog");  
        addBookToCatalog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(frame, "What is the title for the book? ");
                String author = JOptionPane.showInputDialog(frame, "What is the author for the book? ");
                String genre = JOptionPane.showInputDialog(frame, "What is the genre for the book? ");
                if(title != null && author != null && genre != null) {
                    libraryCatalog.addBook(title, author, genre);
                    JOptionPane.showMessageDialog(frame, "Just added the book " + title.toUpperCase() + " BY " + author.toUpperCase());
                    JOptionPane.showMessageDialog(frame, "Thank You!");
                }
            }
        });
        panel.add(addBookToCatalog);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed go through the Users list displaying.
         * Every User present in it. It does this by using a for each for loop. Adding them to a string and at the end displaying that string.  
         */
        JButton displayCurrentUsers = new JButton("Display Users in system");
        displayCurrentUsers.addActionListener(new ActionListener() {
        	StringBuilder UsersInSystem = new StringBuilder();
        	public void actionPerformed(ActionEvent e){
        		for(User user : libraryCatalog.getUsers()){
        			UsersInSystem.append(user.getName().toString()).append("\n");
                }
                JTextArea textArea = new JTextArea(50, 50);
                textArea.setEditable(false);
                textArea.setText(UsersInSystem.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(frame, scrollPane, "Users in System", JOptionPane.PLAIN_MESSAGE);
        	}   	
        });
        panel.add(displayCurrentUsers);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed we remove the book that the User inputed the ID.
         * We do this by making use of our remove function.  
         */
        JButton removeBookFromCatalog = new JButton("Remove Book from our Catalog");
        removeBookFromCatalog.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String data = JOptionPane.showInputDialog(frame, "What is the Identifier of the book?");
                if(data != null) {
                    try{
                        int bookId = Integer.parseInt(data);
                        int size =  libraryCatalog.getBookCatalog().size();
                        libraryCatalog.removeBook(bookId);
                        if(size != libraryCatalog.getBookCatalog().size()) {
                            JOptionPane.showMessageDialog(frame, "Book was able to be removed");
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "Book doesn't exist");
                        }
                    } catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(frame, "Book Identifier not valid, please verify format.");
                    }
                }
            }
        });  
        panel.add(removeBookFromCatalog);
        /**
         * In this lines we create a method and a button, this method checks if a button was pressed. If the button was pressed go through the Catalog list displaying the 
         * Every books present in it. It does this by using a for each for loop. Adding them to a string and at the end displaying that string.  
         */
        JButton displayCatalog = new JButton("Display Catalog");
        displayCatalog.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StringBuilder BookInCatalog = new StringBuilder();
                for(Book book : libraryCatalog.getBookCatalog()){
                	BookInCatalog.append(book.toString()).append("\n");
                }
                JTextArea textArea = new JTextArea(50, 50);
                textArea.setEditable(false);
                textArea.setText(BookInCatalog.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(frame, scrollPane, "Library Catalog", JOptionPane.PLAIN_MESSAGE);
            }
        });
        panel.add(displayCatalog);
        /**
         * This lines are to correctly display the panel and buttons. 
         */ 
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
