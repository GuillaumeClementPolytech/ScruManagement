package business.system;

public class Projet {

    int id;
    private String name;

    public int getId(){return this.id;}
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}