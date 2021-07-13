/**
 * 
 */
package com.dineshonjava.sbsecurity.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Dinesh.Rajput
 *
 */
public interface StudentRepository extends CrudRepository<Student, Long>{
	
	Student findOneByEmail(String email);
}
