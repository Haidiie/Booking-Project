package heidar.booking.controller;


import heidar.booking.model.Reservation;
import heidar.booking.repo.ReservationRepo;
import heidar.booking.service.UserService;
import heidar.booking.temp.CurrentReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    ReservationRepo reservationRepo;

    @Autowired
    UserService userService;

    public List<Reservation> allReservations;


    @GetMapping("/main")
    public String showMainPage(Model model) {

        return "/admin/admin-main";
    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty()) {
            return false;
        }
        return true;
    }

    @GetMapping("/reservations")
    public String showAllRoomPage(Model model, String name) {
        if(isNullOrEmpty(name)){
            model.addAttribute("resList", reservationRepo.findAll());
        }else {
            List<Reservation> reservations = reservationRepo.findbyhavingemail(name);
            allReservations = reservations;
            model.addAttribute("resList", allReservations);
        }

        return "/admin/admin-rooms";
    }

    /*@GetMapping("/reservations")
    public String showAllRoomPage(Model model) {

        // list of reservations for logged user
        model.addAttribute("resList", reservationRepo.findAll());

        return "/admin/admin-rooms";
    }*/

    @GetMapping("/booking-rooms")
    public String newReservations(Model model) {
        // reservation attribute
        model.addAttribute("newReserv", new CurrentReservation());

        return "/admin/booking-rooms";
    }

    @PostMapping("/proceed-reservation")
    public String proceedReservations(@Valid @ModelAttribute("newReserv") CurrentReservation currentReservation,
                                     BindingResult theBindingResult, Model model) {

        // send reservation to services to save it in database
        userService.saveOrUpdateReservation(currentReservation);
        System.out.println(theBindingResult.toString());
        return "redirect:/admin/reservations";
    }

    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {

        // delete reservation sent to services to delete from database
        userService.deleteReservation(resId);

        return "redirect:/admin/reservations";
    }

    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {

        // new update reservation sent to services to store it in database
        model.addAttribute("newReserv", userService.reservationToCurrentReservationById(resId));

        return "/admin/booking-rooms";
    }



}
