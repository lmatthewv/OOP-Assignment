///
/// Student name: Luis Vitoria ID: 16450052
///Pledge of Honour: I pledge by honour that this program is solely my own work.
///

package my_package;

import static java.lang.System.out;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	//This program reads a text document and processes the information into a list of Bank Account objects.
	//This list of objects can be updated using multiple commands in the command line.
	 public static void main(String[] args) {
		 try {
		 new BankAccountApp("data/AssignmentData.txt");
		 } catch (IOException ioe) {
		 out.printf("Missing file: %s/data/AssignmentData.txt? \n\n",
		 new Main().getClass().getPackage().getName());
		 }
	 }
}
	class BankAccountApp {
	 private List<BankAccount> accounts;
	 public void setAccounts(List<BankAccount> accounts) {
		 this.accounts = accounts;
	 }
	 public List<BankAccount> getAccounts(){
		 return accounts;
	 }

	 public BankAccountApp(String filename) throws IOException {
	 readAccountData(filename);
	 displayMenu();
	 int opt = 0;
	 Scanner input = new Scanner(System.in);
	 while (true){

	 out.print("\nSelect an option: ");
	 opt = input.nextInt();// format exception if string
	 if (opt < 0 || opt > 7) {
	 out.println("Invalid. Must be 1 - 7 (or 0 for menu).");
	 continue;
	 }
	 switch (opt)
	 {
	 case 0: displayMenu(); break;
	 case 1: doDeposit(); break;
	 case 2: doWithdraw(); break;
	 case 3: displayHighestBalanceAccount(); break;
	 case 4: displayMostActiveAccount(); break;
	 case 5: displayAllAccounts(); break;
	 case 6:
	 {
	 int total = getTotalTransactions(accounts);
	 out.println("Total transactions: " + total);
	 break;
	 }
	 case 7: farewell(); return;
	 }
	 }
	 }
	 public void readAccountData(String fn) throws IOException {
	 // complete method
		 //This class reads the text document and processes the information into a list of bank account objects.
		 accounts = new ArrayList<BankAccount>();
		 Path path = new File(fn).toPath();
		 List<String> content = null;
			//Read the file
			try {
				content = Files.readAllLines(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//each line is split and used to create a bank account object within the accounts list.
		 for (String line : content) {
				String[] items = line.split(",");
				String name = items[0];
				int age = Integer.valueOf(items[1]);
				String accountID = items[2];
				double balance = Double.valueOf(items[3]);
				int accountType = Integer.valueOf(items[4]);
				accounts.add(new BankAccount(name, age, accountID, balance, accountType));
			}
	 }
	 public void displayAllAccounts() {
	 // complete method
		 //This method loops through every account and displays their information.
		 out.println("Display All Accounts");
		 out.println("------------All Bank Accounts----------");
		 for (int x = 0; x < accounts.size(); x++) {
			 accounts.get(x).displayInfo();
			 out.println("");
		 }

	 }
	 public void displayHighestBalanceAccount() {
	 // complete method
		 //This method calculates the bank account with the largest balance and displays its information.
		 double balance = 0;
		 int num = 0;
		 //Loop through each account.
		 for (int x = 0; x < accounts.size(); x++) {
				if (accounts.get(x).getBalance() > balance) {
				 balance = accounts.get(x).getBalance();
				 num = x;
			 }

		}
		 //Display the largest balance's info.
		 accounts.get(num).displayInfo();

	 }
	 public void displayMostActiveAccount() {
	 // complete method
		 //This method calculates the most active account.
		 int transactions = 0;
		 int num = 0;
		 //Loop through the accounts.
		 for (int x = 0; x < accounts.size(); x++) {
			 if (accounts.get(x).getTransactionCounter() > transactions) {
				 num = x;
			 }
		 }

		 //Display the correct account info.
		 accounts.get(num).displayInfo();
	 }
	 public int getTotalTransactions(List<BankAccount> list) {
	 // complete method (recursive method)
		 //This method recursively calculates the account with the most transactions and displays its information.
		 if(list == null || list.size() == 0) {
			 return 0;
		 }
		 else {
			 int fstElem = list.get(0).getTransactionCounter();
			 List<BankAccount> subList = list.subList(1,  list.size());
			 int total = fstElem + getTotalTransactions(subList);
			 return total;
		 }
	 }
	 public void doDeposit() {
		 // complete method
		 //This method deposits an amount into any bank account.
		 Scanner input = new Scanner(System.in);
		 //Ask the user for a bank id.
		 out.print("Enter an account id for deposit: ");
		 String id = input.nextLine();
		 boolean ifID = false;
		 int x = 0;
		 //Loop through he accounts and check the Id is correct.
		 for (x = 0; x < accounts.size(); x++) {
			 if (accounts.get(x).getAccountID().contains(id)) {
				 ifID = true;
				 break;
			 }
			 else if (!accounts.get(x).getAccountID().contains(id)){
				 ifID = false;
			 }
		 }

		 //If id is correct then ask the user for a deposit amount.
		 if (ifID == true) {
			 out.print("Enter an amount for deposit: ");
			 double amt = input.nextDouble();
			 while (amt < 0) {
				 out.println("Invalid input. Try again.");
				 out.print("Enter an amount for deposit: ");
				 amt = input.nextDouble();
			 }

			 //If the payment is successful or not then the corresponding message is displayed.
			 if (accounts.get(x).deposit(amt) == true) {
				 out.println("Payment successful");
			 }
			 else {
				 out.println("Payment failed");
			 }
		 }

		 else {
			 out.println("Account ID does not exist: " + id);
		 }

	 }
		 public void doWithdraw() {
		 // complete method
			 //This method withdraws an amount of money from any account.
			 Scanner input = new Scanner(System.in);
			 //Ask the user for an id.
			 out.print("Enter an account id for withdraw: ");
			 String id = input.nextLine();
			 boolean ifID = false;
			 int x = 0;
			//Loop through he accounts and check the Id is correct.
			 for (x = 0; x < accounts.size(); x++) {
				 if (accounts.get(x).getAccountID().contains(id)) {
					 ifID = true;
					 break;
				 }
				 else if (!accounts.get(x).getAccountID().contains(id)){
					 ifID = false;
				 }
			 }

			//If id is correct then ask the user for a withdraw amount.
			 if (ifID == true) {
				 out.print("Enter an amount for withdraw: ");
				 double amt = input.nextDouble();
				 while (amt < 0) {
					 out.println("Invalid input. Try again.");
					 out.print("Enter an amount for withdraw: ");
					 amt = input.nextDouble();
				 }

				 //If the payments was successful or not then the corresponding payment is displayed.
				 if (accounts.get(x).withdraw(amt) == true) {
					 out.println("Payment successful");
				 }
				 else {
					 out.println("Payment failed");
				 }
			 }

			 else {
				 out.println("Account ID does not exist: " + id);
			 }

		 }
		 public void farewell() {
		 out.println("\t Thanks for using the service. Bye!");
		 }
		 public void displayMenu() {
		 out.println("\n\n*********************************");
		 out.println("* Bank Account Operation Menu *");
		 out.println("***********************************\n");
		 out.println("0. Display Menu");
		 out.println("1. Deposit");
		 out.println("2. Withdraw");
		 out.println("3. Display Highest Balance Account");
		 out.println("4. Display Most Active Account");
		 out.println("5. Display All Accounts");
		 out.println("6. Display Total Number of Transactions");
		 out.println("7. Exit");
		 }
		}// End of class BankAccountApp
		class BankAccount extends Person {
		 // complete class
			//This class creates a BankAccount object which is an extension of a person object.
			private String accountID;
			private double balance;
			private int accountType;
			private int transactionCounter;
			private double fee;

			//Getters and setters for a BankAccount object.
			public String getAccountID() {
				return accountID;
			}

			public double getBalance() {
				return balance;
			}

			public int getAccountType() {
				return accountType;
			}

			public void setAccountID (String accountID) {
				this.accountID = accountID;
			}

			public void setBalance (double balance) {
				this.balance = balance;
			}

			public void setAccountType (int accountType) {
				this.accountType = accountType;
			}

			public int getTransactionCounter() {
				return transactionCounter;
			}

			public double getFee() {
				return fee;
			}

			public void setTransactionCounter (int transactionCounter) {
				this.transactionCounter = transactionCounter;
			}

			public void setFee (double fee) {
				this.fee = fee;
			}

			//Constructor which contains the variables in the person object as well as the correct fee amount.
			public BankAccount(String name, int age, String accountID, double balance, int accountType) {
				super(name, age);
				this.accountID = accountID;
				this.balance = balance;
				this.accountType = accountType;
				if (accountType == 0) {
					fee = 3.5;
				}

				else if (accountType == 1) {
					fee = 1;
				}

				else if (accountType == 2) {
					fee = 0;
				}
			}

			//A method for displaying information on a certain account to the console.
			@Override public void displayInfo() {
				super.displayInfo();
				out.println("accountID: " + accountID);

				String type = "";
				if (accountType == 0) {
					type = "Regular";
				}

				else if(accountType == 1) {
					type = "Premium";
				}

				else if (accountType == 2){
					type = "VIP";
				}

				out.println("Account Type: " + type);
				out.printf("Account Balance: %.2f\n", balance);
			}

			//A method for depositing money into a bank account object.
			 boolean deposit(double amt) {
				 boolean deposit = false;
				 //If the balance and deposit amount cannot pay the fee...
				 if (amt + balance < fee) {
					 //The payment is unsuccessful
					 deposit = false;
				 }
				 else {
					 //If it can the payment is successful, the amount is deposited, the balance is deducted, and the transaction counter is increased by one.
					 deposit = true;
					 balance += amt;
					 balance -= fee;
					 transactionCounter++;
				 }

				 //Return whether the payments is successful or not.
				 return deposit;
			 }

			//A method for withdrawing money from a bank account object.
			 boolean withdraw (double amt) {
				 boolean withdraw;
				//If the balance cannot pay the amount and fee...
				 if (amt + fee > balance) {
					 //The payment is unsuccessful
					 withdraw = false;
				 }
				 else {
					//If it can the payment is successful, the amount and fee is withdrawn and the transaction counter is increased by one.
					 withdraw = true;
					 balance -= fee + amt;
					 transactionCounter++;
				 }

				//Return whether the payments is successful or not.
				 return withdraw;
			 }


		}
		class Person {
		 // complete class
			//This class creates a Person object
			private String name;
			private int age;

			//Constructor for the person object
			public Person(String name, int age) {
				this.name = name;
				this.age = age;
			}

			//Getters and setters
			public String getName() {
				return name;
			}

			public int getAge() {
				return age;
			}

			public void setName (String name) {
				this.name = name;
			}

			public void setAge (int age) {
				this.age = age;
			}

			//A method for displaying information for a person object.
			public void displayInfo() {
				out.println("Name: " + name);
				out.println("Age: " + age);
			}
		}