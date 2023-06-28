package br.com.dimdim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dimdim.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
