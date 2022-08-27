package pro.sky.homework213.service;

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

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static pro.sky.homework213.service.EmployeeServiceTestConstants.*;
import static pro.sky.homework213.service.EmployeeServiceTestConstants.LASTNAME_DEFAULT;

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
    @MethodSource("salaryCounterMaxParams")
    public void salaryCounterMaxPositiveTest(int department, Employee employee) {
        assertThat(out.salaryCounterMax(department)).isEqualTo(employee);
    }

    @Test
    public void salaryCounterMaxNegativeTest() {

    }

    public static Stream<Arguments> salaryCounterMaxParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Кот", "Федя", 35000, 1)),
                Arguments.of(2, new Employee("Кошка", "Даша", 37100, 2)),
                Arguments.of(3, new Employee("Кошка", "Багира", 31100, 3)),
                Arguments.of(4, new Employee("Кошка", "Люся", 52000, 4))
        );
    }

}