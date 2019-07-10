package br.com.deltasoft.UserControlV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.deltasoft.UserControlV2.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String Username);
}
