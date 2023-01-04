package com.example.firststringproject.Security.Config;


import com.example.firststringproject.jwt.JwtAuthFilterByEmailAndPass;
import com.example.firststringproject.jwt.JwtConfig;
import com.example.firststringproject.jwt.JwtVerifier;
import com.example.firststringproject.student.AppStudentRole;
import com.example.firststringproject.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final StudentService studentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthFilterByEmailAndPass(authenticationManager(),jwtConfig, secretKey))
                .addFilterAfter(new JwtVerifier(secretKey, jwtConfig),JwtAuthFilterByEmailAndPass.class)
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**","/","index").permitAll()
                .antMatchers(HttpMethod.POST).hasAuthority(AppStudentRole.Representative.name())
                .antMatchers(HttpMethod.DELETE).hasAuthority(AppStudentRole.Representative.name())
                .antMatchers(HttpMethod.PUT).hasAuthority(AppStudentRole.Representative.name())
                .anyRequest().authenticated();
        //               .and()
//                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/students",true)
//                .passwordParameter("password")
//                .usernameParameter("username")
//                .and().rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)).key("somethingverysecured").rememberMeParameter("remember-me")
//                .and ().logout().logoutUrl("/logout").clearAuthentication (true).invalidateHttpSession(true).deleteCookies ("JSESSIONID", "remember-me")
//                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(studentService);
        return provider;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
}
