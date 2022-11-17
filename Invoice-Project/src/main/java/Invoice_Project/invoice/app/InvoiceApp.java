package Invoice_Project.invoice.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Invoice_Project.ComponentScanMarker.ComponentScanMarker;
import Invoice_Project.Entities.Customer;
import Invoice_Project.Entities.Inventory;
import Invoice_Project.Entities.Invoice;
import Invoice_Project.Entities.InvoiceDetail;
import Invoice_Project.errorHandler.DbException;
import Invoice_Project.service.EntitiesService;
import Invoice_Project.service.InvoiceService;


@SpringBootApplication(scanBasePackageClasses = {ComponentScanMarker.class})
public class InvoiceApp {
  private Scanner scanner = new Scanner(System.in);
//  private EntitiesService entitiesService = new EntitiesService();
  private Invoice curInvoice;
  private Customer curCustomer;
  private InvoiceDetail curInvoiceDetail;
  private Inventory curInventory;

  //@formatter:off
  private List<String> operations = List.of(
      "1) Add Inventory",
      "2) Add Customer",
      "3) Add Invoice",
      "4) Modify Inventory",
      "5) Modify Customer",
      "6) Modify Invoice",
      "7) Delete Customer",
      "8) Delete Invoice",
      "9) View Invoice Details"
      
      );
  
  //@formatter:on



  public static void main(String[] args) {
    SpringApplication.run(InvoiceApp.class, args);
    new InvoiceApp().displayMenu();

  }// MainMethodEnd

  private void displayMenu() {
    boolean done = false;

    while (!done) {

      try {
        int operation = getOperation();
        switch (operation) {
          case -1:
            done = exitMenu();
            break;

          case 1:
            addInventory();
            break;

          case 2:
            addCustomer();
            break;

          case 3:
            addInvoice();
            break;
          
          case 4:
            modifyInventory();
            break;
            
          case 5:
            modifyCustomer();
            break;

          case 6:
            modifyInvoice();
            break;

          case 7:
            deleteCustomer();
            break;

          case 8:
            deleteInvoice();
            break;

          case 9:
            viewInvoiceDetails();

        }
      } // TryEnd
      catch (Exception e) {
        System.out.println("\nError: " + e.toString() + "Try again.");
      }
    } // WhileEnd

  }// displayMenuEnd


//  private void selectInvoice() {
//    listInvoice();
//    
//    Integer invoiceId = getIntInput("Enter a Invoice Id to chose a invoice");
//    
//    curInvoice = null;
//    
//    curInvoice = EntitiesService.fetchInvoiceById(invoiceId);
//    
//  }

  

 

  private void deleteInvoice() {
    listInvoice();
    
    
    Integer invoiceId = getIntInput("Enter the ID of the Invoice you wish to delete.");
    
    EntitiesService.deleteInvoice(invoiceId);
    System.out.println("Invoice " + invoiceId + " was deleted.");
    
    if(Objects.nonNull(curInvoice) && curInvoice.getInvoice_id().equals(invoiceId)) {
      curInvoice = null;
    }
    
  }

  private void deleteCustomer() {
   listCustomer();
   
   Integer customerId = getIntInput("Enter the ID of the customer you wish to delete.");
   
   EntitiesService.deleteCustomer(customerId);
   System.out.println("Customer " + customerId + " was deleted.");
   
   if(Objects.nonNull(curCustomer) && curCustomer.getCustomer_id().equals(customerId)) {
     curCustomer = null;
   }
  }

  private void modifyInvoice() {
    listInvoice();
    
    Integer invoiceId = getIntInput("Enter a Invoice Id to chose a invoice");
    
    curInvoice = null;
    
    curInvoice = EntitiesService.fetchInvoiceById(invoiceId);
    
    
    
    if(Objects.isNull(curInvoice)) {
      System.out.println("\nPlease select a Invoice.");
      return; 
  }
    //formatter:off
    
    Integer customerId = getIntInput("Enter the CustomersId: [" 
    + curInvoice.getCustomer_id() + "]");    
    BigDecimal totalPrice = getDecimalInput("Enter the Total Price of the invoice: [" 
    + curInvoice.getTotal_price() + "]" );
    String openDate = getStringInput("Enter the Start Date of the invoice: [" 
    + curInvoice.getOpen_date() + "]");
    String closeDate = getStringInput("Enter the Close Date of the invoice: [" 
    + curInvoice.getClose_date() + "]");
    String comments = getStringInput("Enter any comments you have for the invoice: [" 
    + curInvoice.getComments() + "]");
    
    Invoice invoice = new Invoice();
    
    invoice.setInvoice_id(curInvoice.getInvoice_id());
    invoice.setCustomer_id(curInvoice.getCustomer_id());
    invoice.setTotal_price(Objects.isNull(totalPrice) ? curInvoice.getTotal_price() : totalPrice);
    invoice.setOpen_date(Objects.isNull(openDate) ? curInvoice.getOpen_date() : openDate);
    invoice.setClose_date(Objects.isNull(closeDate) ? curInvoice.getClose_date() : closeDate);
    invoice.setComments(Objects.isNull(comments) ? curInvoice.getComments() : comments);
    
    
    EntitiesService.modifyInvoice(invoice);
    
    curInvoice = EntitiesService.fetchInvoiceById(curInvoice.getInvoice_id());
    
    //formatter:on
    
    
    
    
  }

