package com.ap.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {


    @Override

    protected void configure(HttpSecurity http) throws Exception {

       http.authorizeRequests()
                       .antMatchers("/web/index.html", "/web/css/style.css", "/web/img/Mindhub-logo.png", "/web/js/index.js", "/web/img/avila.jpg", "/web/img/favicon.ico", "/web/img/avila.jpg", "/api/login", "/api/logout","/web/img/mindhub.jpg").permitAll()
                       .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                       .antMatchers("/web/css/cards.css","/web/accounts.html", "/web/js/accounts.js","web/js/account.js", "/api/accounts", "/web/cards.html", "/web/js/cards.js", "/api/clients/current").hasAuthority("CLIENT")
                       .antMatchers("/manager.html", "/manager.js", "/api/clients", "/api/accounts", "/api/clients/{id}").hasAuthority("ADMIN")
                       .anyRequest().denyAll()
                       ;

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");


        // turn off checking for CSRF tokens

        http.csrf().disable();


        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes (HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }


}