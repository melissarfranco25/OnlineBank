package onlinebank;

/**
 * Libraries  
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class. To create a customer.
 * @author Melissa Rivera-Franco
 */
public class Customer{
    private String Fname;
    private String Lname;
    private ArrayList<Accounts> accounts;
    
    //CONSTRUCTORS
    public Customer(){}
   
    public Customer(String fN, String lN){
        this.Fname=fN;
        this.Lname=lN;
        accounts= new ArrayList();
    }
    
    //GETTERS
    public String getFirstName(){
        return Fname;
    }
    
    public String getLastName(){
        return Lname;
    }
    
    public String getCustomer(){
        return this.Fname +" "+this.Lname;
    }
    
    //Method to add an account to the customers ArrayList<>
    public void addAccount(Accounts sc){
        accounts.add(sc);
    }
    
    //Account ArrayList<> getters
    public ArrayList<Accounts> getAccounts(){
        return accounts;
    }
    
    //Returns the first name and last name
    public String display(){
        return Fname + " "+Lname;
       
    }
}
/**
 * Class Accounts extends Customer. Creates default methods for different type of accounts.
 * @author Melissa Rivera-Franco
 */
class Accounts extends Customer {
    
     private static int accountNumber=1000;
     private int accNum;
     private double balance;
    
     //CONSTRUCTORS
     public Accounts(){
     super();
     }
     
     
     public Accounts(double bal, String fn, String lN){     
         super(fn,lN);
         this.balance=bal;
         Accounts.incrementAccountNumber();
         accNum=accountNumber;
         
     }
     
     public Accounts(String fn, String ln){
         super(fn,ln);
         Accounts.incrementAccountNumber();
         accNum=accountNumber;
     }
     
     //GETTERS
     public int getAccountNumber(){
         return accNum;
     }

     public double getBalance(){
         return balance;
     }
  
     //Method used to increment the account number within this same class.
     public static void incrementAccountNumber(){
         accountNumber+=1;
     }
     
     //SETTERS
     public void setBalance(double bal){
         this.balance=bal;
     }
     
     //Deposit method.
     public boolean deposit(double am){
         balance+=am;
         return true;
     }
     
     //Withdraw method.
     public boolean withdraw(double am){
         if(balance>=am){
             balance-=am;
             return true;
         }
         return false;
     }
     
     //Method to make sure the user input is a vaild double.
     public double validInput(Scanner sc){
        while (!sc.hasNextDouble()){
            System.out.println("Invalid input. \nPlease try again: ");
            sc.next();
        }
        double userInput = sc.nextDouble();
        return userInput;
       }
     
}

/**
 * Class Checkings extends Accounts. 
 * @author Melissa Rivera-Franco
 */
class Checkings extends Accounts{
    
    //Constructor
    public Checkings(double balance, String fN, String lN){
        super(balance, fN, lN);
        Scanner sc = new Scanner(System.in);
        
        //Makes sure that the user input is greater than or equal to 25.
        while(balance<25){
            System.out.print("\nIn order to create a new checkings account, please deposit a minimum of $25. Please enter new amount:");
            balance=sc.nextDouble();
        }
        super.setBalance(balance);
    }
      
    //Overriding the withdraw method and adding $35 if the withdraw amount is greater than the amount.
    @Override
    public boolean withdraw(double am){
        if(super.getBalance()>=am){
             super.setBalance((super.getBalance()-am));
             return true;
         }else{
            double newBalance = (am-super.getBalance())+35;
            super.setBalance(-(newBalance));
            return false;
        }
    }
    
    //Overriding the display method and adding information on checking accounts specifically.
    @Override
    public String display(){
        return ("\n"+super.display()+"\nCheckings Account: " + getAccountNumber() + "\n\tBalance: "+ getBalance());
    }
}

/**
 * Class savings that extends Accounts.
 * @author Melissa Rivera-Franco
 */
class Savings extends Accounts{
    Scanner sc = new Scanner(System.in);
    private double rewards;
  
    //Constructor for savings
    public Savings(double bala, String Fn, String Ln){
        
        super(bala, Fn, Ln);
        
        //Making sure that the initial deposit is greater than $50
        while(bala<50){
            System.out.print("\nIn order to create a new savings account, a minimum of $50 is required. Please enter new amount: ");
            bala=sc.nextDouble();
        }
        super.setBalance(bala);
    }
    
