package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;

//PARA HABILITAR LAS ANOTACIONES DE SEGURIDAD EN LOS CONTROLLERS
//CON prePostEnabled=true PODEMOS SUSTITUIR @Secured POR @PreAuthorize("hasRole('ROLE_USER')")
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//LAS RUTAS ACCESIBLES A TODO EL MUNDO, INCLUSO ANONIMOS
		//SPRING SECURITY TIENE UN INTERCEPTOR PARA VER SI HAY LOGIN
		//successHandler VIENE DEL LoginSuccessHandler
		//COMENTAMOS LAS RUTAS PARA USAR ANOTACIONES EN EL CONTROLLER CON @Secured
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**").permitAll()
		/*.antMatchers("/ver/**").hasAnyRole("USER")*/
		/*.antMatchers("/uploads/**").hasAnyRole("USER")*/
		/*.antMatchers("/form/**").hasAnyRole("ADMIN")*/
		/*.antMatchers("/eliminar/**").hasAnyRole("ADMIN")*/
		/*.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
		    .formLogin()
		        .successHandler(successHandler)
		        .loginPage("/login")
		    .permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		/*
		 * Deprecated
		 * UserBuilder users = User.withDefaultPasswordEncoder();
		 * */
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//CREAMOS USUARIOS Y ENCRIPTAMOS LA CONTRASEÑA
		//encoder::encode ES UNA LLAMADA ESTÁTICA QUE SUSTITUYE A LA FUNCION LAMBDA
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		//ESTA ES LA FUNCION FLECHA LAMBDA SUSTITUIDA
//		UserBuilder users = User.builder().passwordEncoder(password -> { 
//			return encoder.encode(password);
//		});
		
		//password() ENCRIPTA EL PASS
		build.inMemoryAuthentication()
		.withUser(users.username("Abigail").password("1234").roles("ADMIN", "USER"))
		.withUser(users.username("Sofia").password("1234").roles("ADMIN", "USER"))
		.withUser(users.username("Ancor").password("1234").roles("ADMIN", "USER"))
		.withUser(users.username("Adan").password("1234").roles("USER", "USER"));
	}
}
