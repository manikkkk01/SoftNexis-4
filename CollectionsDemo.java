
import java.util.*;
import java.util.stream.Collectors;

// Employee class for collection demonstrations
class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String department;

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    // Natural ordering: by ID
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("%d: %s (%s)", id, name, department);
    }

    // For Set/Map uniqueness
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

// Generic utility class
class DataSorter<T> {
    // Sort with natural ordering (requires Comparable)
    public List<T> sortList(List<T> list) {
        Collections.sort((List<Comparable>) list);
        return list;
    }

    // Sort with custom Comparator
    public List<T> sortList(List<T> list, Comparator<? super T> comparator) {
        list.sort(comparator);
        return list;
    }

    // Generic filter method using Predicate
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}

public class CollectionsDemo {
    public static void main(String[] args) {
        // 1. List Demonstration
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(101, "Alice", "Engineering"));
        employeeList.add(new Employee(103, "Bob", "Marketing"));
        employeeList.add(new Employee(102, "Charlie", "HR"));

        System.out.println("=== Original List ===");
        employeeList.forEach(System.out::println);

        // 2. Set Demonstration (Unique Employees)
        Set<Employee> employeeSet = new HashSet<>(employeeList);
        employeeSet.add(new Employee(101, "Alice", "Engineering")); // Duplicate

        System.out.println("\n=== Unique Employees (Set) ===");
        System.out.println("Set size: " + employeeSet.size()); // 3, not 4

        // 3. Map Demonstration (ID → Employee)
        Map<Integer, Employee> employeeMap = new HashMap<>();
        employeeList.forEach(emp -> employeeMap.put(emp.getId(), emp));

        System.out.println("\n=== Employee Map ===");
        System.out.println("Employee 102: " + employeeMap.get(102));

        // 4. Generics Utility Usage
        DataSorter<Employee> sorter = new DataSorter<>();

        // Sort by ID (natural ordering)
        List<Employee> sortedById = sorter.sortList(new ArrayList<>(employeeList));
        System.out.println("\n=== Sorted by ID ===");
        sortedById.forEach(System.out::println);

        // Sort by Name (custom comparator)
        List<Employee> sortedByName = sorter.sortList(
                new ArrayList<>(employeeList),
                Comparator.comparing(Employee::getName)
        );
        System.out.println("\n=== Sorted by Name ===");
        sortedByName.forEach(System.out::println);

        // 5. Stream API: Group by Department
        Map<String, List<Employee>> byDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println("\n=== Employees by Department ===");
        byDepartment.forEach((dept, emps) ->
                System.out.println(dept + ": " + emps.size() + " employees")
        );

        // 6. Generic Filter Example
        List<Employee> engineeringEmployees = DataSorter.filter(
                employeeList, e -> "Engineering".equals(e.getDepartment())
        );
        System.out.println("\n=== Filtered (Engineering Employees) ===");
        engineeringEmployees.forEach(System.out::println);
    }
}
