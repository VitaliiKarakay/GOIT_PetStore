package ua.goit.handlers;


import com.google.gson.Gson;
import ua.goit.service.HttpActions;
import ua.goit.service.Services;

import java.util.Scanner;

public abstract class AbstractHandler {

    Scanner scanner = new Scanner(System.in);
    HttpActions httpActions = new HttpActions();
    Services services = new Services();
    Gson gson = new Gson();

    public abstract String getTemplateName();

    public void handle(String params) {

        switch (params.toLowerCase().trim()) {
            case "get":
                get();
                break;
            case "post":
                post();
                break;
            case "put":
                put();
                break;
            case "delete":
                delete();
                break;
        }
    }

    protected abstract void get();

    protected abstract void post();

    protected abstract void put();

    private void delete() {
        System.out.println("Enter " + getTemplateName() + " id");
        String answer = scanner.next().trim();

        httpActions.delete(answer, getTemplateName());
    }
}
