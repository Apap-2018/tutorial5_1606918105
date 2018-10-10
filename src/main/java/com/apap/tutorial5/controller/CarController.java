package com.apap.tutorial5.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired 
	private DealerService dealerService;
	
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable (value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		ArrayList<CarModel> listCar = new ArrayList<CarModel>();
		listCar.add(new CarModel());
		dealer.setListCar(listCar);
		
		model.addAttribute("title","Form Tambah Mobil");
		model.addAttribute("dealer",dealer);
		return "addCar";
		
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"save"})
	private String addCarSubmit(@PathVariable (value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer, Model model) {
		DealerModel dealerbyId = dealerService.getDealerDetailById(dealer.getId()).get();
		for (CarModel car: dealer.getListCar()) {
			car.setDealer(dealerbyId);
			carService.addCar(car);
		}
		
		/*model.addAttribute("title", "Notifikasi Berhasil");
		return "add";*/
		
		return "redirect:/dealer/view?dealerId=" + dealerId ; // GIMANA CARANYA REDIRECT
		
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String deleteCar(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deletCar(car.getId());
		}
		model.addAttribute("title","Notifikasi Berhasil ");
		return "delete-Car";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
		if (dealer.getListCar() == null) {
            dealer.setListCar(new ArrayList<CarModel>());
        }
		dealer.getListCar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    dealer.getListCar().remove(rowId.intValue());
	    
	    model.addAttribute("dealer", dealer);
	    return "addCar";
	}
	
	
}
