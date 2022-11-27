package Models.Entities;

import java.util.Objects;

public abstract class Human {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private String nationality;

    Human() {
        id = -1;
        age = -1;
    }

    Human(int id, String name, String surname, String patronymic, int age, String nationality) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.age = age;
        this.nationality = nationality;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSurname() { return this.surname; }
    public String getPatronymic() { return this.patronymic; }
    public int getAge() { return this.age; }
    public String getNationality() { return this.nationality; }

    @Override
    public String toString() {
        return surname + " " + name + " " + ((patronymic != null) ? patronymic : "");
    }
}
