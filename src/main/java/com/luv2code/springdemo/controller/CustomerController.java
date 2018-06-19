package com.luv2code.springdemo.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//need to inject our customer service
	@Autowired
	private CustomerService customerService;
	
	// inject page size from props	
	@Value("${pageSize:10}")
	private int pageSize;

	@GetMapping("/list")
	public String listCustomers(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber, 
								Model theModel){
		
		long totalCustomerCount = customerService.getCustomersCount();
		
		int totalPages = (int) Math.floor(totalCustomerCount / pageSize);
		
		if ( (totalCustomerCount % pageSize) > 0) {
			totalPages++;
		}
				
		System.out.println("pageNumber=" + pageNumber);
		
		List<Customer> theCustomers = customerService.getCustomersByPage(pageNumber);
				
		// add the customers to the model	
		theModel.addAttribute("customers", theCustomers);
		
		// add data for pagination support
		theModel.addAttribute("pagination", true);
		theModel.addAttribute("totalCustomerCount", totalCustomerCount);
		theModel.addAttribute("currentPage", pageNumber);
		theModel.addAttribute("totalPages", totalPages);
		theModel.addAttribute("pageSize", pageSize);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		//save the cutomer using our service
		customerService.saveCustomer(theCustomer);		
		
		return "redirect:/customer/list";
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormUpdate(@RequestParam("customerId") int theId,
								 Model theModel) {
		
		//get the customer from the service
		Customer theCustomer = customerService.getCustomer(theId);
		
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		//send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId, Model theModel) {
	
		//delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@PostMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
                                  Model theModel) {		

        // search customers from the service
        
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);		
		
		if(theCustomers == null) return "redirect:/customer/list";
			
        // add the customers to the model  
		theModel.addAttribute("pagination", false);
        theModel.addAttribute("customers", theCustomers);
        
        return "list-customers";         
              
    }

}