package org.koustav.java8query;

import java.awt.desktop.SystemEventListener;
import java.util.*;
import java.util.stream.Collectors;

public class DemoQuery {

    public static void main(String[] args) {


        System.out.println("Ques ------How many male and female employees are there in the organization?----");
        Map<String, Long> noOfMaleAndFemaleEmployees =
                EMPDB.getEmployees().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));

        System.out.println(noOfMaleAndFemaleEmployees);

        System.out.println("Ques -----Print the name of all departments in the organization?-----");
        EMPDB.getEmployees().stream()
                .map(Employee::getDepartment)
                .distinct()
                .forEach(System.out::println);

        System.out.println("Ques ------------What is the average age of male and female employees?---------");
        Map<String, Double> avgAgeOfMaleAndFemaleEmployees=
                EMPDB.getEmployees().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));

        System.out.println(avgAgeOfMaleAndFemaleEmployees);

        System.out.println("Ques -----the details of highest paid employee in the organization?------");
        Optional<Employee> highestPaidEmployeeWrapper=
                EMPDB.getEmployees().stream().collect(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)));

        Employee highestPaidEmployee = highestPaidEmployeeWrapper.get();

        System.out.println("Details Of Highest Paid Employee : ");

        System.out.println("==================================");

        System.out.println("ID : "+highestPaidEmployee.getId());

        System.out.println("Name : "+highestPaidEmployee.getName());

        System.out.println("Age : "+highestPaidEmployee.getAge());

        System.out.println("Gender : "+highestPaidEmployee.getGender());

        System.out.println("Department : "+highestPaidEmployee.getDepartment());

        System.out.println("Year Of Joining : "+highestPaidEmployee.getYearOfJoining());

        System.out.println("Salary : "+highestPaidEmployee.getSalary());


        System.out.println("Ques-----Get the names of all employees who have joined after 2015?-------");
        EMPDB.getEmployees().stream()
                .filter(e -> e.getYearOfJoining() > 2015)
                .map(Employee::getName)
                .forEach(System.out::println);


        System.out.println("Ques------Count the number of employees in each department?----------");
        Map<String, Long> employeeCountByDepartment=
                EMPDB.getEmployees().stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        Set<Map.Entry<String, Long>> entrySet = employeeCountByDepartment.entrySet();

        for (Map.Entry<String, Long> entry : entrySet)
        {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }

        System.out.println("Ques----What is the average salary of each department?---");
        Map<String, Double> avgSalaryOfDepartments=
                EMPDB.getEmployees().stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));

        Set<Map.Entry<String, Double>> entrySet1 = avgSalaryOfDepartments.entrySet();

        for (Map.Entry<String, Double> entry : entrySet1)
        {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }


        System.out.println("Ques---------Get the details of youngest male employee in the product development department?-- ");
        Optional<Employee> youngestMaleEmployeeInProductDevelopmentWrapper=
                EMPDB.getEmployees().stream()
                        .filter(e -> e.getGender()=="Male" && e.getDepartment()=="Product Development")
                        .min(Comparator.comparingInt(Employee::getAge));

        Employee youngestMaleEmployeeInProductDevelopment = youngestMaleEmployeeInProductDevelopmentWrapper.get();
        System.out.println(youngestMaleEmployeeInProductDevelopment);


        System.out.println("Ques----------Who has the most working experience in the organization?---------");
        Optional<Employee> seniorMostEmployeeWrapper=
                EMPDB.getEmployees().stream().sorted(Comparator.comparingInt(Employee::getYearOfJoining)).findFirst();

        Employee seniorMostEmployee = seniorMostEmployeeWrapper.get();
        System.out.println(seniorMostEmployee);

        System.out.println("Ques-----How many male and female employees are there in the sales and marketing team?");

        Map<String, Long> countMaleFemaleEmployeesInSalesMarketing=
                EMPDB.getEmployees().stream()
                        .filter(e -> e.getDepartment()=="Sales And Marketing")
                        .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));

        System.out.println(countMaleFemaleEmployeesInSalesMarketing);


        System.out.println("Ques-----What is the average salary of male and female employees?");
        Map<String, Double> avgSalaryOfMaleAndFemaleEmployees=
                EMPDB.getEmployees().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));

        System.out.println(avgSalaryOfMaleAndFemaleEmployees);


        System.out.println("Ques--------- List down the names of all employees in each department?");
        Map<String, List<Employee>> employeeListByDepartment=
                EMPDB.getEmployees().stream().collect(Collectors.groupingBy(Employee::getDepartment));

        Set<Map.Entry<String, List<Employee>>> entrySet2 = employeeListByDepartment.entrySet();

        for (Map.Entry<String, List<Employee>> entry : entrySet2)
        {
            System.out.println("--------------------------------------");

            System.out.println("Employees In "+entry.getKey() + " : ");

            System.out.println("--------------------------------------");

            List<Employee> list = entry.getValue();

            for (Employee e : list)
            {
                System.out.println(e.getName());
            }
        }

        System.out.println("Ques --------What is the average salary and total salary of the whole organization?");
        DoubleSummaryStatistics employeeSalaryStatistics=
                EMPDB.getEmployees().stream().collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println("Average Salary = "+employeeSalaryStatistics.getAverage());

        System.out.println("Total Salary = "+employeeSalaryStatistics.getSum());

        System.out.println("Ques ----Separate the employees who are younger or equal to 25 years from those employees who are older than 25 years.");
        Map<Boolean, List<Employee>> partitionEmployeesByAge=
                EMPDB.getEmployees().stream().collect(Collectors.partitioningBy(e -> e.getAge() > 25));

        Set<Map.Entry<Boolean, List<Employee>>> entrySet3 = partitionEmployeesByAge.entrySet();

        for (Map.Entry<Boolean, List<Employee>> entry : entrySet3)
        {
            System.out.println("----------------------------");

            if (entry.getKey())
            {
                System.out.println("Employees older than 25 years :");
            }
            else
            {
                System.out.println("Employees younger than or equal to 25 years :");
            }

            System.out.println("----------------------------");

            List<Employee> list = entry.getValue();

            for (Employee e : list)
            {
                System.out.println(e.getName());
            }
        }
        System.out.println("Ques -----Who is the oldest employee in the organization? What is his age and which department he belongs to?");
        Optional<Employee> oldestEmployeeWrapper = EMPDB.getEmployees().stream().max(Comparator.comparingInt(Employee::getAge));

        Employee oldestEmployee = oldestEmployeeWrapper.get();
        System.out.println(oldestEmployee);
    }
}
