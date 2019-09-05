package com.springboot.app.models.service;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;
import com.springboot.app.models.entity.Usuario;

/**
 * 
 * @author Daniel Correa
 *
 */
public interface IUsuarioService {

	/**
	 * 
	 * @return
	 */
	public List<Usuario> findAll();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Usuario> obtenerUsuario(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Usuario> findById(Long id);

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public Boolean guardarUsuario(Usuario usuario);
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public Usuario actualizarUsuario(Usuario usuario);
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public Boolean borrarUsuario(Usuario usuario);
	
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public Boolean validacionesUsuario(Usuario usuario);
}
