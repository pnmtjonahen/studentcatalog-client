/*
 * Copyright (C) 2017 Philippe Tjon - A - Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.studentclient;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
public class Main {

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
    
    private void run() throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/student.list"), "UTF-8"))) {
            List<Student> students = buffer
                    .lines()
                    .filter(s -> s.split("\t").length >= 5)
                    .map(s -> {
                        String[] fields = s.split("\t");

                        return new Student(
                                fields[0],
                                fields[1],
                                fields[2],
                                fields[3],
                                fields[4]);
                    }).collect(Collectors.toList());
            int lower = 0;
            int upper = students.size();
            StringBuilder bs = new StringBuilder();
            for (int i = 0; i < students.size(); i++) {
                if (i != 0) {
                    bs.append(",");
                }
                final String surname = students.get((int) (Math.random() * (upper - lower)) + lower).getField1();
                final String name = students.get((int) (Math.random() * (upper - lower)) + lower).getField2();
                
                final String email = createEmail(surname, name, students.get((int) (Math.random() * (upper - lower))));
                final String phonenumber = createPhoneNumber(students.get((int) (Math.random() * (upper - lower))));
                
                bs.append(String.format("{\"title\":\"%s\", \"surname\":\"%s\", \"name\":\"%s\", \"email\":\"%s\", \"phonenumber\":\"%s\" }",
                                students.get((int) (Math.random() * (upper - lower)) + lower).getField0(),
                                surname, name,
                                email,
                                phonenumber));
                
            }
//            System.out.println(bs.toString());
            given()
                    .body("[\n" + bs.toString() + "\n]")
                    .contentType(ContentType.JSON)
                    .put("http://localhost:8080/api/student")
                    .then()
                    .statusCode(201);
        }

    }

    private String createEmail(String surname, String name, Student get) {
        StringBuilder sb = new StringBuilder();
        sb.append(name.replace(" ", ".").toLowerCase())
                .append(".")
                .append(surname.replace(" ", ".").toLowerCase())
                .append("@")
                .append(get.getField4().replace(" ", "-").toLowerCase())
                .append(".")
                .append(get.getField3().substring(0, 3).toLowerCase())
                ;
                
                return sb.toString();
    }

    private String createPhoneNumber(Student get) {
                int num1, num2, num3; //3 numbers in area code
        int set2, set3; //sequence 2 and 3 of the phone number
        
        Random generator = new Random();
        
        //Area code number; Will not print 8 or 9
        num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin  
        num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
        num3 = generator.nextInt(8);
        
        // Sequence two of phone number
        // the plus 100 is so there will always be a 3 digit number
        // randomize to 643 because 0 starts the first placement so if i randomized up to 642 it would only go up yo 641 plus 100
        // and i used 643 so when it adds 100 it will not succeed 742 
        set2 = generator.nextInt(643) + 100;
        
        //Sequence 3 of numebr
        // add 1000 so there will always be 4 numbers
        //8999 so it wont succed 9999 when the 1000 is added
        set3 = generator.nextInt(8999) + 1000;
        
        return "(" + num1 + "" + num2 + "" + num3 + ")" + "-" + set2 + "-" + set3;
    }

}