  private void modifyCustomer() {
    listCustomer();
    Integer customerId = getIntInput("Enter a Customer Id to chose a customer");
    
    curCustomer = null;
    
    curCustomer = EntitiesService.fetchCustomerById(customerId);
    
    
    
    if(Objects.isNull(curCustomer)) {
      System.out.println("\nPlease select a Customer.");
      return; 
  }
    //formatter:off
    String name = getStringInput("Enter the customer name [" 
    + curCustomer.getCustomer_name() + "]");
    String address = getStringInput("Enter the address of the customer [" 
    + curCustomer.getAddress() +"]");
    String phoneNumber = getStringInput("Enter the customers phone Number [" 
    + curCustomer.getPhone_number() + "]");
    
    Customer customer = new Customer();
    
    customer.setCustomer_id(curCustomer.getCustomer_id());
    customer.setCustomer_name(Objects.isNull(name) ? curCustomer.getCustomer_name() : name);
    customer.setAddress(Objects.isNull(address) ? curCustomer.getAddress() : address);
    customer.setPhone_number(Objects.isNull(phoneNumber) ? curCustomer.getPhone_number() : phoneNumber);
    
    EntitiesService.modifyCustomer(customer);
    
    curCustomer = EntitiesService.fetchCustomerById(curCustomer.getCustomer_id());
    
    //formatter:on
    
    
    
  }

  private void modifyInventory() {
    
    listInventory();
    Integer inventoryId = getIntInput("Enter a Item Id to chose Item");
    
    curInventory = null;
    
    curInventory = EntitiesService.fetchInventoryById(inventoryId);
    
    if (Objects.isNull(curInventory)) {
      System.out.println("\nPlease select a Item.");
      return;
    }
 // formatter:off
    String itemName = getStringInput("Enter the Item name [" 
 + curInventory.getItem_name() + "]");

    Integer stock = getIntInput("Enter the Updated stock number [" 
    + curInventory.getStock() + "]");
    
    BigDecimal itemPrice = getDecimalInput("Enter the Price of the Item [" 
    + curInventory.getItem_price() + "]");
    
    
    Inventory inventory = new Inventory();
    
    inventory.setInventory_id(curInventory.getInventory_id());
    inventory.setItem_name(
        Objects.isNull(itemName) ? curInventory.getItem_name() : itemName);
    
    inventory.setStock(Objects.isNull(stock) ? curInventory.getStock() : stock);
    inventory.setItem_price(
        Objects.isNull(itemPrice) ? curInventory.getItem_price() : itemPrice);
    
    
    EntitiesService.modifyInventory(inventory);
    
    curInventory = EntitiesService.fetchInventoryById(curInventory.getInventory_id());

  }
  // formatter:on


  private void addInvoice() {
    Integer customerId = getIntInput("Enter the Customers Id");
    BigDecimal totalPrice = getDecimalInput("Enter Total Price");
    String openDate = getStringInput("Enter the start date of the invoice");
    String closeDate = getStringInput("Enter the end date of the invoice");
    String comments = getStringInput("Enter any comments");

    
    Invoice invoice = new Invoice();
    
    invoice.setCustomer_id(customerId);
    invoice.setTotal_price(totalPrice);
    invoice.setOpen_date(openDate);
    invoice.setClose_date(closeDate);
    invoice.setComments(comments);
    
    Invoice dbInvoice = EntitiesService.addInvoice(invoice);
    System.out.println("You have successfully created a Invoice: " + "\n" + dbInvoice);


  }

  private void addCustomer() {
    String name = getStringInput("Enter the customer name");
    String address = getStringInput("Enter the address of the customer");
    String phoneNumber = getStringInput("Enter the customers phone Number");

    Customer customer = new Customer();
   
    customer.setCustomer_name(name);
    customer.setAddress(address);
    customer.setPhone_number(phoneNumber);
    
    Customer dbCustomer = EntitiesService.addCustomer(customer);
    System.out.println("You have successfully added a Customer: " + "\n" + dbCustomer);
    
  }

