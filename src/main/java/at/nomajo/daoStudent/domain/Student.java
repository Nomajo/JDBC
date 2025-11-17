package at.nomajo.daoStudent.domain;

import at.nomajo.dao.domain.InvalidValueException;

import java.sql.Date;

public class Student extends BaseEntity {
    private String vorname;
    private String nachname;
    private Date geburtsdatum;

    public Student(Long id, String vorname, String nachname, Date geburtsdatum) throws InvalidValueException {
        super(id);
        setNachname(nachname);
        setVorname(vorname);
        setGeburtsdatum(geburtsdatum);
    }

    public Student(String vorname, String nachname, Date geburtsdatum) throws InvalidValueException {
        this(null, vorname, nachname, geburtsdatum);
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        if (vorname != null && vorname.length() > 1) {
            this.vorname = vorname;
        } else {
            throw new InvalidValueException("Vorname muss mindestens 2 Zeichen lang sein!");
        }
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        if (nachname != null && nachname.length() > 1) {
            this.nachname = nachname;
        } else {
            throw new InvalidValueException("Nachname muss mindestens 2 Zeichen lang sein!");
        }
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        if(geburtsdatum != null && geburtsdatum.before(new Date(System.currentTimeMillis()))) {
            this.geburtsdatum = geburtsdatum;
        } else {
            throw new InvalidValueException("Geburtsdatum darf nicht null oder heute sein!");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + this.getId() + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                '}';
    }
}
