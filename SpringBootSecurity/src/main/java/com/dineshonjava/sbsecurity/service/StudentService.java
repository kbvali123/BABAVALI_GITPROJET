/**
 * 
 */
package com.dineshonjava.sbsecurity.service;

import java.util.Collection;

import com.dineshonjava.sbsecurity.bean.UserBean;
import com.dineshonjava.sbsecurity.model.Student;

/**
 * @author Dinesh.Rajput
 *
 */
public interface StudentService {
	
	Student getStudentById(long id);

    Student getStudentByEmail(String email);

    Collection<Student> getAllStudents();

}