  private void addInventory() {
    String name = getStringInput("Enter the item name");
    Integer stock = getIntInput("Enter how many of the items there are");
    BigDecimal price = getDecimalInput("enter the price");
    
    
    Inventory inventory = new Inventory();
    
    inventory.setItem_name(name);
    inventory.setStock(stock);
    inventory.setItem_price(price);
    
    Inventory dbInventory = EntitiesService.addInventory(inventory);
    System.out.println("You have successfully Created An item in the Inventory: " + "\n" + dbInventory);


  }
  
//  private String Date() {
//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
//    LocalDateTime now = LocalDateTime.now(); 
//    return dtf.format(now);
//    
//  }
  

  private boolean exitMenu() {
    System.out.println("\nExiting the Menu. TTFN!");
    return true;
  }

  private BigDecimal getDecimalInput(String prompt) {
    String input = getStringInput(prompt);

    if (Objects.isNull(input)) {
        return null;
    }

    try {
        return new BigDecimal(input).setScale(2);
    } catch (NumberFormatException e) {
        throw new DbException(input + "is not a vaild decimal number. ");
    }
}

  private int getOperation() {
    printOperations();
    Integer op = getIntInput("\nEnter an Operation Number (press Enter to quit)");

    return Objects.isNull(op) ? -1 : op;
  }

  private void printOperations() {
    System.out.println();
    System.out.println("Here's what you can do: ");

    operations.forEach(op -> System.out.println("   " + op));

//    if (Objects.isNull(curInventory)) {
//      System.out.println("\nYou are not working with an Inventory.");
//    } else 
//      System.out.println("\nYou are working with " + curInventory);
//    
//    if (Objects.isNull(curInvoice)) {
//      System.out.println("\nYou are not working with an Invoice.");
//    } else {
//      System.out.println("\nYou are working with " + curInvoice);
//    }
//    if (Objects.isNull(curCustomer)) {
//      System.out.println("\nYou are not working with a Customer.");
//    } else 
//      System.out.println("\nYou are working with " + curCustomer);
  }

  private Integer getIntInput(String prompt) {
    String input = getStringInput(prompt);

    if (Objects.isNull(input)) {
      return null;
    }
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new DbException(input + " is not a valid number.");
    }
  }

  private String getStringInput(String prompt) {
    System.out.print(prompt + ": ");
    String line = scanner.nextLine();

    return line.isBlank() ? null : line.trim();
  }
  
//  private void selectInventory() {
//    listInventory();
//    Integer inventoryId = getIntInput("Enter a Item Id to chose Item");
//    
//    curInventory = null;
//    
//    curInventory = EntitiesService.fetchInventoryById(inventoryId);
//  }
  
     
  private void listInventory() {
    List<Inventory> inventories = EntitiesService.fetchAllInventory();

    System.out.println("\nInventory: ");
    
    inventories.forEach(inventory -> System.out.println("  " + inventory.getInventory_id()
    + ": " + inventory.getItem_name() + ". Stock: " + inventory.getStock()));

  } 
//  private void selectCustomer() {
//    listCustomer();
//    Integer customerId = getIntInput("Enter a Customer Id to chose a customer");
//    
//    curCustomer = null;
//    
//    curCustomer = EntitiesService.fetchCustomerById(customerId);
//  }
  
  
  private void listCustomer() {
    List<Customer> customers = EntitiesService.fetchAllCustomer();

    System.out.println("\nCustomer: ");
    
    customers.forEach(customer -> System.out.println("  " + customer.getCustomer_id()
    + ": " + customer.getCustomer_name()));

  } 
  
//  private void browseAllInvoices() {
//    listInvoice();
//    
//    Integer invoiceId = getIntInput("Enter a Invoice Id to chose a invoice");
//    
//    curInvoice = null;
//    
//    curInvoice = EntitiesService.fetchInvoiceById(invoiceId);
//
//    
//  }
  
  private void listInvoice() {
    List<Invoice> invoices = EntitiesService.fetchAllInvoices();
    
    System.out.println("\nInvoice: ");
    
    invoices.forEach(invoice -> System.out.println("InvoiceID: " + invoice.getInvoice_id()
    + "  CustomerId: " + invoice.getCustomer_id()));
  }
  
  private void viewInvoiceDetails() {
    ListInvoiceAndInformation();
    
    Integer invoiceDetailsId = getIntInput("Enter the Invoice Id you would like to see");
    
    curInvoiceDetail = null;
    
    curInvoiceDetail = EntitiesService.fetchInvoiceDetailsById(invoiceDetailsId);
    
  }
  
  
  private void ListInvoiceAndInformation() {
    List<InvoiceDetail> invoice_details = EntitiesService.fetchInvoiceDetails();
     
    System.out.println("\nInvoice: ");
    
    invoice_details.forEach(invoice_detail -> System.out.println("InvoiceID: " + invoice_detail.getInvoice_id()
    + "  InventoryId: " + invoice_detail.getInventory_id()));
  }
  
  

}// ClassEnd
