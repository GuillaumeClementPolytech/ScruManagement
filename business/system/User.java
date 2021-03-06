package business.system;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String passWord;

    public User(int id) {
        this.id = id;
    }

    public User(int id, String firstName, String lastName, String mail) {
        this.id = id;
        this.email = mail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }

    public String getPassWord() {
        return this.passWord;
    }

    /**
     * @param oldPassWord
     * @param newPassWord
     */
    public void setPassWord(String oldPassWord, String newPassWord) {
        // TODO - implement business.User.setPassWord
        throw new UnsupportedOperationException();
    }

    public void resetPassWord() {
        // TODO - implement business.User.resetPassWord
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object user) {
        if (!(user instanceof User)) {
            return false;
        }
        User otherUser = (User) user;
        if (this.getId() == otherUser.getId()) {
            return true;
        }
        return false;
    }
}