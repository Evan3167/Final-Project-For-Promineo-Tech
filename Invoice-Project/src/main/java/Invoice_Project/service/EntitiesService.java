package Invoice_Project.service;

import java.util.List;
import java.util.NoSuchElementException;
import Invoice_Project.Entities.Customer;
import Invoice_Project.Entities.Inventory;
import Invoice_Project.Entities.Invoice;
import Invoice_Project.Entities.InvoiceDetail;
import Invoice_Project.dao.InvoiceDao;
import Invoice_Project.errorHandler.DbException;

public class EntitiesService {
  

    static InvoiceDao invoiceDao = new InvoiceDao();
    
    
    
   public static List<Invoice> fetchAllInvoices() {
    return invoiceDao.fetchAllInvoices();
  }//fetchInvoicesEnd
   
   public static Invoice fetchInvoiceById(Integer invoiceId) {
     return invoiceDao.fetchInvoiceById(invoiceId).orElseThrow(
             () -> new NoSuchElementException("Invoice with invoice ID= " + invoiceId + " does not exist."));
   }
   
   public static Inventory fetchInventoryById(Integer inventory_Id) {
     return invoiceDao.fetchInventoryById(inventory_Id).orElseThrow(
             () -> new NoSuchElementException("Inventory with inventory ID= " + inventory_Id + " does not exist."));

  }
   
   public static void modifyInventory(Inventory inventory) {
     if(!invoiceDao.modifyInventory(inventory)) {
       throw new DbException("Inventory with ID= " + inventory.getInventory_id() + " does not exist."); 
     }
   }

  public static Invoice addInvoice(Invoice invoice) {
    return invoiceDao.addInvoice(invoice);
  }

  public static Customer addCustomer(Customer customer) {
    return invoiceDao.addCustomer(customer);
  }

  public static Inventory addInventory(Inventory inventory) {
    return invoiceDao.addInventory(inventory);
  }

  public static void modifyCustomer(Customer customer) {
    if(!invoiceDao.modifyCustomer(customer)) {
      throw new DbException("Customer with ID= " + customer.getCustomer_id() + " does not exist.");
    }
    
  }

  public static Customer fetchCustomerById(Integer customer_id) {
      return invoiceDao.fetchCustomerById(customer_id).orElseThrow(
          () -> new NoSuchElementException("Customer with Customer ID=" + customer_id + "does not exist."));
  }

  public static List<Inventory> fetchAllInventory() {
    return invoiceDao.fetchAllInventory();
  }

  public static void modifyInvoice(Invoice invoice) {
    if(!invoiceDao.modifyInvoice(invoice)) {
      throw new DbException("Invoice with ID=" + invoice.getInvoice_id() + "does not exist.");
    }
    
  }

  public static List<Customer> fetchAllCustomer() {
    return invoiceDao.fetchAllCustomers();
  }

  public static void deleteCustomer(Integer customerId) {
    if(!invoiceDao.deleteCustomer(customerId)) {
      throw new DbException("Customer with ID= " + customerId + " does not exist.");
    }
    
  }

  public static void deleteInvoice(Integer invoiceId) {
   if(!invoiceDao.deleteInvoice(invoiceId)) {
     throw new DbException("Invoice with ID= " + invoiceId + " does not exist.");
   }
    
  }

  public static List<InvoiceDetail> fetchInvoiceDetails() {
    return invoiceDao.fetchInvoiceDetails();
  }

  public static InvoiceDetail fetchInvoiceDetailsById(Integer invoiceDetailsId) {
   return invoiceDao.fetchInvoiceDetailsById(invoiceDetailsId).orElseThrow(
       ()-> new NoSuchElementException("Invoice with ID= " + invoiceDetailsId + "oes Not exist."));
  }

  

}
