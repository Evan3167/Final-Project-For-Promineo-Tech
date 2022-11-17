package Invoice_Project.Entities;

import lombok.Builder;
import lombok.Data;

@Data
public class Customer {
  
  private Integer customer_id;
  private String customer_name;
  private String address;
  private String phone_number;
}
