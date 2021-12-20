package ua.goit;

import ua.goit.service.Services;

/**
 * Hello world!
 * TODO:
 * uploadImage in petHandler;
 * put() in petHandler
 */
public class App 
{
    public static void main( String[] args )
    {
        Services services = new Services();
        services.run();

    }
}
