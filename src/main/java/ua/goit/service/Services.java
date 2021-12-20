package ua.goit.service;

import com.google.gson.Gson;
import ua.goit.handlers.AbstractHandler;
import ua.goit.handlers.PetHandler;
import ua.goit.handlers.StoreHandler;
import ua.goit.handlers.UserHandler;
import ua.goit.model.pet.Category;
import ua.goit.model.pet.Pet;
import ua.goit.model.pet.Pets;
import ua.goit.model.pet.Tag;
import ua.goit.model.store.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

public class Services {
    public static final List<String> requestList = Arrays.asList("get", "post", "put", "delete");
    public static final List<String> entities = Arrays.asList("pet", "store", "user");
    public static final List<String> statusList = Arrays.asList("available", "pending", "sold");
    public static final Map<String, AbstractHandler> commandMap = new HashMap<>();
    Gson gson = new Gson();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        printRegularMessage("Welcome to the app\n" + "You can send requests to petstore.swagger.io\n");
        commandMap.put("pet", new PetHandler());
        commandMap.put("store", new StoreHandler());
        commandMap.put("user", new UserHandler());

        while (true) {
            printRegularMessage("Choose the entity\n" + entities + "\nTo exit enter: exit");
            String inputEntity = scanner.nextLine().toLowerCase();
            if (inputEntity.equalsIgnoreCase("exit")) {
                System.out.println("Exiting from app...");
                break;
            }
            if (!commandMap.containsKey(inputEntity)) {
                printErrorMessage("Your input is not correct");
                continue;
            }

        printRegularMessage("Choose the request\n" + requestList + "\nTo exit enter: exit");
        String inputRequest = scanner.nextLine().toLowerCase();
            if (!requestList.contains(inputRequest)) {
                printErrorMessage("Invelid request. Try again");
            }

            commandMap.get(inputEntity).handle(inputRequest);
        }
    }

    public void printErrorMessage(String msg) {
        System.err.println(msg);
    }

    public void printRegularMessage(String msg) {
        System.out.println(msg);
    }

    public Pet collectPet(HttpResponse response) {
        return gson.fromJson(String.valueOf(response.body()), Pet.class);
    }

    public Pets collectPets(HttpResponse response) {
        return gson.fromJson(String.valueOf(response.body()), Pets.class);
    }

    public Map collectStore(HttpResponse response) {
        return gson.fromJson(String.valueOf(response), Map.class);
    }

    public Order collectOrder (HttpResponse response) {
        return gson.fromJson(String.valueOf(response), Order.class);
    }

    public void createPet (Scanner scanner) {
        Pet pet = new Pet();
        printRegularMessage("Enter pets ID");
        pet.setId(scanner.nextLong());
        printRegularMessage("Enter pets name");
        pet.setName(scanner.next());
        printRegularMessage("Enter pets status");
        pet.setStatus(scanner.next());

        Category category = new Category();
        printRegularMessage("Enter category ID");
        category.setId(scanner.nextInt());
        printRegularMessage("Enter category name");
        category.setName(scanner.next());
        pet.setCategory(category);

        List<Tag> tags = new ArrayList<>();
        createTags(scanner, tags);
        printRegularMessage("Do you need to add an additional tag? Enter Yes or No");
        while (scanner.next().equals("yes")) {
            createTags(scanner, tags);
            printRegularMessage("Do you need to add an additional tag? Enter Yes or No");
        }
        pet.setTags(tags);

        List<String> urls = new ArrayList<>();
        printRegularMessage("Enter path to photo's URL");
        urls.add(scanner.next());
        pet.setProtoUrls(urls);

        File file = new File ("pet.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("File wasn't created");;
        }
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(pet));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File wasn't created");;
        }

    }

    private void createTags(Scanner scanner, List<Tag> tags) {
        Tag tag = new Tag();
        printRegularMessage("Enter pets tag name");
        tag.setName(scanner.next());
        tags.add(tag);
    }

    public void createOrder(Scanner scanner) {
        Order order = new Order();
        printRegularMessage("Enter order's ID");
        order.setId(scanner.nextLong());
        printRegularMessage("Enter petID");
        order.setPetId(scanner.nextLong());
        printRegularMessage("Enter quantity");
        order.setQuantity(scanner.nextLong());
        Date date = new Date(System.currentTimeMillis());
        order.setShipDate(date);
        printRegularMessage("Enter status");
        order.setStatus(scanner.next());
        order.setComplete(false);

        File file = new File ("order.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("File wasn't created");;
        }
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(order));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File wasn't created");;
        }
    }

}
