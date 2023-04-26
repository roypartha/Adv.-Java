package ecom.controller;

import ecom.domain.Customer;
import ecom.domain.Product;
import ecom.domain.User;
import ecom.dto.OrderDto;
import ecom.service.CustomerService;
import ecom.service.ProductService;
import ecom.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.sql.SQLException;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;
    private ProductService productService;

    public CustomerController(CustomerService customerService,ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("customers", customerService.list());
        return "customer/list";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customers", customerService.list());

        return "customer/create";
    }
    @RequestMapping("/order_create")
    public String createOrder(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("customers", customerService.list());
        return "customerOrder/create";
    }




    @RequestMapping("/store")
    public String store(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "customer/create";
        }
        customerService.create(customer);
        return "redirect:/customers/list";
    }
    @RequestMapping("/storeOrder")
    public String storeOrder(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            System.out.println(product);
            return "/product/create";
        }
        productService.create(product);
        return "redirect:/products/list";
    }

    @RequestMapping("/edit")
    public String edit(@RequestParam("customerId") Long customerId, Model model) throws SQLException {
        model.addAttribute("customer", customerService.get(customerId));
        return "customer/edit";
    }

    @RequestMapping("/update")
    public String update(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "customer/edit";
        }
        customerService.update(customer);
        return "redirect:/customers/list";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("customerId") Long customerId) {
        customerService.delete(customerId);
        return "redirect:/customers/list";
    }
}
