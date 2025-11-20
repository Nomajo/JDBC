package at.nomajo.daoStudent.domain;

import at.nomajo.dao.domain.InvalidValueException;

import java.sql.Date;

public class Student extends BaseEntity {
    private String firstName;
    private String lastName;
    private Date birthdate;

    public Student(Long id, String firstname, String lastName, Date birthdate) throws InvalidValueException {
        super(id);
        setLastName(lastName);
        setFirstName(firstname);
        setBirthdate(birthdate);
    }

    public Student(String vorname, String lastName, Date birthdate) throws InvalidValueException {
        this(null, vorname, lastName, birthdate);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && firstName.length() > 1) {
            this.firstName = firstName;
        } else {
            throw new InvalidValueException("Vorname muss mindestens 2 Zeichen lang sein!");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && lastName.length() > 1) {
            this.lastName = lastName;
        } else {
            throw new InvalidValueException("Nachname muss mindestens 2 Zeichen lang sein!");
        }
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        if(birthdate != null && birthdate.before(new Date(System.currentTimeMillis()))) {
            this.birthdate = birthdate;
        } else {
            throw new InvalidValueException("Geburtsdatum darf nicht null oder heute sein!");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + this.getId() + '\'' +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
