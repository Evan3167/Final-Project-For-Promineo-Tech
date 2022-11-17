package Invoice_Project.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import Invoice_Project.Entities.Customer;
import Invoice_Project.Entities.Inventory;
import Invoice_Project.Entities.Invoice;
import Invoice_Project.Entities.InvoiceDetail;
import Invoice_Project.errorHandler.DbException;

public class InvoiceDao extends DaoBase{

  private static final String CUSTOMER_TABLE = "customer";
  private static final String INVENTORY_TABLE = "inventory";
  private static final String INVOICE_DETAIL = "invoice_detail";
  private static final String INVOICE_TABLE = "invoice";
  
  
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;  
  
  
  
  
  public List<Invoice> fetchAllInvoices(){
    String sql = "SELECT * FROM " + INVOICE_TABLE + " ORDER BY invoice_id";

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Invoice> invoice = new LinkedList<>();

                while (rs.next()) {
                  invoice.add(extract(rs, Invoice.class));
                }
                return invoice;
            }
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }

}//fetchAllInvoicesEnd
  
  public Optional<Invoice> fetchInvoiceById(Integer invoiceId) {
    String sql = "SELECT * FROM " + INVOICE_TABLE + " WHERE invoice_id = ?";

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try {
            Invoice invoice = null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, invoiceId, Integer.class);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                      invoice = extract(rs, Invoice.class);
                    }
                  
                }
            }
           
            commitTransaction(conn);
            return Optional.ofNullable(invoice);

        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }
}//fetchProjectByIdEnd

  public boolean modifyInventory(Inventory inventory) {
 // @formatter:off
    String sql = "" 
            + "UPDATE " + INVENTORY_TABLE + " SET "
            + "item_name = ?, "
            + "stock = ?, "
            + "item_price = ? "      
            + "WHERE inventory_id = ?";
    // @formatter:on
 
    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameter(stmt, 1, inventory.getItem_name(), String.class);
            setParameter(stmt, 2, inventory.getStock(), Integer.class);
            setParameter(stmt, 3, inventory.getItem_price(), BigDecimal.class);
            setParameter(stmt, 4, inventory.getInventory_id(), Integer.class);

            boolean modified = stmt.executeUpdate() == 1;
            commitTransaction(conn);

            return modified;
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }
    } catch (SQLException e) {
        throw new DbException(e);
    }
}//modifyInventoryEnd

  public Optional<Inventory> fetchInventoryById(Integer inventory_Id) {
    String sql = "SELECT * FROM " + INVENTORY_TABLE + " WHERE inventory_id = ?";

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try {
            Inventory inventory = null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, inventory_Id, Integer.class);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                      inventory = extract(rs, Inventory.class);
                    }
                }             
            }
           
            commitTransaction(conn);
            return Optional.ofNullable(inventory);

        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }
  }

  public Invoice addInvoice(Invoice invoice) {
 // @formatter:off
    String sql = "" 
    + "INSERT INTO " + INVOICE_TABLE + " "
    + "(customer_id, total_price, open_date, close_date, comments)"
    + "VALUES "
    + "(?, ?, ?, ?, ?)";
    // @formatter:on

        try (Connection conn = DBconnection.getConnection()) {
            startTransaction(conn);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, invoice.getCustomer_id(), Integer.class);
                setParameter(stmt, 2, invoice.getTotal_price(), BigDecimal.class);
                setParameter(stmt, 3, invoice.getOpen_date(), String.class);
                setParameter(stmt, 4, invoice.getClose_date(), String.class);
                setParameter(stmt, 5, invoice.getComments(), String.class);

                stmt.executeUpdate();

                Integer invoiceId = getLastInsertId(conn, INVOICE_TABLE);
                commitTransaction(conn);

                invoice.setInvoice_id(invoiceId);
                return invoice;
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);

            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

  public Customer addCustomer(Customer customer) {
 // @formatter:off
    String sql = "" 
    + "INSERT INTO " + CUSTOMER_TABLE + " "
    + "(customer_name, address, phone_number)"
    + "VALUES "
    + "(?, ?, ? )";
    // @formatter:on

        try (Connection conn = DBconnection.getConnection()) {
            startTransaction(conn);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, customer.getCustomer_name(), String.class);
                setParameter(stmt, 2, customer.getAddress(), String.class);
                setParameter(stmt, 3, customer.getPhone_number(), String.class);
                
                stmt.executeUpdate();

                Integer customerId = getLastInsertId(conn, CUSTOMER_TABLE );
                commitTransaction(conn);

                customer.setCustomer_id(customerId);
                return customer;
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);

            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

  public Inventory addInventory(Inventory inventory) {
 // @formatter:off
    String sql = "" 
    + "INSERT INTO " + INVENTORY_TABLE + " "
    + "(item_name, stock, item_price)"
    + "VALUES "
    + "(?, ?, ? )";
    // @formatter:on

        try (Connection conn = DBconnection.getConnection()) {
            startTransaction(conn);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, inventory.getItem_name(), String.class);
                setParameter(stmt, 2, inventory.getStock(), Integer.class);
                setParameter(stmt, 3, inventory.getItem_price(), BigDecimal.class);
                
                stmt.executeUpdate();

                Integer inventoryId = getLastInsertId(conn, INVENTORY_TABLE );
                commitTransaction(conn);

                inventory.setInventory_id(inventoryId);
                return inventory;
            } catch (Exception e) {
                rollbackTransaction(conn);
                throw new DbException(e);

            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

  public Optional<Customer> fetchCustomerById(Integer customer_id) {
    String sql = "SELECT * FROM " + CUSTOMER_TABLE + " WHERE customer_id = ?";

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try {
          Customer customer = null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, customer_id, Integer.class);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        customer = extract(rs, Customer.class);
                    }
                }
            }
            
            commitTransaction(conn);
            return Optional.ofNullable(customer);

        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }
}

  public boolean modifyCustomer(Customer customer) {
 // @formatter:off
    String sql = "" 
            + "UPDATE " + CUSTOMER_TABLE + " SET "
            + "customer_name = ?, "
            + "address = ?, "
            + "phone_number = ? "
            + "WHERE customer_id = ?";
    // @formatter:on

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameter(stmt, 1, customer.getCustomer_name(), String.class);
            setParameter(stmt, 2, customer.getAddress(), String.class);
            setParameter(stmt, 3, customer.getPhone_number(), String.class);         
            setParameter(stmt, 4, customer.getCustomer_id(), Integer.class);

            boolean modified = stmt.executeUpdate() == 1;
            commitTransaction(conn);

            return modified;
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }
    } catch (SQLException e) {
        throw new DbException(e);
    }
}

  public List<Inventory> fetchAllInventory() {
    String sql = "SELECT * FROM " + INVENTORY_TABLE + " ORDER BY item_name";

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Inventory> inventory = new LinkedList<>();

                while (rs.next()) {
                  inventory.add(extract(rs, Inventory.class));
                }
                return inventory;
            }
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }

}

  public boolean modifyInvoice(Invoice invoice) {
 // @formatter:off
    String sql = "" 
            + "UPDATE " + INVOICE_TABLE + " SET "
            + "customer_id = ?, "
            + "total_price ?, "
            + "open_date = ?,"
            + "close_date = ?,"
            + "comments = ? "
            + "WHERE customer_id = ?";
    // @formatter:on

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameter(stmt, 1, invoice.getCustomer_id(), Integer.class);
            setParameter(stmt, 2, invoice.getTotal_price(), Integer.class);
            setParameter(stmt, 3, invoice.getOpen_date(), String.class);         
            setParameter(stmt, 4, invoice.getClose_date(), String.class);
            setParameter(stmt, 4, invoice.getComments(), String.class);

            boolean modified = stmt.executeUpdate() == 1;
            commitTransaction(conn);

            return modified;
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }
    } catch (SQLException e) {
        throw new DbException(e);
    }
}

  public List<Customer> fetchAllCustomers() {
    String sql = "SELECT * FROM " + CUSTOMER_TABLE + " ORDER BY customer_id";

    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Customer> customer = new LinkedList<>();

                while (rs.next()) {
                  customer.add(extract(rs, Customer.class));
                }
                return customer;
            }
        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }

}

  public boolean deleteCustomer(Integer customerId) {
    String sql = "DELETE FROM " + CUSTOMER_TABLE + " WHERE customer_id = ?";;
    
    try (Connection conn = DBconnection.getConnection()){
        startTransaction(conn);
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            setParameter(stmt, 1, customerId, Integer.class);
            
            boolean deleted = stmt.executeUpdate() == 1;
            
            commitTransaction(conn);
            return deleted;
        }
        catch(Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }
    } catch (SQLException e) {
        throw new DbException(e);
    }
}

  public boolean deleteInvoice(Integer invoiceId) {
 String sql = "DELETE FROM " + INVOICE_TABLE + " WHERE invoice_id = ?";;
    
    try (Connection conn = DBconnection.getConnection()){
        startTransaction(conn);
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            setParameter(stmt, 1, invoiceId, Integer.class);
            
            boolean deleted = stmt.executeUpdate() == 1;
            
            commitTransaction(conn);
            return deleted;
        }
        catch(Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }
    } catch (SQLException e) {
        throw new DbException(e);
    }
}

  public List<InvoiceDetail> fetchInvoiceDetails() {
//    String sql = "SELECT * FROM " + INVOICE_TABLE + " i "
//        +" INNER JOIN " + CUSTOMER_TABLE +  " c ON i.customer_id = c.customer_id "
//        +" LEFT JOIN " + INVOICE_DETAIL +  " d ON i.invoice_id = d.invoice_id "
//        +" LEFT JOIN " + INVENTORY_TABLE + " y ON y.inventory_id = d.inventory_id "
//        +" WHERE inventory_id = :inventory_id "
//        + " AND customer_id = :customer_id "
//        + " AND invoice_detail = :invoice_detail";
    String sql = ""
        + "SELECT * FROM " + INVOICE_TABLE
            + " LEFT JOIN " + CUSTOMER_TABLE + " USING (customer_id) "
                + " LEFT JOIN " + INVENTORY_TABLE + " USING (invoice_id) "
                    + " WHERE invoice_detail = invoice_detail";

    try (Connection conn = DBconnection.getConnection()) {
      startTransaction(conn);

      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
          try (ResultSet rs = stmt.executeQuery()) {
              List<InvoiceDetail> invoiceDetail = new LinkedList<>();

              while (rs.next()) {
                invoiceDetail.add(extract(rs, InvoiceDetail.class));
              }
              return invoiceDetail;
          }
      } catch (Exception e) {
          rollbackTransaction(conn);
          throw new DbException(e);
      }

  } catch (SQLException e) {
      throw new DbException(e);
  }

}

  public Optional<InvoiceDetail> fetchInvoiceDetailsById(Integer invoiceDetailsId) {
    String sql = "SELECT * FROM " + INVOICE_DETAIL + " ORDER BY invoice_id";
    
    
    try (Connection conn = DBconnection.getConnection()) {
        startTransaction(conn);

        try {
            InvoiceDetail invoiceDetails = null;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParameter(stmt, 1, invoiceDetailsId, Integer.class);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                      invoiceDetails = extract(rs, InvoiceDetail.class);
                    }
                }
            }
  
            commitTransaction(conn);
            return Optional.ofNullable(invoiceDetails);

        } catch (Exception e) {
            rollbackTransaction(conn);
            throw new DbException(e);
        }

    } catch (SQLException e) {
        throw new DbException(e);
    }
}
  
  


}//class end
