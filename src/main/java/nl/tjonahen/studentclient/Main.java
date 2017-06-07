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
                bs.append(String.format("{\"title\":\"%s\", \"surname\":\"%s\", \"name\":\"%s\", \"nationality\":\"%s\", \"academicBackground\":\"%s\" }",
                                students.get((int) (Math.random() * (upper - lower)) + lower).getField0(),
                                students.get((int) (Math.random() * (upper - lower)) + lower).getField1(),
                                students.get((int) (Math.random() * (upper - lower)) + lower).getField2(),
                                students.get((int) (Math.random() * (upper - lower)) + lower).getField3(),
                                students.get((int) (Math.random() * (upper - lower)) + lower).getField4()));
                
            }
//            System.out.println(bs.toString());
            given()
                    .body("[\n" + bs.toString() + "\n]")
                    .contentType(ContentType.JSON)
                    .put("http://localhost:8080/student/")
                    .then()
                    .statusCode(201);
        }

    }

}
