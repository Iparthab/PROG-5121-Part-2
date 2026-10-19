package com.mycompany.assignment2;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Assignment2 {
    
    public static class Login {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String cellPhoneNumber;
        
        public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.cellPhoneNumber = cellPhoneNumber;
        }
        
        public boolean checkUserName() {
            return username.contains("_") && username.length() <= 5;
        }
        
        public boolean checkPasswordComplexity() {
            boolean lengthCheck = password.length() >= 8;
            boolean uppercaseCheck = false;
            boolean numberCheck = false;
            boolean specialCharCheck = false;
            
            for(int i = 0; i < password.length(); i++) {
                char ch = password.charAt(i);
                if (Character.isUpperCase(ch)) uppercaseCheck = true;
                else if (Character.isDigit(ch)) numberCheck = true;
                else if (!Character.isLetterOrDigit(ch)) specialCharCheck = true;
            }
            return lengthCheck && uppercaseCheck && numberCheck && specialCharCheck;
        }
        
        public boolean checkCellPhoneNumber() { 
            String regex =  "^\\+27\\d{9}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.cellPhoneNumber);
            return matcher.matches();
            
        }
        
        public String registerUser() {
            if(!checkUserName()) {
                return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
            }
            if (!checkPasswordComplexity()) { 
                return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
            }
            return "Username and password successfully capturerd.";
        }
        // Adding my second commit 
        public String checkCellPhoneRegistration() {
            if (checkCellPhoneNumber()) {
                return "Cell phone number successfully added.";
            } else{
                return "Cell phone number incorrectly formatted or does not contain international code.";
            }
        }
        //third commit
        public boolean loginUser(String enteredUsername, String enteredPassword) {
            return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
        }
        // fourth commit
        public String returnLoginStatus(boolean isLoggedIn) {
            if (isLoggedIn) {
                return "Welcome " + firstName + "," + lastName + " it is great to see you again.";
            } else {
                return "Username or password incorrect, please try again.";
            }
        }
    }
    // fifth commit
    public static class Message {
        
        private String messageID;
        private int messageNumber;
        private String recipient;
        private String message;
        private String messageHash;
        
        public Message(int messageNumber, String recipient, String message) {
            
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.message = message;
            
            generateMessageID();
            messageHash = createMessageHash();
        }
        
        private void generateMessageID() { 
            
            Random random = new Random();
            
            long id = 1000000000L +
                    (long)(random.nextDouble()* 9000000000L);
            
            messageID = String.valueOf(id);
        } 
        
        public boolean checkMessageID() {
            return messageID.length() <= 10;
        } 
        
        public String checkRecipientCell() { 
            
            if(recipient.matches("^\\+27\\d{9}$")) { 
                return "Cell phone number successfully captured.";
            } 
            
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
        
        public String checkMessageLength() { 
            
            if(message.length() <= 250) {
                return "Message ready to send.";
            }
            
            int excess = message.length() - 250;
            
            return "Message exceeds 250 characters by"
                   +excess
                    + ", please reduce the size.";
        }
        
        public String createMessageHash() { 
            
            String[] words = message.trim().split("\\s+");
            
            String firstWord = words[0].toUpperCase();
            String lastWord = words[words.length - 1].toUpperCase();
            
            return messageID.substring(0, 2)
                    + ":" + messageNumber
                     + ":" + firstWord + lastWord;
        } 
        
        public String sentMessage(int choice) { 
            
            switch(choice) {
                
                case 1:
                    return "Message successfully sent.";
                    
                case 2:
                    return "Press 0 to delete the message.";
                    
                case 3:
                    return "Message successfully stored.";
                    
                default:
                    return "Invalid choice.";
                    
                    
            }
        } 
        
        public void storeMessage() {
            
            try { 
                
                FileWriter writer = 
                        new FileWriter("storedMessages.json", true);
                
                writer.write(
                        "{\n" +
                        "\"MessageID\":\"" + messageID + "\",\n" + 
                         "\"Recipient\":\"" + recipient + "\",\n" +   
                          "\"Message\":\"" + message + "\"\n" +
                          "}\n"
                               
                );
                
                writer.close();
                
            } catch (IOException e) {
                
                System.out.println("Error storing message.");
            }
        } 
        
        public String printMessage() {
            
            return "Message ID: " + messageID
                    + "\nMessage Hash: " + messageHash
                    + "\nRecipient: " + recipient
                    + "\nMessage: " + message;
        } 
        
        public String getMessageID() { 
            return messageID;
        } 
        
        public String getMessageHash() { 
            return messageHash;
        }
    }
    
    static int totalMessagesSent = 0;
    
    static ArrayList<Message> sentMessages = 
            new ArrayList<>();
    
    static ArrayList<Message> storedMessages = 
            new ArrayList<>();
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- Register a New Account ---");
        System.out.print("Enter First Name: ");
        String fName = scanner.nextLine();
        
        // sixth commit 
        System.out.print("Enter Last Name: ");
        String lName = scanner.nextLine();
        
        
        System.out.print("Enter a Username: ");
        String uName = scanner.nextLine();
        
        System.out.print("Enter a Password: ");
        String pass = scanner.nextLine();
        
        System.out.print("Enter a South African Cell Number (e.g. +27...");
        String cell = scanner.nextLine();
        
        Login userLogin = new Login(fName, lName, uName, pass, cell);
        
        System.out.println("\n--- Registration Status ---");
        System.out.println(userLogin.registerUser());
        System.out.println(userLogin.checkCellPhoneRegistration());
        
        if (userLogin.checkUserName() && userLogin.checkPasswordComplexity() && userLogin.checkCellPhoneNumber()  ) { 
           System.out.println("\n--- Login ---");
           System.out.print("Enter Username: ");
           String loginUser = scanner.nextLine(); 
           System.out.print("Enter Password: ");
           String loginPass =scanner.nextLine();
       
           
           boolean isLoggedIn = userLogin.loginUser(loginUser, loginPass);
           System.out.println(userLogin.returnLoginStatus(isLoggedIn));
           
           if (isLoggedIn) {
               
               System.out.println("\nWelcome to QuickChat."); 
               
               System.out.print("How many messages would you like to enter? ");
               int numberOfMessages = Integer.parseInt(scanner.nextLine()); 
               
               boolean running = true;
               
               while (running) { 
                   
                   System.out.println("n===== QuickChat Menu =====");
                   System.out.println("1. Send Messages");
                   System.out.println("2. Show Recently Sent Messages");
                   System.out.print("Choose an option: ");
                   
                   int choice = Integer.parseInt(scanner.nextLine());
                   
                   switch (choice) { 
                       
                       case 1:
                           
                           for (int i = 0; i < numberOfMessages; i++) {
                               
                               System.out.println("\nMessage " + (i + 1));
                               
                               System.out.print("Enter recipient number: ");
                               String recipient = scanner.nextLine();
                               
                               System.out.print("Enter message: ");
                               String messageText = scanner.nextLine();
                               
                               Message msg = new Message(
                               i + 1, 
                               recipient, 
                               messageText); 
                               
                               System.out.println(msg.checkRecipientCell());
                               System.out.println(msg.checkMessageLength());
                               
                               if (messageText.length() <= 250) { 
                                   
                                   System.out.println("\n1.Send Message");
                                   System.out.println("2. Disregard Message");
                                   System.out.println("3. Store Message");
                                   System.out.print("Choose option: ");
                                   
                                   int sendChoice = 
                                           Integer.parseInt(scanner.nextLine());
                                   
                                   System.out.println(
                                   msg.sentMessage(sendChoice));
                                   
                                   if( sendChoice == 1) { 
                                       
                                       sentMessages.add(msg);
                                       totalMessagesSent++; 
                                       
                                       System.out.println(msg.printMessage());
                                   }
                                   
                                   if (sendChoice == 3) {
                                       
                                       storedMessages.add(msg);
                                       msg.storeMessage();
                                   }
                               }
                           
                           }
                           
                           System.out.println( 
                           "\nTotal messages sent: "
                           + totalMessagesSent); 
                           
                           break;
                           
                           
                       case 2: 
                           System.out.println("Coming Soon.");
                           break;
                           
                       case 3:
                           running = false;
                           System.out.println("Exiting QuickChat...");
                           break;
                           
                       default:
                           System.out.println("Invalid option.");
                   }
               }
           }
        } else{
            System.out.println("\nRegistration failed. Please restart the application and fix formatting errors.");
        }
        scanner.close();
        
    }
}