package ua.goit.handlers;

import ua.goit.model.store.Order;

import java.net.http.HttpResponse;
import java.util.Map;

public class StoreHandler extends AbstractHandler{
    @Override
    public String getTemplateName() {
        return "store";
    }

    @Override
    protected void get() {
        services.printRegularMessage("To get order - enter order\n" +
                "To get inventory - enter inventory");
        String answer = scanner.next();
        switch (answer) {
            case "order":
                getOrder();
                break;
            case "inventory":
                getInventory(answer);
                break;
            default:
                services.printRegularMessage("Enter order or inventory");
        }
    }

    private void getInventory(String answer) {
        String params = "";
        HttpResponse httpResponse = httpActions.get(getTemplateName(), params, answer);
        Map inventory = services.collectInventory(httpResponse);
        services.printRegularMessage(inventory.entrySet().toString());
    }

    private void getOrder() {
        String params = "order/";
        services.printRegularMessage("Enter ID of order\n" +
                "Must be between 1 - 10");
        String id = scanner.next().trim();
        HttpResponse httpResponse = httpActions.get(getTemplateName(), params, id);
        Order order = services.collectOrder(httpResponse);
        services.printRegularMessage(order.toString());
    }

    @Override
    protected void post() {
        services.createOrder(scanner, getTemplateName());
        httpActions.post(getTemplateName());
    }

    @Override
    protected void put() {
        services.printErrorMessage("Not implemented");
    }
}
