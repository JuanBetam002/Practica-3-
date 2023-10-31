public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String careerAspiration;
    private double mathScore;

    public Student(int id, String firstName, String lastName, String gender, String careerAspiration, double mathScore) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.careerAspiration = careerAspiration;
        this.mathScore = mathScore;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getCareerAspiration() {
        return careerAspiration;
    }

    public double getMathScore() {
        return mathScore;
    }
}
