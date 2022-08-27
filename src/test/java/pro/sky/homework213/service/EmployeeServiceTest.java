package pro.sky.homework213.service;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.homework213.Employee;
import pro.sky.homework213.exceptions.BadRequestException;
import pro.sky.homework213.exceptions.EmployeeNotFoundException;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.homework213.service.EmployeeServiceTestConstants.*;

class EmployeeServiceTest {
    private final EmployeeService out = new EmployeeService();

    @ParameterizedTest
    @MethodSource("ProvideParamsForTest")
    public void addEmployeePositiveTest(String name, String lastName, double salary, int department) {
        Employee employee = out.addEmployee(name, lastName, salary, department);
        assertNotNull(employee);
        assertTrue(StringUtils.isAlphaSpace(employee.getName()));
        assertEquals(employee.getLastName(), lastName);
    }

    @ParameterizedTest
    @MethodSource("ProvideParamsForTest")
    public void addEmployeePositiveTest2(String name, String lastName, double salary, int department) {
        Employee employee = new Employee(name, lastName, salary, department);
        employee.setName(name);
        employee.setLastName(lastName);
        assertThat(out.addEmployee(name, lastName, salary, department)).isEqualTo(employee);
        assertThat(out.staffList().contains(employee)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("ProvideBadParamsForTest")
    public void addEmployeeNegativeTest(String name, String lastName, double salary, int department) {
        assertThrows(BadRequestException.class, () -> out.addEmployee(name, lastName, salary, department));
    }

    @ParameterizedTest
    @MethodSource("ProvideStringsForTest")
    public void checkNamePositiveTest(String a, String b) {
        String c = a + " " + b;
        assertDoesNotThrow(() -> out.checkName(c));
    }

    @ParameterizedTest
    @MethodSource("ProvideBadStringsForTest")
    public void checkNameNegativeTest(String a, String b) {
        String c = a + " " + b;
        assertThrows(BadRequestException.class, () -> out.checkName(c));
    }

    @Test
    public void removeEmployeePositiveTest() {
        Employee employee = new Employee(NAME_CORRECT, LASTNAME_DEFAULT, 0, 3);
        assertThat(out.addEmployee(NAME_CORRECT, LASTNAME_DEFAULT, 0, 3)).isEqualTo(employee);
        assertThat(out.removeEmployee(NAME_CORRECT, LASTNAME_DEFAULT)).isEqualTo(employee);
    }

    @ParameterizedTest
    @MethodSource("ProvideParamsForTest")
    public void removeEmployeeNegativeTest(String name, String lastName, double salary, int department) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.removeEmployee("test", "test"));

        Employee employee = new Employee(name, lastName, salary, department);
        employee.setName(name);
        employee.setLastName(lastName);
        assertThat(out.addEmployee(name, lastName, salary, department)).isEqualTo(employee);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.removeEmployee("test", "test"));
    }

    @Test
    public void searchEmployeePositiveTest() {
        Employee employee = new Employee(NAME_CORRECT, LASTNAME_DEFAULT, 0, 3);
        assertThat(out.addEmployee(NAME_CORRECT, LASTNAME_DEFAULT, 0, 3)).isEqualTo(employee);
        assertThat(employee).isEqualTo(out.searchEmployee(NAME_CORRECT, LASTNAME_DEFAULT));
    }

    @ParameterizedTest
    @MethodSource("ProvideParamsForTest")
    public void searchEmployeeNegativeTest(String name, String lastName, double salary, int department) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.searchEmployee("test", "test"));

        Employee employee = new Employee(name, lastName, salary, department);
        employee.setName(name);
        employee.setLastName(lastName);
        assertThat(out.addEmployee(name, lastName, salary, department)).isEqualTo(employee);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.searchEmployee("test", "test"));
    }

    public static Stream<Arguments> ProvideParamsForTest() {
        return Stream.of(
                Arguments.of(NAME_CORRECT, LASTNAME_DEFAULT, 0, 3),
                Arguments.of(NAME_RU, LASTNAME_DEFAULT, 0, 3),
                Arguments.of(NAME_SPACE, LASTNAME_DEFAULT, 0, 3),
                Arguments.of(NAME_UPPER_CASE, LASTNAME_DEFAULT, 0, 3),
                Arguments.of(NAME_LOWER_CASE, LASTNAME_DEFAULT, 0, 3),
                Arguments.of(NAME_EMPTY, LASTNAME_EMPTY, 0, 3)
        );
    }

    public static Stream<Arguments> ProvideStringsForTest() {
        return Stream.of(
                Arguments.of(NAME_EMPTY, LASTNAME_EMPTY),
                Arguments.of(NAME_RU, LASTNAME_DEFAULT),
                Arguments.of(NAME_CORRECT, LASTNAME_DEFAULT),
                Arguments.of(NAME_SPACE, LASTNAME_DEFAULT),
                Arguments.of(NAME_UPPER_CASE, LASTNAME_DEFAULT),
                Arguments.of(NAME_LOWER_CASE, LASTNAME_DEFAULT)
        );
    }

    public static Stream<Arguments> ProvideBadParamsForTest() {
        return Stream.of(
                Arguments.of(NAME_NUM, LASTNAME_DEFAULT, 0, 3),
                Arguments.of(NAME_CHAR, LASTNAME_DEFAULT, 0, 3)
        );
    }

    public static Stream<Arguments> ProvideBadStringsForTest() {
        return Stream.of(
                Arguments.of(NAME_NUM, LASTNAME_DEFAULT),
                Arguments.of(NAME_CHAR, LASTNAME_DEFAULT)
        );
    }

}
