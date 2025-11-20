package at.nomajo.daoStudent.ui;

import at.nomajo.daoStudent.dataaccess.DatabaseException;
import at.nomajo.daoStudent.dataaccess.MyStudentRepository;
import at.nomajo.daoStudent.domain.InvalidValueException;
import at.nomajo.daoStudent.domain.Student;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli {
    Scanner scan;
    MyStudentRepository repo;

    public Cli(MyStudentRepository repo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "-";
        while(!input.equals("x")) {
            showMenue();
            input = scan.nextLine();
            switch(input) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    showAllStudents();
                    break;
                case "3":
                    showStudentDetails();
                    break;
                case "4":
                    updateStudentDetails();
                    break;
                case "5":
                    deleteStudent();
                    break;
                case "6":
                    studentBirthdaySearch();
                    break;
                case "7":
                    studentFirstNameSearch();
                    break;
                case "8":
                    studentLastNameSearch();
                    break;
                case "x":
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
        }
        scan.close();
    }

    private void studentBirthdaySearch() {
        System.out.println("Geben Sie einen ein Datum (YYYY-MM-DD) an!");
        Date searchString = Date.valueOf(scan.nextLine());
        List<Student> studentList;

        try {
            studentList = repo.findStudentbyBirthdate(searchString);
            for(Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentsuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentsuche: " + exception.getMessage());
        }
    }

    private void studentLastNameSearch() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Student> studentList;

        try {
            studentList = repo.findStudentbyLastName(searchString);
            for(Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentsuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentsuche: " + exception.getMessage());
        }
    }

    private void studentFirstNameSearch() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Student> studentList;

        try {
            studentList = repo.findStudentbyFirstName(searchString);
            for(Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentsuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentsuche: " + exception.getMessage());
        }
    }

    private void deleteStudent() {
        System.out.println("Welchen Student möchten Sie löschen? Bitte ID angeben:");
        Long studentIdToDelete = Long.parseLong(scan.nextLine());

        try {
            repo.deleteById(studentIdToDelete);
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler beim Löschen: " + e.getMessage());
        }
    }

    private void updateStudentDetails() {
        System.out.println("Für welche Student-ID möchten Sie die Student-Details ändern?");
        Long studentId = Long.parseLong(scan.nextLine());

        try {
            Optional<Student> studentOptional = repo.getById(studentId);
            if(studentOptional.isEmpty()) {
                System.out.println("Student mit der gegebenen ID nicht in der Datenbank gefunden!");
            } else {
                Student student = studentOptional.get();

                System.out.println("Änderungen für folgenen Student: ");
                System.out.println(student);

                String firstname, lastname, birthdate;

                System.out.println("Bitte neue Studentdaten angeben (Enter, falls keine Änderung gewünscht ist):");
                System.out.println("Vorname: ");
                firstname = scan.nextLine();
                System.out.println("Nachname: ");
                lastname = scan.nextLine();
                System.out.println("Geburtsdatum (YYYY-MM-DD): ");
                birthdate = scan.nextLine();

                Optional<Student> optionalStudentUpdated = repo.update(
                        new Student(
                                student.getId(),
                                firstname.equals("") ? student.getFirstName() : firstname,
                                lastname.equals("") ? student.getLastName() : lastname,
                                birthdate.equals("") ? student.getBirthdate() : Date.valueOf(birthdate)
                        )
                );

                optionalStudentUpdated.ifPresentOrElse(
                        (c) -> System.out.println("Student aktualisiert: " + c),
                        () -> System.out.println("Student konnte nicht aktualisiert werden!")
                );
            }
        } catch(IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch(InvalidValueException invalidValueException) {
            System.out.println("Kursdaten nicht korrekt angeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void addStudent() {
        String firstName, lastName;
        Date birthdate;

        try {
            System.out.println("Bitte alle Studentdaten angeben:");
            System.out.println("Vorname: ");
            firstName = scan.nextLine();
            if(firstName.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Nachname");
            lastName = scan.nextLine();
            if(lastName.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Startdatum (YYYY-MM-DD): ");
            birthdate = Date.valueOf(scan.nextLine());

            Optional<Student> optionalCourse = repo.insert(
                    new Student(firstName, lastName, birthdate)
            );
            if(optionalCourse.isPresent()) {
                System.out.println("Kurs angelegt: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden!");
            }

        } catch(IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch(InvalidValueException invalidValueException) {
            System.out.println("Studentdaten nicht korrekt angeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void showStudentDetails() {
        System.out.println("Für welchen Student möchten Sie die Student-Details anzeigen?");
        Long studentId = Long.parseLong(scan.nextLine());
        try {
            Optional<Student> studentOptional = repo.getById(studentId);
            if(studentOptional.isPresent()) {
                System.out.println(studentOptional.get());
            } else {
                System.out.println("Student mit der ID " + studentId + " nicht gefunden!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Student-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Anzeige Student-Detailanzeige: " + exception.getMessage());
        }
    }

    private void showAllStudents() {
        List<Student> list = null;
        list = repo.getAll();
        try {
            if(!list.isEmpty()) {
                for(Student student : list) {
                    System.out.println(student);
                }
            } else {
                System.out.println("Studentliste leer!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Students: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Anzeige aller Students: " + exception.getMessage());
        }

    }

    private void showMenue() {
        System.out.println("---------------- KURSMANAGEMENT ----------------");
        System.out.println("(1) - Student hinzufügen \t (2) Alle Studenten anzeigen \t (3) Studentdetails anzeigen");
        System.out.println("(4) - Student updaten \t (5) Student löschen \t (6) Student Geburtstagssuche");
        System.out.println("(7) - Student Vornamesuche \t (8) Student Nachnamesuche \t (-) --------");
        System.out.println("(x) - ENDE");
    }

    private void inputError() {
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben!");
    }
}
