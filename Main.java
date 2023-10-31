import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Main {
    static List<Student> students;

    public static void main(String[] args) throws IOException {
        loadStudents();
        studentsByCareer();
        totalFemaleByCareer();
        totalMaleByCareer();
        studentWithHighestMathScoreByCareer();
        studentWithHighestMathScore();
        averageMathScoreByCareer();
    }

    static void loadStudents() throws IOException {
        Pattern pattern = Pattern.compile(",");
        String filename = "student-scores.csv";

        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            students = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                if (arr.length >= 11) {
                    double mathScore = tryParseDouble(arr[10]);
                    if (mathScore != Double.MIN_VALUE) {
                        return new Student(
                            Integer.parseInt(arr[0]),
                            arr[1],
                            arr[2],
                            arr[4], 
                            arr[9], 
                            mathScore
                        );
                    } else {
                        System.err.println("Error en línea: " + line);
                    }
                } else {
                    System.err.println("Línea con formato incorrecto: " + line);
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    static double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.MIN_VALUE;
        }
    }

    static void studentsByCareer() {
      
        Map<String, Long> studentsByCareer = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getCareerAspiration,
                        Collectors.counting()
                ));

        System.out.println("Aspirantes por carrera (mostrar la lista y el total):");
        studentsByCareer.forEach((career, count) -> {
            System.out.println("Carrera: " + career + ", Total: " + count);
        });
    }

    static void totalFemaleByCareer() {

        Map<String, Long> totalFemaleByCareer = students.stream()
                .filter(student -> "Female".equals(student.getGender()))
                .collect(Collectors.groupingBy(
                        Student::getCareerAspiration,
                        Collectors.counting()
                ));

        System.out.println("\nTotal de mujeres por carrera:");
        totalFemaleByCareer.forEach((career, count) -> {
            System.out.println("Carrera: " + career + ", Total de mujeres: " + count);
        });
    }

    static void totalMaleByCareer() {
    
        Map<String, Long> totalMaleByCareer = students.stream()
                .filter(student -> "Male".equals(student.getGender()))
                .collect(Collectors.groupingBy(
                        Student::getCareerAspiration,
                        Collectors.counting()
                ));

        System.out.println("\nTotal de hombres por carrera:");
        totalMaleByCareer.forEach((career, count) -> {
            System.out.println("Carrera: " + career + ", Total de hombres: " + count);
        });
    }

    static void studentWithHighestMathScoreByCareer() {

        Map<String, Optional<Student>> studentWithHighestMathScoreByCareer = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getCareerAspiration,
                        Collectors.maxBy(Comparator.comparingDouble(Student::getMathScore))
                ));

        System.out.println("\nEstudiante con el puntaje más alto (math_score) por carrera:");
        studentWithHighestMathScoreByCareer.forEach((career, student) -> {
            System.out.println("Carrera: " + career + ", Estudiante: " + student.map(Student::getFirstName).orElse("N/A")
                    + " " + student.map(Student::getLastName).orElse("N/A") + ", Puntaje máximo: " + student.map(Student::getMathScore).orElse(0.0));
        });
    }

    static void studentWithHighestMathScore() {

        Optional<Student> studentWithHighestMathScore = students.stream()
                .max(Comparator.comparingDouble(Student::getMathScore));

        System.out.println("\nEstudiante con el puntaje más alto (math_score) de todos:");
        System.out.println("Estudiante: " + studentWithHighestMathScore.map(Student::getFirstName).orElse("N/A")
                + " " + studentWithHighestMathScore.map(Student::getLastName).orElse("N/A")
                + ", Puntaje máximo: " + studentWithHighestMathScore.map(Student::getMathScore).orElse(0.0));
    }

    static void averageMathScoreByCareer() {

        Map<String, Double> averageMathScoreByCareer = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getCareerAspiration,
                        Collectors.averagingDouble(Student::getMathScore)
                ));

        System.out.println("\nPuntaje Promedio (math_score) por carrera:");
        averageMathScoreByCareer.forEach((career, average) -> {
            System.out.println("Carrera: " + career + ", Puntaje Promedio: " + average);
        });
    }
}
