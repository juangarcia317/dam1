package com.ies.baroja;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import com.model.Jugadores;
import com.bbdd.ConexionBBDD;

public class Controller {
	private static String sConsultaJugadores = "SELECT Nombre, Procedencia,Altura,Peso,Posicion,	Nombre_equipo FROM jugadores;";

	/**
	 * Devolver lista de jugadores
	 * @return
	 */
	public static LinkedList<Jugadores> getJugadores() {
		//Objeto con la lista de jugadores
		LinkedList<Jugadores> listaJugadores = new LinkedList<Jugadores>();
		// Primero conectamos a la BBDD
		ConexionBBDD miConexion = new ConexionBBDD();
		try {
			miConexion.conectar();
			// Lanzamos la consulta
			ResultSet rsResultado = miConexion.ejecutarConsulta(sConsultaJugadores);
			if (rsResultado != null) {
				// Si hay resultado recuperamos los datos (como un FETCH de un CURSOR)
				while (rsResultado.next()) {
					// Creamos un objeto jugador por cada fila de la tabla (cada jugador)
					Jugadores jugador = new Jugadores(rsResultado.getString("Nombre"),
							rsResultado.getString("Procedencia"), rsResultado.getString("Altura"),
							String.valueOf(rsResultado.getInt("Peso")), rsResultado.getString("Posicion"),
							rsResultado.getString("Nombre_equipo"));
					// Lo insertamos en la lista
					listaJugadores.add(jugador);
				}
			} else {
				System.out.println("La consulta no devuelve resultados");
			}
			System.out.println("Número de jugadores=" + listaJugadores.size());
		} catch (SQLException sqlex) {
			System.out.println("Error: " + sqlex.getMessage());
			sqlex.printStackTrace();
		} finally {
			miConexion.desconectar();
		}
		return listaJugadores;
	}
}