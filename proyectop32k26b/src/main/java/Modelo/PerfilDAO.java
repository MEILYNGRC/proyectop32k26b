package Modelo;

import Controlador.clsPerfil;
import Controlador.clsBitacora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO {

    // =======================
    // SQL PERFIL
    // =======================
    private static final String SQL_SELECT_PERFIL =
            "SELECT perid, pernombre, perdescripcion, perestatus FROM perfil";

    private static final String SQL_INSERT_PERFIL =
            "INSERT INTO perfil(pernombre, perdescripcion, perestatus) VALUES(?, ?, ?)";

    private static final String SQL_UPDATE_PERFIL =
            "UPDATE perfil SET pernombre=?, perdescripcion=?, perestatus=? WHERE perid=?";

    private static final String SQL_DELETE_PERFIL =
            "DELETE FROM perfil WHERE perid=?";

    private static final String SQL_SELECT_PERFIL_ID =
            "SELECT perid, pernombre, perdescripcion, perestatus FROM perfil WHERE perid=?";


    // =======================
    // SQL BITACORA (AJUSTADO A TU CLASE)
    // =======================
    private static final String SQL_INSERT_BITACORA =
            "INSERT INTO bitacora(usucodigo, aplcodigo, bitfecha, bitip, bitequipo, bitaccion) VALUES(?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BITACORA =
            "SELECT bitcodigo, usucodigo, aplcodigo, bitfecha, bitip, bitequipo, bitaccion FROM bitacora";

    private static final String SQL_SELECT_BITACORA_ID =
            "SELECT bitcodigo, usucodigo, aplcodigo, bitfecha, bitip, bitequipo, bitaccion FROM bitacora WHERE bitcodigo=?";


    // =======================
    // CRUD PERFIL
    // =======================
    public List<clsPerfil> consultaPerfiles() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsPerfil> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PERFIL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsPerfil p = new clsPerfil();
                p.setPerId(rs.getInt("perid"));
                p.setPerNombre(rs.getString("pernombre"));
                p.setPerDescripcion(rs.getString("perdescripcion"));
                p.setPerEstatus(rs.getString("perestatus"));
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
            stmt = conn.prepareStatement(SQL_INSERT_PERFIL);

            stmt.setString(1, perfil.getPerNombre());
            stmt.setString(2, perfil.getPerDescripcion());
            stmt.setString(3, perfil.getPerEstatus());

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
            stmt = conn.prepareStatement(SQL_UPDATE_PERFIL);

            stmt.setString(1, perfil.getPerNombre());
            stmt.setString(2, perfil.getPerDescripcion());
            stmt.setString(3, perfil.getPerEstatus());
            stmt.setInt(4, perfil.getPerId());

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
            stmt = conn.prepareStatement(SQL_DELETE_PERFIL);
            stmt.setInt(1, perfil.getPerId());

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
            stmt = conn.prepareStatement(SQL_SELECT_PERFIL_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                perfil = new clsPerfil();
                perfil.setPerId(rs.getInt("perid"));
                perfil.setPerNombre(rs.getString("pernombre"));
                perfil.setPerDescripcion(rs.getString("perdescripcion"));
                perfil.setPerEstatus(rs.getString("perestatus"));
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
    // CRUD BITACORA
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

    public List<clsBitacora> consultarBitacoras() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsBitacora> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BITACORA);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsBitacora b = new clsBitacora();
                b.setBitcodigo(rs.getInt("bitcodigo"));
                b.setUsucodigo(rs.getInt("usucodigo"));
                b.setAplcodigo(rs.getInt("aplcodigo"));
                b.setBitfecha(rs.getString("bitfecha"));
                b.setBitip(rs.getString("bitip"));
                b.setBitequipo(rs.getString("bitequipo"));
                b.setBitaccion(rs.getString("bitaccion"));
                lista.add(b);
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

    public clsBitacora obtenerBitacoraPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsBitacora b = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BITACORA_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                b = new clsBitacora();
                b.setBitcodigo(rs.getInt("bitcodigo"));
                b.setUsucodigo(rs.getInt("usucodigo"));
                b.setAplcodigo(rs.getInt("aplcodigo"));
                b.setBitfecha(rs.getString("bitfecha"));
                b.setBitip(rs.getString("bitip"));
                b.setBitequipo(rs.getString("bitequipo"));
                b.setBitaccion(rs.getString("bitaccion"));
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return b;
    }
}