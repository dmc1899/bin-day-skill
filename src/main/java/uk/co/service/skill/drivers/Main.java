package uk.co.service.skill.drivers;

import uk.co.service.skill.adapters.mvc.BinCollectionController;

public class Main {

    public static void main (String args[]){
        System.out.println("Hello Skill...");
        BinCollectionScheduleFactory myFactory = new BinCollectionScheduleFactory();
        BinCollectionController myController = myFactory.createBinCollectionController();

        // Execute the controller!
        String myAddress = "62-Kesh-Road-Lisburn";
        String response = myController.executeUseCase(myAddress);

        System.out.println("The next bin collection schedule for " + myAddress + " is: " + response);

    }

}
