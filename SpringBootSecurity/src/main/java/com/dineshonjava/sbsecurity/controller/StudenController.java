/**
 * 
 */
package com.dineshonjava.sbsecurity.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dineshonjava.sbsecurity.bean.UserBean;
import com.dineshonjava.sbsecurity.bean.validator.UserBeanValidator;
import com.dineshonjava.sbsecurity.model.Student;
import com.dineshonjava.sbsecurity.service.StudentService;

/**
 * @author Dinesh.Rajput
 *
 */
@Controller
public class StudenController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudenController.class);
	
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/students")
	public ModelAndView getStudentPage() {
		LOGGER.debug("Getting student page");
		return new ModelAndView("students", "students", studentService.getAllStudents());
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping("/student/{id}")
    public ModelAndView getStudentPage(@PathVariable Long id) {
		LOGGER.debug("Getting student page for user={}", id);
        return new ModelAndView("students", "student", studentService.getStudentById(id));
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/student/create", method = RequestMethod.GET)
    public ModelAndView getStudentCreatePage() {
    	 LOGGER.debug("Getting user create form");
        return new ModelAndView("user_create", "form", new Student());
    }
    
	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/student/create", method = RequestMethod.POST)
    public String handleStudentCreateForm(@Valid @ModelAttribute("form") Student form, BindingResult bindingResult) {
    	 LOGGER.debug("Processing user create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user_create";
        }
        try {
        	studentService.create(form);
        } catch (DataIntegrityViolationException e) {
        	LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "user_create";
        }
        return "redirect:/users";
    }
}
