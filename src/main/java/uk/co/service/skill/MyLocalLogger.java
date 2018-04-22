package uk.co.service.skill;

public class MyLocalLogger implements LocalLogger{

    public void log(String message) {
        System.out.println(message);
    }
}
