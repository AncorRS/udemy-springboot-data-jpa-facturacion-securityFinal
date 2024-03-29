package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component //PARA QUE QUE SE GUARDE EN EL CONTENEDOR Y SE PUEDA INYECTAR
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		//PARA PODER REGISTRA UN FLASH
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		//PARA LOS MENSAJES
		FlashMap flashMap = new FlashMap();
		
		//AGREGAMOS MENSAJE
		flashMap.put("success", "Hola " +authentication.getName()+ ", sesión iniciada");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			logger.info("El usuario '"+authentication.getName()+"' ha iniciado sesión con éxito");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
