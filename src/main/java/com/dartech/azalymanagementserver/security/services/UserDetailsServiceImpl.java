package com.dartech.azalymanagementserver.security.services;

import com.dartech.azalymanagementserver.entity.User;
import com.dartech.azalymanagementserver.exceptioHandler.ApplicationException;
import com.dartech.azalymanagementserver.exceptioHandler.CustomError;
import com.dartech.azalymanagementserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		if(!user.isApproved()) {
			throw new ApplicationException( new CustomError(400, "Account is not approved by the admin yet", "account_not_approved"));
		}

		if(!user.isActive()) {
			throw new ApplicationException( new CustomError(400, "Account is not active", "account_not_active"));
		}

		return UserDetailsImpl.build(user);
	}

}
