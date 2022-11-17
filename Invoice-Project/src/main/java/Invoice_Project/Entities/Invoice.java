package Invoice_Project.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class Invoice {
  
  private Integer invoice_id;
  private Integer customer_id;
  private BigDecimal total_price;
  private String open_date;
  private String close_date;
  private String comments;
  
}
