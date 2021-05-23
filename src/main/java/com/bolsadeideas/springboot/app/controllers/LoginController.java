package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	//CONTROLAMOS ERROR CON EL PARAMETRO error RECIBIDO
	//CONTROLAMOS LOGOUT CON EL PARAMETRO logout RECIBIDO
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		//SI principal != null ES PORQUE YA HABIA INICIADO SESION ANTERIORMENTE
		//PARA EVITAR QUE HAGA DOBLE INICIO DE SESION
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			return "redirect:/";
		}
		
		//SI EXISTE ERROR EN EL LOGIN
		if(error != null) {
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		
		//PARA CERRAR LA SESION
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		
		return "login";
	}
}
