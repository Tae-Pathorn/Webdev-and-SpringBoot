package ku.cs.kafe.controller;


import ku.cs.kafe.model.AddCartRequest;
import ku.cs.kafe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;


@Controller
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cart", orderService.getCurrentOrder());
        return "cart";
    }
    @GetMapping("/{orderId}")
    public String getAllOrders(@PathVariable UUID orderId, Model model) {
        model.addAttribute("order", orderService.getById(orderId));
        return "order-view";
    }


    @PostMapping("/{orderId}/finish")
    public String finishOrder(@PathVariable UUID orderId, Model model) {
        orderService.finishOrder(orderId);
        return "redirect:/admin/orders";
    }



    @PostMapping
    public String submitOrder(Model model) {
        orderService.submitOrder();
        model.addAttribute("confirmOrder", true);
        return "home";
    }


    @PostMapping("/{menuId}")
    public String order(@PathVariable UUID menuId,
                        @ModelAttribute AddCartRequest request,
                        Model model) {
        orderService.order(menuId, request);
        return "redirect:/menus";
    }
}
