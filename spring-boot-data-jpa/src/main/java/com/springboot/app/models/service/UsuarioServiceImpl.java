package com.springboot.app.models.service;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.springboot.app.models.dao.IUsuarioDao;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.util.Constantes;
import com.springboot.app.util.GeneradorRespuestas;


/**
 * 
 * @author Daniel Correa
 *
 */
@Repository
public class UsuarioServiceImpl implements IUsuarioService {

	private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private IUsuarioDao usuDao;


	@Override
	public List<Usuario> findAll() {
		List<Usuario> lstUsu = null;
		GeneradorRespuestas respon = new GeneradorRespuestas();
		Gson gson = new Gson();
		Response resp = null;
		try {
			lstUsu = (List<Usuario>) usuDao.findAll();
			/*
			 * String json = gson.toJson(lstUsu);
			 * 
			 * respon.validarSolicitud(Constantes.CODS_RESP_OK, lstUsu.toString(), null);
			 * resp = respon.buildResponse(Response.Status.OK, Constantes.CODS_RESP_OK,
			 * "Se ha consultado correctamente", json, null);
			 */
			return lstUsu;
		} catch (Exception e) {
			log.error("Erro en los usuarios", e);
		}
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public Boolean guardarUsuario(Usuario usuario) {
		Response resp = null;
		try {

			usuDao.save(usuario);
			return true;

		} catch (Exception e) {
			log.error("Ha ocurrido un error almacenando el usuario", e);
		}
		return false;
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		try {
			return usuDao.findById(id);
		} catch (Exception e) {
			log.error("Ha ocurrido un error obteniedo el usuario" + id, e);
		}
		return null;

	}

	@Override
	public Usuario actualizarUsuario(Usuario usuario) {
		Response resp = null;
		try {
			return usuDao.save(usuario);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return null;
	}

	@Override
	public Boolean borrarUsuario(Usuario usuario) {
		Response resp = null;
		try {
			usuDao.delete(usuario);
			return true;
		} catch (Exception e) {
			log.error("Error", e);
		}
		return false;
	}

	@Override
	public Optional<Usuario> obtenerUsuario(Long id) {
		Optional<Usuario> usu = null;
		try {
			usu = usuDao.findById(id);
		} catch (Exception e) {
			log.error("", e);
		}
		return usu;
	}

	@Override
	public Boolean validacionesUsuario(Usuario usuario) {
		int cont = 0;
		try {
			if(usuario.getEdad().isEmpty()) {
				cont++;
			}
			if(usuario.getNombre().isEmpty()) {
				cont++;
			}
			if(usuario.getTelefono().isEmpty()) {
				cont++;
			}
			if(cont>0) {
				return false;
			}
			return true;	
				
		} catch (Exception e) {
			log.error("Error", e);
		}
		return false;
	}

}
