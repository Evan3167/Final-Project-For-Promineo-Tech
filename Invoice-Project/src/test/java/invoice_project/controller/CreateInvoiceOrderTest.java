package invoice_project.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import Invoice_Project.Entities.Invoice;
import Invoice_Project.Entities.InvoiceDetail;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@ActiveProfiles("test")

@Sql(scripts = {
    "classpath:flyway/Migrations/invoice.sql"}, 
    config = @SqlConfig(encoding = "utf-8"))

class CreateInvoiceOrderTest {

  @Test
  void testThatInvoicesAreReturnedWhenAValidIdIsSupplied() {
   //Given: a valid id and URI
    //Invoice_detail invoiceId = Invoice_detail;
    
    
   //When: a connection is made to the URI
    
    
   //Then: a success (OK - 200) status code is returned.
    
   //And(maybe): the an invoice is returned.
  }

}
