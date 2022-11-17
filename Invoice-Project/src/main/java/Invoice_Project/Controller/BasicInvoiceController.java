package Invoice_Project.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import Invoice_Project.Entities.Invoice;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BasicInvoiceController implements InvoiceController {

 

  @Override
  public List<Invoice> fetchInvoices() {
    log.info("Fetching a list of Invoices" );
    return new ArrayList<>();
  }

}
