package pro.sky.homework213.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.homework213.Employee;
import pro.sky.homework213.exceptions.EmployeeNotFoundException;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeServiceMock;

    @InjectMocks
    private DepartmentService out;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Кот", "Федя", 35000, 1),
                new Employee("Кот", "Тимофей", 34500, 1),
                new Employee("Кот", "Барсик", 33000, 1),
                new Employee("Кошка", "Даша", 37100, 2),
                new Employee("Кошка", "Клёпа", 29300, 2),
                new Employee("Кошка", "Багира", 31100, 3),
                new Employee("Кошка", "Люся", 52000, 4)
        );
        when(employeeServiceMock.staffList()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("salaryCounterParams")
    public void salaryCounterMaxPositiveTest(int department, Employee employee) {
        assertThat(out.salaryCounterMax(department)).isEqualTo(employee);
    }

    @Test
    public void salaryCounterMaxNegativeTest() {
        assertThrows(EmployeeNotFoundException.class, () -> out.salaryCounterMax(5));
    }

    @ParameterizedTest
    @MethodSource("salaryCounterParams")
    public void salaryCounterMinPositiveTest(int department, Employee employee) {
        assertThat(out.salaryCounterMax(department)).isEqualTo(employee);
    }

    @Test
    public void salaryCounterMinNegativeTest() {
        assertThrows(EmployeeNotFoundException.class, () -> out.salaryCounterMax(6));
    }

    @Test
    public void findEmployeesTest() {
        assertThat(out.findEmployees()).isNotNull();
    }

    @Test
    public void findEmployeesTest2() {
        Assertions.assertThat(out.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Кот", "Федя", 35000, 1),
                                new Employee("Кот", "Тимофей", 34500, 1),
                                new Employee("Кот", "Барсик", 33000, 1)),
                        2, List.of(new Employee("Кошка", "Даша", 37100, 2),
                                new Employee("Кошка", "Клёпа", 29300, 2))
                )
        );
    }

    @Test
    public void findEmployeesFromDepartmentTest() {
        assertThat(new ArrayList<>(out.findEmployeesFromDepartment(1))).isNotNull();
    }

    public static Stream<Arguments> salaryCounterParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Кот", "Федя", 35000, 1)),
                Arguments.of(2, new Employee("Кошка", "Даша", 37100, 2)),
                Arguments.of(3, new Employee("Кошка", "Багира", 31100, 3)),
                Arguments.of(4, new Employee("Кошка", "Люся", 52000, 4))
        );
    }

    public static Map<Integer, List<Employee>> findEmployeesParams() {
        return Map.of(
                1, List.of(new Employee("Кот", "Федя", 35000, 1),
                        new Employee("Кот", "Тимофей", 34500, 1),
                        new Employee("Кот", "Барсик", 33000, 1)),
                2, List.of(new Employee("Кошка", "Даша", 37100, 2),
                        new Employee("Кошка", "Клёпа", 29300, 2))
        );
    }

}