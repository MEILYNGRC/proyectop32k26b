package Modelo;

import Controlador.clsPerfil;
import Controlador.clsBitacora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO {

    // =======================
    // SQL PERFIL (ACTUALIZADO)
    // =======================
    private static final String SQL_SELECT =
            "SELECT percodigo, pernombre, perestado FROM perfil";

    private static final String SQL_INSERT =
            "INSERT INTO perfil(pernombre, perestado) VALUES(?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE perfil SET pernombre=?, perestado=? WHERE percodigo=?";

    private static final String SQL_DELETE =
            "DELETE FROM perfil WHERE percodigo=?";

    private static final String SQL_SELECT_ID =
            "SELECT percodigo, pernombre, perestado FROM perfil WHERE percodigo=?";


    // =======================
    // SQL BITACORA
    // =======================
    private static final String SQL_INSERT_BITACORA =
            "INSERT INTO bitacora(usucodigo, aplcodigo, bitfecha, bitip, bitequipo, bitaccion) VALUES(?, ?, ?, ?, ?, ?)";


    // =======================
    // CRUD PERFIL
    // =======================
    public List<clsPerfil> obtenerPerfiles() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsPerfil> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsPerfil p = new clsPerfil();
                p.setPercodigo(rs.getInt("percodigo"));
                p.setPernombre(rs.getString("pernombre"));
                p.setPerestado(rs.getString("perestado"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return lista;
    }

    public int insertarPerfil(clsPerfil perfil) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setString(1, perfil.getPernombre());
            stmt.setString(2, perfil.getPerestado());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public int actualizarPerfil(clsPerfil perfil) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, perfil.getPernombre());
            stmt.setString(2, perfil.getPerestado());
            stmt.setInt(3, perfil.getPercodigo());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public int eliminarPerfil(clsPerfil perfil) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setInt(1, perfil.getPercodigo());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public clsPerfil obtenerPerfilPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsPerfil perfil = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                perfil = new clsPerfil();
                perfil.setPercodigo(rs.getInt("percodigo"));
                perfil.setPernombre(rs.getString("pernombre"));
                perfil.setPerestado(rs.getString("perestado"));
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return perfil;
    }


    // =======================
    // INSERTAR BITACORA
    // =======================
    public int insertarBitacora(clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_BITACORA);

            stmt.setInt(1, bitacora.getUsucodigo());
            stmt.setInt(2, bitacora.getAplcodigo());
            stmt.setString(3, bitacora.getBitfecha());
            stmt.setString(4, bitacora.getBitip());
            stmt.setString(5, bitacora.getBitequipo());
            stmt.setString(6, bitacora.getBitaccion());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }
}