package ua.goit.handlers;

import ua.goit.model.user.User;

import java.net.http.HttpResponse;
import java.util.Locale;

public class UserHandler extends AbstractHandler{
    @Override
    public String getTemplateName() {
        return "user";
    }

    @Override
    protected void get() {
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

    private void get(String username) {
        String params = "";
        HttpResponse httpResponse = httpActions.get(getTemplateName(), params, username);
        User user = services.collectUser(httpResponse);
        services.printRegularMessage(user.toString());
    }

    @Override
    protected void post() {

    }

    @Override
    protected void put() {

    }
}
