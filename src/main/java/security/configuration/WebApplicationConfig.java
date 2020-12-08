package security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import security.entity.Authority;

@Configuration
@EnableWebSecurity
public class WebApplicationConfig extends WebSecurityConfigurerAdapter  {
	
	//This bean takes care of user authentication .
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
			
		//this bean takes care of password encryption. is required to login into spring security application
		@Bean
		public  PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		//this bean takes care of security policy
		protected void configure(HttpSecurity http) throws Exception {
			//Let's start with the most stringent limitations to get to the less stringent ones
			// if you don't disable csrf you won't be able to execute write methods
			 http.csrf().disable()
			 .authorizeRequests()
			 .antMatchers("/user/chiuso", "/user/hello")
			 .hasAnyRole("ROLE_ADMIN")
			 .and()
			 .authorizeRequests()
			 .antMatchers("/")
			 .permitAll();
		}
		
		//Exemple with Inmemory user 
		@Bean
		public UserDetailsService userDetailsService() {
		    UserDetails user = User.builder()
		        .username("user")
		        .password(passwordEncoder().encode("Ciao"))
		        .roles("USER")
		        .authorities(Authority.getGrantedAuthority(Authority.ADMIN))
		        .build();
		    UserDetails admin = User.builder()
		        .username("admin")
		        .password(passwordEncoder().encode("Ziao"))
		        .roles("USER", "ADMIN")
		        .authorities(Authority.getGrantedAuthority(Authority.USER))
		        .build();
		    
		    return new InMemoryUserDetailsManager(user, admin);
		}
	

}
