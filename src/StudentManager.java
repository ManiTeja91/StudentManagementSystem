import java.io.*;
import java.util.*;

public class StudentManager {
    private List<Student> students;
    private static final String FILE_PATH = "students.txt";

    public StudentManager() {
        students = new ArrayList<>();
        loadStudents();
    }

    // CRUD Operations
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    public Student getStudent(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateStudent(String id, String name, String grade) {
        Student student = getStudent(id);
        if (student != null) {
            student = new Student(id, name, grade);
            students.removeIf(s -> s.getId().equals(id));
            students.add(student);
            saveStudents();
        }
    }

    public void deleteStudent(String id) {
        students.removeIf(s -> s.getId().equals(id));
        saveStudents();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    // File Persistence
    private void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new Student(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            System.out.println("No existing data found. Starting fresh.");
        }
    }

    private void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}