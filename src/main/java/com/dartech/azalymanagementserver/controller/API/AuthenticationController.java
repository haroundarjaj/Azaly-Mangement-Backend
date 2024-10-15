package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.constants.ERole;
import com.dartech.azalymanagementserver.controller.Request.UserAddRequest;
import com.dartech.azalymanagementserver.dto.UserDto;
import com.dartech.azalymanagementserver.entity.Role;
import com.dartech.azalymanagementserver.entity.User;
import com.dartech.azalymanagementserver.payload.request.LoginRequest;
import com.dartech.azalymanagementserver.payload.request.SignupRequest;
import com.dartech.azalymanagementserver.payload.response.JwtResponse;
import com.dartech.azalymanagementserver.payload.response.MessageResponse;
import com.dartech.azalymanagementserver.repository.RoleRepository;
import com.dartech.azalymanagementserver.repository.UserRepository;
import com.dartech.azalymanagementserver.security.jwt.JwtUtils;
import com.dartech.azalymanagementserver.security.services.UserDetailsImpl;
import com.dartech.azalymanagementserver.service.UserService;
import com.dartech.azalymanagementserver.utils.StringDecryption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
    PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	StringDecryption stringDecryption;

	@Autowired
	ModelMapper modelMapper;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException  {

		System.out.println("**********************************************************");
		System.out.println(encoder.encode("03101997"));

		String decryptedPassword = stringDecryption.decryptString(loginRequest.getPassword()) ;
		System.out.println("decryptedPassword");
		System.out.println(decryptedPassword);
		System.out.println(encoder.encode(decryptedPassword));
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), decryptedPassword));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		UserDto userdto = userService.findByEmail(userDetails.getEmail());
		System.out.println("userDetails.getAuthorities()");
		System.out.println(userDetails.getAuthorities());
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		userdto.setRoles(roles);

		return ResponseEntity.ok(new JwtResponse(jwt, userdto));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserAddRequest userAddRequest) {
		Optional<User> userOpt = userRepository.findByUsername(userAddRequest.getUsername());
		if (userOpt.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		userOpt = userRepository.findByEmail(userAddRequest.getEmail());
		if (userOpt.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = modelMapper.map(userAddRequest, User.class);
		String decryptedPassword = stringDecryption.decryptString(userAddRequest.getPassword());

		user.setPassword(encoder.encode(decryptedPassword));
		user.setActive(false);
		user.setApproved(false);
		List<Role> roles = new ArrayList<>();

		user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		user = userRepository.save(user);
		UserDto userDto = modelMapper.map(user, UserDto.class);

		if (userAddRequest.getRoles() == null) {
			Role userRole = roleRepository.findByCode("US")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			for (String code : userAddRequest.getRoles()) {
				Role role = roleRepository.findByCode(code).get();
				roles.add(role);
			}
		}

		user.setRoles(roles);
		System.out.println(roles);
		System.out.println(user);
		user = userRepository.save(user);


		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
