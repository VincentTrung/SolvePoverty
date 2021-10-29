//ICS4U 
//Mr.Loo
//
//Poverty Text-based Game Simulation
//Created by Vincent Trung
/////////////////////////////////
import java.util.Scanner; // import for input 

class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in); // set up input

    /////////////////////////////////////
    // Create Game start variables
    int day = 31;
    int time = 5000;
    int paying, emergencyChance, billIndex, emergencyIndex;
    double interest = 0.19; // 19% interest

    // arrays for the emergency paymenys and scenarios
    double[] emergencyCosts = { 0, 0 };
    String[] emergencyTypes = {
        "Your car is broken, you will take public transportation for work $5/day.\n\tRepairs will  cost",
        "Oh no! A family member has fallen ill.\n\tThey require a medical procedure costing" };

    //arrays for bill payments
    String[] bills = { "Electric Bill", "Water Bill", "Gas Bill", "Internet Bill", "Phone Bill", "Rent","Groceries" };
    double[] billCosts = { 95, 58, 42, 54, 50, 1800, 100 };

    //end text to direct user to sources corresponding missed payment of their bills
    String endgameDisplay = "\nThese are the payments you seemed to have struggled with, the following are the support programs or petitions that are essential to decreasing economic inqualities and help them get basic human needs.\nWe must ensure they are well funded and DO NOT receive budget cuts!!\n\n";
    String[] endgameInfo = {
      "The Ontario Electricity Support Program is a great program for those low income households which need assistance paying the electric bills. They will apply a deduction to your bill monthly and is super easy to apply! the deduction just depends on the residents and combined income. More information can be found in the following site:\n\t www.ontarioelectricitysupport.ca",
      "If you are a low income senior or low income with diabilities you may be able to apply for Toronto's Water Rebate Program. More information on the application can be found here:\n\t www.toronto.ca/services-payments/property-taxes-utilities/property-tax/property-tax-rebates-and-relief-programs/property-tax-and-utility-relief-program/",
      "There are multiple agencies that can offer Low-income Energy Assistance Programs that will help you if youre behind on electric or natural gas bills! More information on LEAP can be found here:\n\t www.oeb.ca/rates-and-your-bill/help-low-income-consumers/low-income-energy-assistance-program",
      "The CRTC recently did a reversal on lowering wholesale internet prices. This affects the prices smaller ISP can provide to the consumers and therefore they are banding together to create a petition to overule the CRTC. Do your part in keeping internet rates low by signing the petition here:\n\t www.action.openmedia.org/page/83751/petition/1?locale=en-US",
      "It is unfair how phone plans continue to rise in Canada while developed nations are lowering it! The CRTC must listen to Canadians to make it affordable for all. Here is a petition that you can sign to make a difference:\n\t www.change.org/p/justin-trudeau-affordable-unlimited-data-plans-in-canada",
      "Toronto offers Rent-Geared-to-Income subsidy to make rent afforable for households. They give subsidized units that is adjusted to the households income. For more information on applying visit:\n\t www.toronto.ca/community-people/employment-social-support/housing-support/rent-geared-to-income-subsidy/"
      };

    //boolean values used for condition statements
    boolean running, survived;
    boolean emergency = false;
    boolean dayPassed = false;

    //strings that will be called upon for inputs and displaying strings
    String cls = "\033[H\033[2J"; // quick easy way to clear console
    String billDisplay = "";
    String emergencyDisplay = "";

    String action, start;

    // user variables starting values
    double balance = 1000;
    double wage = 14.25;
    int foodStock = 3;
    // end of variables created/intialized
    //////////////////////////////////////

    ////////////////////////////////////////////////////////////////////
    //Intro Screen
    while(true) {
      System.out.print(cls);

      System.out.printf("%50s \n\n","Welcome to the Poverty Line");
      System.out.println("Objective: Make your payments by the end of the month or you will be evicted, be sure to have savings\n");
      System.out.println("Objective: Make your payments by the end of the month or you will be evicted, be sure to have savings\n");
      System.out.print("To start, type \"play\" or \"quit\" to exit: ");
      start = input.nextLine();

      if (start.equals("play")){
        running = true;
        break;
      }else if (start.equals("quit")) {
        running = false;
        break;
      }
    }
    System.out.print(cls);
    //end of start screen
    /////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////
    // start the game loop
    while (running == true) {
      // cls for this loop/game day
      System.out.print(cls);

      // always display the HUD with the stats and info needed
      System.out.printf("Days Left: %d\n", day);
      System.out.printf("Days of meals left: %d\n", foodStock);
      System.out.printf("Balance: $%.2f\n", balance);
      System.out.printf("Wage: $%.2f\n\n", wage);

      // the index to be reset so it can display the different bills and can be
      // recalled later to call index of the list
      billIndex = 0;
      emergencyIndex = 0;

      // loop through all the bills that need to be paid for, then display that info
      for (int i = 0; i < bills.length; i++) {
        // If the billCost is 0, therefore paid, it can be skipped
        if (billCosts[i] == 0) {
          continue;
        }
        // keep track of how many bills and index them
        billIndex += 1;
        // print out the information
        billDisplay = billIndex + ". " + bills[i] + ": $" + billCosts[i];
        System.out.println(billDisplay);
      }
      // Create emergency situations and list them with other payments
      // same structure but the index is added ontop of the billIndex lists
      for (int i = 0; i < emergencyTypes.length; i++) {
        if (emergencyCosts[i] == 0) {
          continue;
        }
        emergencyIndex += 1;
        emergencyDisplay = billIndex + emergencyIndex + ". " + emergencyTypes[i] + ": $" + emergencyCosts[i];
        System.out.println(emergencyDisplay);
      }

      // Chance of emergency keep generating until it happens
      emergencyChance = (int) (Math.random() * 100); // 0-100
      if (emergencyChance < 5) { // about 5% chance
        emergencyCosts[0] = 1000;
      } else if (emergencyChance >= 95) {// 5%
        emergencyCosts[1] = 1000;
      }

      // wait for input/decision
      System.out.print("\nEnter \"pay\" to proceed with a payment or \"work\" to work the whole day: ");
      action = input.nextLine();

      // Check the answer to determine whether user works or is paying a bill
      if (action.equals("pay")) {
        // Ask user for the bill they want to pay, starting from one
        System.out.print("Enter the index of the bill, or enter any other number to go back: ");
        paying = (input.nextInt()) - 1; // minus 1 cause arrays start at 0
        input.nextLine();// read dummy string after collecting int input

        // Check to make sure that the input is one of the options being listed
        if (paying < (billIndex + emergencyIndex) && paying >= 0) {
          // if the input is under the bill indexs, try to figure which one it is
          if (paying < billIndex) {
            // loop through all the billcosts to see what is paid then apply that shift to
            // variable paying so its aligned with the correct array index
            for (int i = 0; i <= paying; i++) {
              if (billCosts[i] == 0) {
                paying += 1; // adjust the index for bill being paid since the list was changed
                continue;
              }
            }
            // subtract the cost of bill from balance and set the value as zero in the
            // corresponding array index to note it was already paid
            balance -= billCosts[paying];
            billCosts[paying] = 0;

            // check if input is a emergency bill
          } else if (paying <= billIndex + emergencyIndex) {
            paying -= billIndex; // adjust the index to the emergency arrays indexs
            // loop through the emergencyCosts to adjust the index
            for (int i = 0; i <= paying; i++) {
              if (emergencyCosts[i] == 0) {
                paying += 1; // adjust the index for bill being paid since the list was changed
                continue;
              }
            }
            // subtract the cost of the bill from balance and set the value to zero of the
            // corresponding array index.
            balance -= emergencyCosts[paying];
            emergencyCosts[paying] = 0;
            dayPassed = true; // this task will take a day
            // catch every other number
          } else {
            continue;
          }
        }
      } else if (action.equals("work")) {
        // the whole game day will pass and add money into balance based on wage
        dayPassed = true;
        balance += wage * 5;
        // if there is a car emergency it will affect the transportation to work
        // therefore cost 5$ transportation
        if (emergencyCosts[0] > 0) {
          balance -= 8;
        }
        // let the user know it was invalid and have a short delay for them to read. then re-loop
      } else {
        try {
          System.out.println("\nThat was an invalid input, please try again.");
          Thread.sleep(1000);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        }
        System.out.print(cls);
        continue;
      }

    
      // if they buy Groceries add 7 days of stock and keep the bill still there in
      // case they want to bulk buy
      if (billCosts[6] == 0) {
        foodStock += 7;
        billCosts[6] = 100;
      }

      // minus a day for certain tasks done
      // and return it back to false to catch if day passes again
      if (dayPassed) {
        day -= 1;
        dayPassed = false;
        // If theres no groceries/foodstock, there is no option but to pay for fast food
        if (foodStock == 0) {
          System.out.println(
              "\nYou do not have enough food to feed yourself today. \nYou decided to have fast food (-$15) \nBe sure to stock up on groceries. Enter any character to continue: ");
          balance -= 20;
          input.nextLine();//pause until they want to continue after reading
        } else {
          foodStock -= 1;
        }

        // apply daily interest if in debt
        if (balance < 0) {
          balance -= balance * interest;
        }
      }

      // Check if game is over
      if (day <= 0){
        running = false;
      }
    }
    // end of game loop
    ////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////
    //end game screen

    //reuse again to index of the list
    billIndex = 0;

    //cycle through bills to see where they struggled besides the last one(groceries)
    for (int i = 0; i < bills.length-1; i++) {
      // If the billCost is 0, therefore paid, it can be skipped
      if (billCosts[i] == 0) {
        continue;
      }
      // keep number the bills assistance programs that are being listed
      billIndex += 1;
      //save information into variable to print later
      endgameDisplay += "\n" +billIndex + ". " + bills[i] + "\n" + endgameInfo[i]+ "\n";
    }

    //clear screen to display final info needed
    System.out.print(cls);
    System.out.println(endgameDisplay);
    //end of code
    ///////////////////////////////////////////////////////////////////////
  }
}