/**
 * This Book class represents an object book formed by Id, Title, Author, Genre, Checkout Date, and availability. This object is part of a library system
 */
package main;
import java.time.LocalDate;

public class Book {
    private String Title;
    private int Id;
    private String Author;
    private String Genre;
    private LocalDate LastCheckOut;
    private Boolean CheckedOut;
    /**
     * Constructor for a Book object.
     *
     * @param Id           Identifier of the book.
     * @param Title        Title of the book.
     * @param Author       Author of the book.
     * @param Genre        Genre of the book.
     * @param LastCheckOut Date when the book was last checked out.
     * @param CheckedOut   Book availability (taken or ready to be taken).
     */
    public Book(int Id, String Title, String Author, String Genre, LocalDate LastCheckOut, Boolean CheckedOut) {
        this.Id = Id;
        this.Title = Title;
        this.Author = Author;
        this.Genre = Genre;
        this.LastCheckOut = LastCheckOut;
        this.CheckedOut = CheckedOut;
    }
    /**
     * Get the unique identifier of the book.
     *
     * @return Identifier of the book.
     */
    public int getId() {
        return this.Id;
    }
    /**
     * Change the desired book identifier.
     *
     * @param id New identifier for the specified book Id.
     */
    public void setId(int id) {
        this.Id = id;
    }
    /**
     * Get the title of the book.
     *
     * @return Title of the Book.
     */
    public String getTitle() {
        return this.Title;
    }
    /**
     * Change the desired book title.
     *
     * @param title New title for the book.
     */
    public void setTitle(String title) {
        this.Title = title;
    }
    /**
     * Get the author of the book.
     *
     * @return Author of the Book.
     */
    public String getAuthor() {
        return Author;
    }
    /**
     * Change the desired book author.
     *
     * @param author New Author for the book.
     */
    public void setAuthor(String author) {
        this.Author = author;
    }
    /**
     * Get the genre of the book.
     *
     * @return Genre of the Book.
     */
    public String getGenre() {
        return Genre;
    }
    /**
     * Change the desired book genre.
     *
     * @param genre New Genre for the book.
     */
    public void setGenre(String genre) {
        this.Genre = genre;
    }
    /**
     * Get the last date when the book was checked out.
     *
     * @return Date when the book was last checked out.
     */
    public LocalDate getLastCheckOut() {
        return LastCheckOut;
    }
    /**
     * Change the desired book checkout date.
     *
     * @param lastCheckOut New checkout date for the book.
     */
    public void setLastCheckOut(LocalDate lastCheckOut) {
        this.LastCheckOut = lastCheckOut;
    }
    /**
     * Get the book's availability.
     *
     * @return Book's availability.
     */
    public boolean isCheckedOut() {
        return CheckedOut;
    }
    /**
     * Update the book's availability.
     *
     * @param checkedOut Book's new availability.
     */
    public void setCheckedOut(boolean checkedOut) {
        this.CheckedOut = checkedOut;
    }
    /**
     * Method that Overrides toString() so that we can get the desired output behavior when printing the Book object.
     *
     * @return Book's title followed by who wrote it. Following this format {TITLE} By {AUTHOR}.
     */
    @Override
    public String toString() {
        /*
         * This is supposed to follow the format
         *
         * {TITLE} By {AUTHOR}
         *
         * Both the title and author are in uppercase.
         */
        return getTitle().toUpperCase() + " BY " + getAuthor().toUpperCase();
    }
    /**
     * Method that calculates the fee based on the number of days over the 31 day mark with a base fee of 10 and adding 1.50 per day over 31. All this if the book is checked out.
     *
     * @return float value indicating the amount of money owed to the library based on the amount of overdue days and a base fee of 10.
     */
    public float calculateFees() {
        float fee = 0;
        if (CheckedOut) {
            LocalDate checkOutDate = getLastCheckOut();
            LocalDate currentDate = LocalDate.of(2023, 9, 15);
            float days = (float) checkOutDate.datesUntil(currentDate).count();
            if (days == 31) {
                fee = 10;
            } else if (days > 31) {
                fee = (float) (10 + (1.50 * (days - 31)));
            }
        }
        return fee;
    }
}
