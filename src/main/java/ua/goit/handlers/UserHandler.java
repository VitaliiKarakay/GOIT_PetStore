package ua.goit.handlers;

import ua.goit.model.user.User;

import java.net.http.HttpResponse;
import java.util.ArrayList;

public class UserHandler extends AbstractHandler{
    @Override
    public String getTemplateName() {
        return "user";
    }

    @Override
    protected  void get() {
        services.printRegularMessage("To get user by username - enter username\n" +
                "To log into the system - enter login\n" +
                "To log out - enter logout");
        String answer = scanner.next().trim().toLowerCase();
        switch (answer) {
            case "username":
                services.printRegularMessage("Print " + getTemplateName() + " username");
                String username = scanner.next();
                get(username);
                break;
            case "login":
                services.printRegularMessage("Enter " + getTemplateName() + " login");
                login();
                break;
            case "logout":
                logout();
                break;
            default:
                services.printErrorMessage("To get user by username - enter username\n" +
                        "To log into the system - enter login\n" +
                        "To log out - enter logout");
        }
    }

    private void logout() {
        String params = "logout";
        HttpResponse httpResponse = httpActions.get(getTemplateName(), params, "");
        if (httpResponse != null && httpResponse.statusCode() == 200) {
            services.printRegularMessage("Logged out");
        }
        else {
            services.printRegularMessage(String.valueOf(httpResponse.statusCode()));
        }
    }

    private void login() {
        String params = "login?username=" + scanner.next();
        services.printRegularMessage("Enter password");
        params += "&password=" + scanner.next();
        HttpResponse httpResponse = httpActions.get(getTemplateName(), params, "");
        if (httpResponse != null && httpResponse.statusCode() == 200) {
            services.printRegularMessage("Logged in");
        }
        else {
            services.printRegularMessage(String.valueOf(httpResponse.statusCode()));
        }
    }

    private User get(String username) {
        String params = "";
        HttpResponse httpResponse = httpActions.get(getTemplateName(), params, username);
        User user = services.collectUser(httpResponse);
        services.printRegularMessage(user.toString());
        return user;
    }

    @Override
    protected void post() {
        services.printRegularMessage("To create single user - enter user\n" +
                "To create users from array - enter array\n" +
                "To create users from list - enter list");
        String answer = scanner.next().trim().toLowerCase();
        switch (answer) {
            case "user":
                createUser();
                break;
            case "array":
            case "list":
                createUsers(answer);
                break;
        }
    }

    private void createUser() {
        services.createUser(scanner, getTemplateName());
        httpActions.post(getTemplateName());
    }

    private void createUsers(String answer) {
        String params = "";
        switch (answer) {
            case "list":
                params = "/createWithList";
                break;
            case "array":
                params = "/createWithArray";
                break;
        }
        services.printRegularMessage("How many users you want create?");
        int count = scanner.nextInt();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = services.createUser(scanner, getTemplateName());
            users.add(user);
        }
        services.createFile(users, getTemplateName());
        httpActions.post(getTemplateName(), params);
    }

    @Override
    protected void put() {
        services.printRegularMessage("Enter username for update");
        String params = scanner.next();
        User user = get(params);
        User user2 = services.createUser(scanner, getTemplateName());
        user2.setUsername(user.getUsername());
        services.createFile(user2, getTemplateName());
        httpActions.put(getTemplateName(), params);

    }
}
