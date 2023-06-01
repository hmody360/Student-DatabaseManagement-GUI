
//This class was made to assign each column a value (ID,FullName,DOB,GPA) and makes objects for each row(record) in the table
public class Student {
    int id;
    String name;
    String DOB;
    float GPA;
    
    //constractor and give initial values
    public Student(){
        id = 0;
        name = "";
        DOB = "";
        GPA = 0;
    }
    
    //constractor that take valuse
    public Student(int id, String name, String DOB, float GPA) {
        this.id = id;
        this.name = name;
        this.DOB = DOB;
        this.GPA = GPA;
    }
    
    
    // getters and setters
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }


}

