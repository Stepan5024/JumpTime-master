package com.example.p.jumptime;

public class Students
{

    private String studentsId;
    private String studentName;

    Students(String studentsId, String studentName) {
        this.studentsId = studentsId;
        this.studentName = studentName;
    }

    public String getStudentsId() {
        return studentsId;
    }

    public String getStudentName() {
        return studentName;
    }
}
