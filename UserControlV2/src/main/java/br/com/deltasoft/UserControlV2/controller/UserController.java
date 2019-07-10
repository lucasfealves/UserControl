package br.com.deltasoft.UserControlV2.controller;

import java.util.List;

import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.deltasoft.UserControlV2.model.Login;
import br.com.deltasoft.UserControlV2.model.User;
import br.com.deltasoft.UserControlV2.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@RestController
public class UserController {
	
    @Autowired
    private UserRepository repository;
    
    //private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11);
    
    private String secretKey = "35725c901c45f1c13f9e3fe8421a15dd26130118"; // Chave privada de exemplo
    
	@PostMapping("/GetHash")
	public User GetHash(@RequestBody Login T) {
		
		User vuser = repository.findByUsername(T.getUsuario());
		if (vuser != null) {
			if (verifyHashKey(T.getSenha(), vuser.getPassword())) {
				String token = Jwts.builder()
		                .setSubject(vuser.getUsername())
		                .claim("auth", vuser.getRole())
		                .signWith(SignatureAlgorithm.HS512, secretKey)
		                .setExpiration(null)
		                .compact();
		
				vuser.setToken(token);
				return vuser;
			}
		}
		return null;
	}
	
	@GetMapping("/CheckHash/{Hash}")
	public String CheckHash(@PathVariable String Hash) {
	    try {
	        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(Hash);
	        return "Valido";
	    } catch (SignatureException e) {
	        return "Invalido";
	    }
	}
	
	
	@GetMapping("/GetAllUsers")
	private List<User> getAllUsers() {
		return repository.findAll();
	}
	
	@GetMapping("/GetUserId/{IdUser}")
	private User getUser(@PathVariable Integer IdUser) {
		User vuser = repository.findById(IdUser).get();
		if (vuser != null) {
			return vuser;
		}
		return null;
	}
	
	@PostMapping("/SaveUser")
	private User SaveUser(@RequestBody @Valid User T) {
		String senha = T.getPassword();
		
		T.setPassword(BCrypt.hashpw(senha, BCrypt.gensalt()));
		return repository.save(T);
	}
	
	@DeleteMapping("/DeleteUser")
	private User DeleteUser(@RequestBody @Valid User T) {
		return repository.save(T);
	}
	
    public boolean verifyHashKey(String password, String hash) {
        if (BCrypt.checkpw(password, hash)) {
            return true;
        }
        return false;
    }
}
