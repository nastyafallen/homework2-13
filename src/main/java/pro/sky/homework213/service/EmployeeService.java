package pro.sky.homework213.service;

import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import pro.sky.homework213.Employee;
import pro.sky.homework213.exceptions.BadRequestException;
import pro.sky.homework213.exceptions.EmployeeAlreadyAddedException;
import pro.sky.homework213.exceptions.EmployeeNotFoundException;

import java.util.*;

@Service
public class EmployeeService {

    private Map<String, Employee> staff = new HashMap(Map.of(
            "Кот Федя", new Employee ("Кот", "Федя", 35000, 1),
            "Кот Тимофей", new Employee("Кот", "Тимофей", 34500, 1),
            "Кот Барсик", new Employee("Кот", "Барсик", 33000, 1),
            "Кошка Даша", new Employee("Кошка", "Даша", 37100, 2),
            "Кошка Клёпа", new Employee("Кошка", "Клёпа", 29300, 2),
            "Кошка Багира", new Employee("Кошка", "Багира", 31100, 3),
            "Кошка Люся", new Employee("Кошка", "Люся", 52000, 4),
            "Кот Шагал", new Employee("Кот Марк", "Шагал", 27700, 4),
            "Кот Джек", new Employee("Кот", "Джек", 39000, 5),
            "Кошка Джинни", new Employee("Кошка", "Джинни", 34000, 5)
    ));

    public Employee addEmployee(String name, String lastName, double salary, int department) throws EmployeeAlreadyAddedException {
        Employee employee = new Employee(name, lastName, salary, department);
        employee.setName(name);
        employee.setLastName(lastName);
        String key = getKey(employee.getName(), employee.getLastName());
        checkName(key);
        if (!staff.containsKey(key)) {
            staff.put(key, employee);
        } else {
            throw new EmployeeAlreadyAddedException("Employee is already added");
        }
        return employee;
    }

    public Employee removeEmployee(String name, String lastName) throws EmployeeNotFoundException {
        String key = getKey(name, lastName);
        if (!staff.containsKey(key)) {
            throw new EmployeeNotFoundException("Employee is not found");
        }
        return staff.remove(key);
    }

    public Employee searchEmployee(String name, String lastName) throws EmployeeNotFoundException {
        String key = getKey(name, lastName);
        if (!staff.containsKey(key)) {
            throw new EmployeeNotFoundException("Employee is not found");
        }
        return staff.get(key);
    }

    public List<Employee> staffList() {
        return new ArrayList<>(staff.values());
    }

    private String getKey(String name, String lastName) {
        return name + " " + lastName;
    }

    public void checkName(String s) throws BadRequestException {
        if (!StringUtils.isAlphaSpace(s)) {
            throw new BadRequestException("Данные введены некорректно!");
        }
    }

}
