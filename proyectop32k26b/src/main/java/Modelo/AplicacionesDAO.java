/*
 * Autor: Anthony Suc
 * Fecha: 28/03/2026
 * Descripción:
 * DAO encargado de gestionar las operaciones CRUD de la tabla "aplicaciones".
 * Incluye registro en bitácora para auditoría de acciones.
 */

package Modelo;

import Controlador.clsAplicaciones;
import Controlador.clsUsuarioConectado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AplicacionesDAO {

    public List<clsAplicaciones> listar() {
        List<clsAplicaciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM aplicaciones";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsAplicaciones app = new clsAplicaciones();
                app.setAplcodigo(rs.getInt("Aplcodigo"));
                app.setAplnombre(rs.getString("Aplnombre"));
                app.setAplestado(rs.getString("Aplestado"));
                lista.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar aplicaciones", e);
        }

        return lista;
    }

    public void insert(clsAplicaciones app) {

        String sql = "INSERT INTO aplicaciones (Aplnombre, Aplestado) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, app.getAplnombre());
            ps.setString(2, app.getAplestado());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                int idGenerado = 0;

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }

                //Bitácora segura 
                try {
                    registrarBitacora("INSERT aplicación ID: " + idGenerado +
                                      " Nombre: " + app.getAplnombre());
                } catch (Exception ex) {
                    System.out.println("Error en bitácora (no crítico): " + ex.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar aplicación", e);
        }
    }

    public void update(clsAplicaciones app) {

        String sql = "UPDATE aplicaciones SET Aplnombre=?, Aplestado=? WHERE Aplcodigo=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, app.getAplnombre());
            ps.setString(2, app.getAplestado());
            ps.setInt(3, app.getAplcodigo());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {
                    registrarBitacora("UPDATE aplicación ID: " + app.getAplcodigo() +
                                      " Nombre: " + app.getAplnombre());
                } catch (Exception ex) {
                    System.out.println("Error en bitácora (no crítico): " + ex.getMessage());
                }

            } else {
                throw new RuntimeException("No se encontró la aplicación para actualizar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar aplicación", e);
        }
    }

    public void delete(int codigo) {

        String sql = "DELETE FROM aplicaciones WHERE Aplcodigo=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            int rows = ps.executeUpdate();

            if (rows > 0) {

                try {
                    registrarBitacora("DELETE aplicación ID: " + codigo);
                } catch (Exception ex) {
                    System.out.println("Error en bitácora (no crítico): " + ex.getMessage());
                }

            } else {
                throw new RuntimeException("No se encontró la aplicación para eliminar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar aplicación", e);
        }
    }

    public clsAplicaciones query(int codigo) {

        clsAplicaciones app = null;
        String sql = "SELECT * FROM aplicaciones WHERE Aplcodigo=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    app = new clsAplicaciones();
                    app.setAplcodigo(rs.getInt("Aplcodigo"));
                    app.setAplnombre(rs.getString("Aplnombre"));
                    app.setAplestado(rs.getString("Aplestado"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar aplicación", e);
        }

        return app;
    }

}