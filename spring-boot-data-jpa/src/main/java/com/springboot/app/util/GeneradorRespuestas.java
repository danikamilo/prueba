package com.springboot.app.util;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Daniel Correa
 *
 */
public class GeneradorRespuestas {

	private final Logger log = LoggerFactory.getLogger(GeneradorRespuestas.class);
	private static String nombreCortoWs = "";
	private static String peticion = "";
	private static String tipoPeticion = "";

	public GeneradorRespuestas() {
	}

	public void setNombreCortoWs(String nombreCortoWsP) {
		this.nombreCortoWs = nombreCortoWsP;
	}

	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}

	public void setTipoPeticion(String tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}

	/**
	 * 
	 * @param stResponse
	 * @param respuesta
	 * @param mensaje
	 * @param mensajeJson
	 * @param datosHeader
	 * @return
	 */
	public Response buildResponse(Response.Status stResponse, Integer respuesta, String mensaje, String mensajeJson,
			HashMap<String, String> datosHeader) {

		try {
			if (stResponse.equals(Response.Status.OK)) {
				return Response.status(stResponse).build();
			} else {
				return Response.status(stResponse).entity(mensajeJson).build();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 
	 * @param code
	 * @param mensajeJson
	 * @param datosHeader
	 * @return
	 */
	public Response validarSolicitud(Integer code, String mensajeJson, HashMap<String, String> datosHeader) {
		GeneradorRespuestas codigoError = new GeneradorRespuestas();
		Response response = null;

		try {

			HashMap<Integer, String> map = new HashMap<Integer, String>();

			for (Map.Entry<Integer, String> entry : map.entrySet()) {
				log.info("LA RESPUESTA EN LA CABECERA SERA: ");
				log.info("clave=" + entry.getKey() + ", valor=" + entry.getValue());
				if (code.equals(Constantes.CODS_RESP_OK)) {
					response = codigoError.buildResponse(Response.Status.OK, entry.getKey(), entry.getValue(),
							mensajeJson, datosHeader);
				}
				if (code.equals(Constantes.CODS_RESP_NO_CONTENT)) {
					response = codigoError.buildResponse(Response.Status.NO_CONTENT, entry.getKey(), entry.getValue(),
							mensajeJson, datosHeader);
				}
				if (code.equals(Constantes.CODS_RESP_BAD_REQUEST)) {
					response = codigoError.buildResponse(Response.Status.BAD_REQUEST, entry.getKey(), entry.getValue(),
							mensajeJson, datosHeader);
				}
				if (code.equals(Constantes.CODS_RESP_NOT_AUTORIZED)) {
					response = codigoError.buildResponse(Response.Status.UNAUTHORIZED, entry.getKey(), entry.getValue(),
							mensajeJson, datosHeader);
				}
				if (code.equals(Constantes.CODS_RESP_MANY_REQUEST)) {
					response = codigoError.buildResponse(Response.Status.BAD_REQUEST, entry.getKey(), entry.getValue(),
							mensajeJson, datosHeader);
				}
				if (code.equals(Constantes.CODS_RESP_INTERNAL_SERVER_ERROR)) {
					response = codigoError.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, entry.getKey(),
							entry.getValue(), mensajeJson, datosHeader);
				}
			}

			return response;
		} catch (Exception e) {
			log.info(
					"Ha ocurrido un error realizando la validacion de las cabeceras, el error generado fue en requestValidation "
							+ e.getMessage());
			log.error("", e);
		}
		return response;
	}
}
