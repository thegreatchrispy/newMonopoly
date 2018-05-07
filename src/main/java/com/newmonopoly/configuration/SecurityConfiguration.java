package com.newmonopoly.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.accounts-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				/* Permits. */
				// Webpages.
				.antMatchers("/").permitAll()
				.antMatchers("/checkEmail").permitAll()
				.antMatchers("/checkUsername").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/learn").permitAll()
				.antMatchers("/gameplay").permitAll()
				.antMatchers("/lobby").permitAll()
				
				/* Game Controller. */
				/* Creating, join, retrieve, update, and delete game. */
				.antMatchers("/creategame").permitAll()
				.antMatchers("/joingame").permitAll()
				.antMatchers("/retrievegame").permitAll()
				.antMatchers("/updategame").permitAll()
				.antMatchers("/deletegame").permitAll()
				.antMatchers("/gameover").permitAll()

				/* Accessor Methods. */
				.antMatchers("/getnumberofplayers").permitAll()
				.antMatchers("/getnamesofplayers").permitAll()
				.antMatchers("/getrandomized").permitAll()
				.antMatchers("/getswappedstring").permitAll()
				.antMatchers("/getdoublescount").permitAll()
				.antMatchers("/getmoney").permitAll()
				.antMatchers("/getposition").permitAll()
				.antMatchers("/getinjail").permitAll()
				.antMatchers("/getbuildstatus").permitAll()
				.antMatchers("/getmonopolygroups").permitAll()
				.antMatchers("/getbuildinginfo").permitAll()
				.antMatchers("/gettradestatus").permitAll()
				.antMatchers("/getmortgagestatus").permitAll()
				.antMatchers("/getmortgageinfo").permitAll()
				.antMatchers("/getownedproperties").permitAll()
				.antMatchers("/getbankruptstatus").permitAll()
				.antMatchers("/hasanyhouses").permitAll()
				.antMatchers("/gethousegroups").permitAll()
				.antMatchers("/getspacename").permitAll()

				/* Mutator Methods. */
				.antMatchers("/moveplayertojail").permitAll()
				.antMatchers("/incrementdoubles").permitAll()
				.antMatchers("/decrementjailtime").permitAll()
				.antMatchers("/setdoubles").permitAll()
				.antMatchers("/getoutofjailfree").permitAll()
				.antMatchers("/getoutofjail").permitAll()

				/* Logical Methods. */
				.antMatchers("/performspaceaction").permitAll()
				.antMatchers("/moveplayer").permitAll()
				.antMatchers("/acceptpurchase").permitAll()
				.antMatchers("/auctionpurchase").permitAll()
				.antMatchers("/addmonopolyafterpurchase").permitAll()
				.antMatchers("/addmonopolyafterauction").permitAll()
				.antMatchers("/purchasebuildings").permitAll()
				.antMatchers("/addmortgage").permitAll()
				.antMatchers("/paymortgage").permitAll()
				.antMatchers("/givetoallplayers").permitAll()
				.antMatchers("/paytoplayers").permitAll()
				.antMatchers("/auctionbankruptpurchase").permitAll()
				.antMatchers("/removeplayer").permitAll()

				// Authorities.
				.antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
				// Login and Logout
				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.loginPage("/login").defaultSuccessUrl("/home")
				.usernameParameter("email")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/jquery/**");
	}
}