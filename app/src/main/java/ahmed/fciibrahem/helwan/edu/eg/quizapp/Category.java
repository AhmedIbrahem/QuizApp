package ahmed.fciibrahem.helwan.edu.eg.quizapp;

public class Category {

    public static final int programing=1;
    public static final int Math=2;
    public static final int Since=3;
    public static final int thortical=4;


    private int id;
    private String name;
   public Category(){};

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
