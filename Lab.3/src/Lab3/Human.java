package Lab3;

public abstract class Human {

    private String name;
    private int age;
    private String sex;

    protected Human()
    {
        this.name = " ";
        this.age = 0;
        this.sex = "man";
    }

    protected Human(String name, int age, String sex)
    {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String GetName() {return name;}

    public int GetAge() {return age;}

    public String GetSex() {return sex;}

    public void SetName(String name) {this.name = name;}

    public void SetAge(int age) {this.age = age;}

    public void SetSex(String sex) {this.sex = sex;}

    public String toString()
    {
        return name + "\t" + age + "\t" + sex;
    }

    public String toFileString()
    {
        return name + "\n" + age + "\n" + sex;
    }
}
