package heidar.booking.controller;

import heidar.booking.service.UserService;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserPageController {

    private final UserService userService;

    @Autowired
    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "/user/user-main";
    }

    @GetMapping("/hotel-rooms")
    public String showRoomDetailPage() {
        return "/user/hotel-rooms";
    }

    @GetMapping("/booking-rooms")
    public String newReservation(Model model) {
        model.addAttribute("newRes", new CurrentReservation());
        return "/user/booking-rooms";
    }

    @PostMapping("/proceed-reservation")
    public String proceedReservation(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation) {
        userService.saveOrUpdateReservation(currentReservation);
        return "redirect:/user/your-reservations";
    }

    @GetMapping("/your-reservations")
    public String reservationsList(Model model) {
        model.addAttribute("resList", userService.getReservationsForLoggedUser());
        return "/user/user-reservation";
    }

    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {
        userService.deleteReservation(resId);
        return "redirect:/user/your-reservations";
    }

    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {
        model.addAttribute("newRes", userService.currentReservationById(resId));
        return "/user/booking-rooms";
    }

}
