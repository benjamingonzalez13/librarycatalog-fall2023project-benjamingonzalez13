/**
 * This User class represents an object User formed by Id, Name, and Books that they currently hold. This user is inside of the library registry
 */
package main;
import interfaces.List;

public class User {
    private int Id;
    private String Name;
    private List<Book> CheckedOutList;   
    /**
     * Constructor to initialize a User object.
     *
     * @param Id             Identifier of the user.
     * @param Name           Name of the user.
     * @param CheckedOutList List representing the books that they currently have checked out.
     */
    public User(int Id, String Name, List<Book> CheckedOutList) {
        this.Id = Id;
        this.Name = Name;
        this.CheckedOutList = CheckedOutList;
    }
    /**
     * Get the User's Identifier.
     *
     * @return Identifier of the user.
     */
    public int getId() {
        return Id;
    }
    /**
     * Update the User's Identifier.
     *
     * @param id New User's Identifier.
     */
    public void setId(int id) {
        this.Id = id;
    }
    /**
     * Get the User's Name.
     *
     * @return Name of the user.
     */
    public String getName() {
        return Name;
    }
    /**
     * Update the User's Name.
     *
     * @param name New User's Identifier.
     */
    public void setName(String name) {
        this.Name = name;
    }
    /**
     * Get the User's CheckedOutList.
     *
     * @return CheckedOutList of the user.
     * We use a list for these books because we can dynamically add and remove elements from it while taking advantage of knowing the positions of the elements.
     */
    public List<Book> getCheckedOutList() {
        return CheckedOutList;
    }
    /**
     * Update the User's CheckedOutList.
     *
     * @param checkedOutList New User's CheckedOutList.
     */
    public void setCheckedOutList(List<Book> checkedOutList) {
        this.CheckedOutList = checkedOutList;
    }
}
