package Invoice_Project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Invoice_Project.Entities.Invoice;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class DefaultInvoiceService implements InvoiceService {

  
  @Transactional(readOnly = true)
  @Override
  public List<Invoice> fetchInvoices(Invoice invoice) {
    log.info("Fetching list of invoices" );
    return new ArrayList<>();
  }

}
