package ua.goit.handlers;

import com.google.gson.Gson;
import ua.goit.model.pet.Pet;
import ua.goit.model.pet.Pets;
import ua.goit.service.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpResponse;

public class PetHandler extends AbstractHandler{



    @Override
    public String getTemplateName() {
        return "pet";
    }

    @Override
    protected void get() {
        services.printRegularMessage("Get by ID or status?");
        String answer = scanner.next().toLowerCase().trim();
        switch (answer) {
            case "id":
                services.printRegularMessage("Enter " + getTemplateName() + " id");
                Long id = scanner.nextLong();
                get(id);
                break;
            case "status":
                services.printRegularMessage("Available statuses: " + Services.statusList);
                String status = scanner.next().trim();
                get(status);
                break;
            default:
                services.printErrorMessage("Enter id or status");
                get();
        }
    }

    private void get(String status) {
        String params = "findByStatus?status=";
        services.printRegularMessage("Available statuses: " + Services.statusList);
        if (!Services.statusList.contains(status)) {
            services.printErrorMessage("Print correct status");
        }
        HttpResponse response = httpActions.get(getTemplateName(), params, status);
        Pets pets = services.collectPets(response);
        for (Pet pet: pets) {
            services.printRegularMessage(pet.toString());
        }
    }

    private void get(Long id) {
        String params = "";
        HttpResponse response = httpActions.get(getTemplateName(),params, id.toString());
        Pet pet = services.collectPet(response);
        services.printRegularMessage(pet.toString());
    }

    @Override
    protected void post() {
        services.printRegularMessage("Create new pet - enter new\n"
        + "upload an image by path - enter image\n"
        + "update existing pet - enter update");

        String input = scanner.next().trim().toLowerCase();
        switch (input) {
            case "new":
                createNewPet();
                break;
            case "image":
                uploadImage();
                break;
            case "update":
                updatePet();
                break;
            default:
                services.printErrorMessage("Enter correct option");
        }
    }

    private void createNewPet() {
        services.createPet(scanner, getTemplateName());
        httpActions.post(getTemplateName());
    }

    private void uploadImage() {

    }

    private void updatePet() {
        services.printRegularMessage("Enter pet's ID");
        HttpResponse httpResponse = httpActions.get(getTemplateName(), "", String.valueOf(scanner.next()));
        if (httpResponse.statusCode() == 200) {
            Pet pet = services.collectPet(httpResponse);
            services.printRegularMessage("Enter new pet's name");
            pet.setName(scanner.next());
            services.printRegularMessage("Enter new pet's status.\n" +
                    "available statuses are " + Services.statusList);
            pet.setStatus(scanner.next());

            File file = new File("pet.json");
            try {
                file.createNewFile();
            } catch (IOException e) {
                services.printErrorMessage("File was not created");
            }
            FileWriter writer;
            try {
                writer = new FileWriter(file);
                writer.write(gson.toJson(pet));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                services.printErrorMessage("File was not created");
            }
            httpActions.post(getTemplateName());
        }
        else {
            services.printRegularMessage("Pet does not exist");
        }
    }

    @Override
    protected void put() {

    }


}
