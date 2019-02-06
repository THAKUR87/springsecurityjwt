package com.demo.spring.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.spring.springsecurity.UserRepository;
import com.demo.spring.springsecurity.model.User;

@Service
public class JwtUserDetailsServiceImpl  implements UserDetailsService{
	
	@Autowired UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailIgnoreCase(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No User found with useranme '%s'.", username));
		} else {
			return JwtUserFactory.create(user);
		}
		
	}

}
