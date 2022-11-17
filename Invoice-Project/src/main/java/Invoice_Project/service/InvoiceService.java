package Invoice_Project.service;

import java.util.List;
import java.util.NoSuchElementException;
import Invoice_Project.Entities.Inventory;
import Invoice_Project.Entities.Invoice;
import Invoice_Project.dao.InvoiceDao;
import Invoice_Project.errorHandler.DbException;


public interface InvoiceService {

 
  
  
  public List<Invoice> fetchInvoices(Invoice invoice);
  
}//ClassEnd
