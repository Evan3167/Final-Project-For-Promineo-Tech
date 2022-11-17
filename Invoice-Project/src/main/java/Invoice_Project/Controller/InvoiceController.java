package Invoice_Project.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import Invoice_Project.Entities.Invoice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/invoices")
@OpenAPIDefinition(info = @Info(title = "Invoice"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface InvoiceController {

  // @formatter:off
  @Operation(
      summary = "Returns a list of invoices",
      description = "Returns a list of invoices given an id ",
      responses = { 
          @ApiResponse(
              responseCode = "200", 
              description = "A list of invoices are returned", 
              content = @Content(
              mediaType = "application/json", 
              schema = @Schema(implementation = Invoice.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid", 
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No invoices were found with the input criteria", 
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occured" , 
              content = @Content(mediaType = "application/json"))
      }
      ,
    parameters = {
//          @Parameter(
//              name = "invoice_id", 
//              allowEmptyValue = false, 
//              required = false, 
//              description = "The invoice id (i.e. '1,2,12')" ),
          
   }
  )

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Invoice> fetchInvoices();
//@formatter:on
  
  
}//classEnd