    //Overriding the deposit method so each deposit will earn 2% back.
    @Override
    public boolean deposit(double am){
       double earned=((0.02)*am);
       super.deposit(am+earned);
       rewards+=earned;
       
       return true;
    }
    
    //Returns rewards.
    public double getRewards(){
        return rewards;
    }
    
    //Overriding the display method to add more information on savings account
    @Override
    public String display(){
         return ("\n"+super.display()+ "\nSavings Account: " + getAccountNumber()+"\n\tBalance: "+ getBalance() + "\n\tRewards: (2% back on each deposit after the initial one) "+ getRewards());
        }
    
}

/**
 * Class credit card that extends Accounts. 
 * @author Melissa Rivera-Franco
 */
class CreditCard extends Accounts{
    
    private double availableCredit = 500;
    
    //Constructor
    public CreditCard(String fN, String lN){
        super(fN,lN);
    }
    
    //Overriding the withdraw method to include available balance.
    @Override
    public boolean withdraw(double amount){
        if(availableCredit >= amount){
            availableCredit-=amount;
            return true;
        }
        return false;   
    }
    
    //Overriding the deposit method to include the available balance. 
    @Override
    public boolean deposit(double am){
        if((availableCredit + am)<=500){
            availableCredit+=am;
            return true;
        }
        return false;
    }
    
    //Return the available credit instead of balance.
    @Override
    public double getBalance(){
        return availableCredit;
    }
    
    //Overriding the display method to include information on credit cards.
    @Override
    public String display(){        
        double availCred = (availableCredit- getBalance());
        return ("\n"+super.display()+ "\nCredit Card: " + getAccountNumber() +"\n\tBalance: "+ getBalance()+"\t\nAvailable Credit: "+ availCred);
        }
        
    }


/**
 * Online bank for the main method.
 * @author Melissa Rivera-Franco
 */
class onlineBank{
    
    private ArrayList<Customer> customers;
    
    //Constructor
    public onlineBank(){
    customers=new ArrayList();
    }
    
    //GETTER
    public ArrayList<Customer> getCustomers(){
     return customers;
    }
    
    //Keeping the customers in one ArrayList<>
    public void addCustomer(Customer cs){
        customers.add(cs);
    }
    
    //Finding a customer based on their last name.
    public boolean exsitingCustomer(String ln){
        for(Customer ex : customers){
            String lastName = ex.getLastName();
            if(lastName.equals(ln)){
                return true;
            }
        }
        return false;
    }
    
    //Print welcome message
    public static void Welcome(){
        System.out.println("\t\t***** WELCOME TO BANK DE FRANCO *****\n");
    }
    
    //Print menu message
    public static void Menu(){        
        System.out.println("\nPlease select the option that best fit your needs by entering a number: ");
        System.out.println("\t1. Create a checkings account.");
        System.out.println("\t2. Create a savings account.");
        System.out.println("\t3. Open a new credit card.");
        System.out.println("\t4. Deposit into an exsiting account. (pay credit card)");
        System.out.println("\t5. Withdraw from an exsiting account.");
        System.out.println("\t6. Check balance on exsiting account.");
        System.out.println("\t7. Check Out");
    }
    
