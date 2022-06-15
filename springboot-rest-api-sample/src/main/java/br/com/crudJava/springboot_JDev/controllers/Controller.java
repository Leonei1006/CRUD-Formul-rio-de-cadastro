package br.com.crudJava.springboot_JDev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.crudJava.springboot_JDev.models.Usuario;
import br.com.crudJava.springboot_JDev.repository.UsuarioRepository;

import java.util.List;
import java.util.Locale;

@RestController
public class Controller {
	
	@Autowired /*IC/CD ou CDI - Injeção de Depêndencia*/
	private UsuarioRepository usuarioRepository;
	
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso JDev - Treinamento " + name + " o mais lindão!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario); //Salva no banco de dados
    	
    	return "Olá Mundo " + nome + " !";
    }

    @GetMapping(value = "listatodos")
    @ResponseBody //Retorna os dados  para o corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuarios(){

        List<Usuario> usuarios = usuarioRepository.findAll(); // Executa a consulta no banco de dados

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // Retorna a lista e Json

    }

    @PostMapping(value = "salvar") // mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){ //Rescebe os dados para salvar

        Usuario user = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED );
    }

    @PutMapping(value = "atualizar") // mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){ //Rescebe os dados para salvar

        if (usuario.getId() == null){
            return new ResponseEntity<String>("Id não informado para atualização", HttpStatus.OK);
        }

        Usuario user = usuarioRepository.saveAndFlush(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete") // mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<String> delete (@RequestParam Long iduser){ //Rescebe os dados para deletar

        usuarioRepository.deleteById(iduser);

        return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.OK);
    }

    @GetMapping(value = "buscaruserid") // mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "iduser") Long iduser){ //Busca o usuario por id

        Usuario usuario = usuarioRepository.findById(iduser).get();

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @GetMapping(value = "buscarPorNome") // mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name = "name") String name){ //Busca o usuario por id

        List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

        return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
}
