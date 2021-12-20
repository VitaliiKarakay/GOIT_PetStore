package ua.goit.handlers;

import ua.goit.model.store.Order;

import java.net.http.HttpResponse;

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
                getInventory();
                break;
            default:
                services.printRegularMessage("Enter order or inventory");
        }
    }

    private void getInventory() {

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

    }

    @Override
    protected void put() {

    }
}