    public static void main(String[] args) {
       
        Scanner sc =new Scanner(System.in);
        Accounts ac = new Accounts();     
        onlineBank OB = new onlineBank();
        
        Customer defaultCustomer = new Customer("", "");
        OB.addCustomer(defaultCustomer);
        
        ArrayList<Customer> cus = OB.getCustomers();
        ArrayList<Accounts> accounts = new ArrayList<>();
        
        Welcome();
        Menu();
        
        //Making sure the input is vaild and its the same numbers on the list.
        int input = (int)ac.validInput(sc);
       
        while(input<0 || input>7){
            System.out.println("Please enter a valid menu option: ");
            input=(int)ac.validInput(sc);
        }
        
        //While loop to continue the menu display until they select 7.
        while(input != 7){
            switch(input){
                //Open a checking account
                case 1: 
                    System.out.println("\nThank you for choosing us to bank with! \nPlease keep in mind a minimum of $25 is needed to open a new checkings account. ");
                    
                    System.out.print("\nPlease enter your Last Name: ");
                    String LastName= sc.next();
                  
                    if(OB.exsitingCustomer(LastName)){
                        System.out.println("Welcome Back!");
                        for(Customer c : cus){
                            if(c.getLastName().equals(LastName)){
                                System.out.print("\nPlease enter the starting deposit: ");
                                double deposit = ac.validInput(sc);
                                
                                Checkings newCheck = new Checkings(deposit,c.getFirstName(),LastName);
                                c.addAccount(newCheck);
                                accounts.add(newCheck);
                                System.out.println("\nAccount has been added. Account Number: "+ newCheck.getAccountNumber());
                            }
                        } 
                        Menu();
                        input= (int)ac.validInput(sc);
                        break;
                    } else{
                    
                    System.out.print("\nSorry, we couldn't find a customer with that last name. Lets Create a new profile!\nPlease enter your first name: ");
                    String firstName= sc.next();
                    
                    Customer newCustomer = new Customer(firstName, LastName);
                    OB.addCustomer(newCustomer);
                    
                    System.out.print("Success! Please enter the starting balance: ");
                    double deposit= ac.validInput(sc);
                    
                    Checkings newCheckings = new Checkings(deposit, firstName, LastName);
                    newCustomer.addAccount(newCheckings);
                    accounts.add(newCheckings);
                    System.out.println("\nAccount Created. Account Number: "+ newCheckings.getAccountNumber());
                    Menu();
                    input=(int)ac.validInput(sc);
                    break;
                    }
               //Open a savings account
                case 2:
                    System.out.println("\nThank you for choosing us to bank with! \nPlease keep in mind a minimum of $50 is needed to open a new savings account.");
                    System.out.print("\nPlease enter your Last Name: ");
                    LastName= sc.next();
                  
                    if(OB.exsitingCustomer(LastName)){
                        System.out.println("Welcome Back!");
                        for(Customer c : cus){
                            if(c.getLastName().equals(LastName)){
                                System.out.print("\nPlease enter the starting deposit: ");
                                double deposit = ac.validInput(sc);
                                
                                Savings newSave = new Savings(deposit,c.getFirstName(),LastName);
                                c.addAccount(newSave);
                                accounts.add(newSave);
                                System.out.println("\nAccount has been added. Account Number: "+ newSave.getAccountNumber());
                            }
                        } 
                        Menu();
                        input= (int)ac.validInput(sc);
                        break;
                    } else{
                    
                    System.out.print("\nSorry, we couldn't find a customer with that last name. Lets Create a new profile!\nPlease enter your first name: ");
                    String firstName= sc.next();
                    
                    Customer newCustomer = new Customer(firstName, LastName);
                    OB.addCustomer(newCustomer);
                    
                    System.out.print("\nSuccess! Please enter the starting balance: ");
                    double deposit= ac.validInput(sc);
                    
                    Savings newSave = new Savings(deposit, firstName, LastName);
                    newCustomer.addAccount(newSave);
                    accounts.add(newSave);
                    System.out.println("\nAccount Created. Account Number: "+ newSave.getAccountNumber());
                    Menu();
                    input=(int)ac.validInput(sc);
                    break;
                    }
                //Create a credit card account
                case 3:
                    System.out.println("\nThank you for choosing us to bank with! \nPlease keep in mind the available credit is $500");
                    System.out.print("\nPlease enter your Last Name: ");
                    LastName= sc.next();
                  
                    if(OB.exsitingCustomer(LastName)){
                        System.out.println("Welcome Back!");
                        for(Customer c : cus){
                            if(c.getLastName().equals(LastName)){
                                CreditCard newCredit = new CreditCard(c.getFirstName(), LastName);
                                c.addAccount(newCredit);
                                accounts.add(newCredit);
                                System.out.println("Account has been added. Your new account number is: " + newCredit.getAccountNumber());
                            }
                        } 
                        Menu();
                        input= (int)ac.validInput(sc);
                        break;
                    } else{
                    
                    System.out.print("\nSorry, we couldn't find a customer with that last name. Lets Create a new profile!\nPlease enter your first name: ");
                    String firstName= sc.next();
                    
                    Customer newCustomer = new Customer(firstName, LastName);
                    OB.addCustomer(newCustomer);
                    
                    
                                       
                    CreditCard newCredit = new CreditCard(firstName, LastName);
                    newCustomer.addAccount(newCredit);
                    accounts.add(newCredit);
                    System.out.println("Success! Account is created. Account Number: "+ newCredit.getAccountNumber());
                    
                    Menu();
                    input=(int)ac.validInput(sc);
                    break;
                    }
                //Deposit in exsiting accounts
                case 4: 
                    System.out.print("\nPlease enter the account number: ");
                    int inputAccNum = (int)ac.validInput(sc);
                    
                    for(Accounts accon : accounts){
                        if(accon.getAccountNumber() == inputAccNum){
                            if(accon instanceof Savings){
                                    System.out.println("You will recieve 2% on your deposit!");
                                    System.out.print("\nPlease enter the amount you would like to deposit: ");
                                    int deposit = (int)ac.validInput(sc);
                                    accon.deposit(deposit);
                                    System.out.println("Success!Your new balance is: " + accon.getBalance());
                                    Menu();
                                    input= (int)ac.validInput(sc);
                                    break;  
                                }
                            System.out.print("\nPlease enter the amount you would like to deposit: ");
                            int deposit = (int)ac.validInput(sc);
                            if(accon.deposit(deposit)){
                                System.out.println("Success!Your new balance is: " + accon.getBalance());
                                Menu();
                                input= (int)ac.validInput(sc);
                                break;  
                            }else{
                                System.out.println("Sorry, if you are trying to pay your credit card balance, it must be an amount that will not make the available balance go over $500.\nYour balance: $" + accon.getBalance()+ " Please try again.");
                                Menu();
                                input= (int)ac.validInput(sc);
                                break; 
                            } 
                        }
                    } 
                    System.out.println("Sorry, we didn't find an account with that account number.");
                    Menu();
                    input=(int)ac.validInput(sc);
                    break;
                //Withdraw from an exsisting account
                case 5:
                    System.out.print("\nPlease enter the account number: ");
                    inputAccNum = (int)ac.validInput(sc);
                    
                    for(Accounts accon : accounts){
                        if(accon.getAccountNumber() == inputAccNum){
                            System.out.print("\nPlease enter the amount you would like to withdraw: ");
                            int withdraw = (int)ac.validInput(sc);
                            if(!(accon.withdraw(withdraw))){
                                if(accon instanceof Checkings){
                                    System.out.println("The amount you withdrew was greater than your available balance. You have been charged $35.\n\tYour new balance is: "+accon.getBalance());
                                    Menu();
                                    input=(int)ac.validInput(sc);
                                    break;
                                }
                                System.out.println("The amount you would like to withdraw is greater than the available balance. Transaction Cancelled.");
                                Menu();
                                input=(int)ac.validInput(sc);
                                break;
                            }
                           
                            System.out.println("Success!Your new balance is: " + accon.getBalance());
                            Menu();
                            input= (int)ac.validInput(sc);
                            break;
                        }
                    } 
                    System.out.println("Sorry, we didn't find an account with that account number.");
                    Menu();
                    input=(int)ac.validInput(sc);
                    break;
                //Checking a balance on an exsisting account
                case 6:
                    System.out.print("\nPlease enter the account number: ");
                    inputAccNum = (int)ac.validInput(sc);
                    
                     for(Accounts accon : accounts){
                        if(accon.getAccountNumber() == inputAccNum){
                            System.out.println(accon.display());
                            Menu();
                            input= (int)ac.validInput(sc);
                            break;
                        }
                     }
                     System.out.println("Sorry we could not find an account with that number. Please try again.");
                     Menu();
                     input= (int)ac.validInput(sc);
                     break;             
            }
            
        }
        
        //Creating a string to print out on the file receipt
        String str = "\t\t**** Thank you for banking with us ****\n";
        for(Accounts uac : accounts){
            str+=uac.display();
        }
        
        str+="\n\n\t\t****We hope to see you soon!****";
        
        System.out.println("Thank you for banking with us! Your receipt is named \"BankingReceipt.txt\"");
        
        //File Output
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("BankingReceipt.txt"));
            writer.write(str);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(onlineBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
