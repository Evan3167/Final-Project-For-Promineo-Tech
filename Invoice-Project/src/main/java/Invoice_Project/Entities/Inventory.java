package Invoice_Project.Entities;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
public class Inventory {
  
  private Integer inventory_id;
  private String item_name;
  private Integer stock;
  private BigDecimal item_price;

}

