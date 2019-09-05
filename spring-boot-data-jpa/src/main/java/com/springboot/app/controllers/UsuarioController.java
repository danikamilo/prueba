package com.springboot.app.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.IUsuarioService;
import com.springboot.app.util.Constantes;


/**
 * 
 * @author Daniel Correa
 *
 */
@Controller
@RequestMapping("/wsusuario")
@SessionAttributes("wsusuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuServ;

	private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	
	/**
	 * 
	 * @param model
	 * @param flash
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/verUsuarios", produces = { "application/json" })
	public @ResponseBody List<Usuario> verUsuarios(Model model, RedirectAttributes flash,
			HttpServletResponse response) {
		List<Usuario> listUsu = null;
		try {
			listUsu = usuServ.findAll();
			return (List<Usuario>) ResponseEntity.ok();
		} catch (Exception e) {
			log.error("", e);
		}
		return (List<Usuario>) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
	}

	
	/**
	 * 
	 * @param id
	 * @param model
	 * @param flash
	 * @return
	 */
	@GetMapping(value = "/verUsuario/{id}", produces = { "application/json" })
	public @ResponseBody Usuario verUsuario(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Usuario usuObj = null;
		try {
			if(id == null) {
				return (Usuario) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
			}
			usuObj = usuServ.obtenerUsuario(id).get();
			return (Usuario) ResponseEntity.ok();
		} catch (Exception e) {
			log.error("", e);
		}
		return (Usuario) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
	}

	/**
	 * 
	 * @param nombre
	 * @param edad
	 * @param telefono
	 * @return
	 */
	@PostMapping(path = "/guardaUsuario", produces = "text/plain")
	public @ResponseBody Usuario nuevoUsuario(@RequestParam String nombre, String edad, String telefono) {
		Response resp = null;
		Usuario usu = null;
		try {
			if(!(usuServ.validacionesUsuario(new Usuario(nombre, edad, telefono)))) {
				return (Usuario) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
			}
			usu = new Usuario(nombre, edad, telefono);
			if(usuServ.guardarUsuario(usu)) {
				return usu;
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return usu;
	}

	/**
	 * 
	 * @param nombre
	 * @param edad
	 * @param telefono
	 * @param idUsuario
	 * @return
	 */
	@RequestMapping (value = "/actualizarUsuario/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Usuario> actualizarUsuario(String nombre, String edad, String telefono, @PathVariable(value = "id") Long idUsuario) {
		Usuario objUsu = new Usuario(nombre, edad, telefono);
		Usuario usuActualizado = null;
		try {
			if(!(usuServ.validacionesUsuario(objUsu))) {
				return (ResponseEntity<Usuario>) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
			}
			objUsu = usuServ.findById(idUsuario).get();
			usuActualizado = usuServ.actualizarUsuario(objUsu);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return (ResponseEntity<Usuario>) ResponseEntity.ok();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/borrarUsuario/{id}")
	public ResponseEntity<Usuario> borrarUsuario(@PathVariable Long id) {
		Usuario objUsu = null;
		try {
			if(id == null) {
				return (ResponseEntity<Usuario>) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
			}
			objUsu = usuServ.findById(id).get();
			usuServ.borrarUsuario(objUsu);
			return (ResponseEntity<Usuario>) ResponseEntity.ok();
		} catch (Exception e) {
			log.error("Error", e);
		}
		return (ResponseEntity<Usuario>) ResponseEntity.status(Constantes.CODS_RESP_BAD_REQUEST);
	}

}
