package heidar.booking.controller;

import heidar.booking.service.UserService;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserPageController {

    @Autowired
    UserService userService;

    @GetMapping("/main")
    public String showProductPage(Model model) {

        return "/user/user-main";
    }

    @GetMapping("/booking-rooms")
    public String newReservation(Model model) {
        // reservation attribute
        model.addAttribute("newRes", new CurrentReservation());

        return "/user/booking-rooms";
    }

    @PostMapping("/proceed-reservation")
    public String proceedReservation(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation,
                                     BindingResult theBindingResult, Model model) {

        // send reservation to services to save it in database
        userService.saveOrUpdateReservation(currentReservation);
        System.out.println(theBindingResult.toString());
        return "redirect:/user/your-reservations";
    }

    @GetMapping("/your-reservations")
    public String reservationsList(Model model) {

        // list of reservations for logged user
        model.addAttribute("resList", userService.getReservationsForLoggedUser());

        return "/user/user-reservation";
    }

    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {

        // delete reservation sent to services to delete from database
        userService.deleteReservation(resId);

        return "redirect:/user/your-reservations";
    }

    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {

        // new update reservation sent to services to store it in database
        model.addAttribute("newRes", userService.reservationToCurrentReservationById(resId));

        return "/user/booking-rooms";
    }

}
