/**
 * 
 */
package com.dineshonjava.sbsecurity.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dineshonjava.sbsecurity.bean.UserBean;
import com.dineshonjava.sbsecurity.model.Student;
import com.dineshonjava.sbsecurity.model.StudentRepository;

/**
 * @author Dinesh.Rajput
 *
 */
@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	StudentRepository userRepository;
	
	@Override
	public Student getUserById(long id) {
		LOGGER.debug("Getting user={}", id);
		return userRepository.findById(id).get();
	}

	@Override
	public Student getUserByEmail(String email) {
		LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
		return userRepository.findOneByEmail(email);
	}

	@Override
	public Collection<Student> getAllUsers() {
		LOGGER.debug("Getting all users");
		return (Collection<Student>) userRepository.findAll();
	}

	@Override
	public Student create(UserBean userBean) {
		Student user = new Student();
		user.setUsername(userBean.getUsername());
        user.setEmail(userBean.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userBean.getPassword()));
        user.setRole(userBean.getRole());
        return userRepository.save(user);
	}

}
