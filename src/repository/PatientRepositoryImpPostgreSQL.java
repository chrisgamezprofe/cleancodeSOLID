package repository;

import interfaces.PatientRepository;
import java.util.List;
import model.Patient;
import utils.Constant;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.ConnectionServer;

public class PatientRepositoryImpPostgreSQL implements PatientRepository{

    @Override
    public boolean create(Patient patient) {
        Constant.QUERY ="INSERT INTO patients(name, age, description) "
                + "VALUES(?,?,?)";
        Connection con = ConnectionServer.postgreServerConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(Constant.QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getDescription());
            Constant.MESSAGE = "Guardado con exito!";
            return pstmt.executeUpdate()>0;
        } catch (SQLException ex) {
            Constant.MESSAGE = ex.getMessage();
            return false;
        }
    }

    @Override
    public Patient read(int id) {
        Constant.QUERY ="SELECT * FROM patients WHERE id = ? ";
        Connection con = ConnectionServer.postgreServerConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(Constant.QUERY);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            Patient patient=null;
            Constant.MESSAGE = "No existe el paciente";
            if(rs.next()){
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setAge(rs.getInt("age"));
                patient.setName(rs.getString("name"));
                patient.setDescription(rs.getString("description"));
                Constant.MESSAGE = "Consultado con exito!";
            }
            
            return patient;
        } catch (SQLException ex) {
            Constant.MESSAGE = ex.getMessage();
            return null;
        }
    }

    @Override
    public List<Patient> readAll() {
      Constant.QUERY ="SELECT * FROM patients ORDER BY name";
        Connection con = ConnectionServer.postgreServerConnection();
        List<Patient> patients=new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(Constant.QUERY);
            
            Patient patient=null;
            while(rs.next()){
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setAge(rs.getInt("age"));
                patient.setName(rs.getString("name"));
                patient.setDescription(rs.getString("description"));
                patients.add(patient);
            }
        } catch (SQLException ex) {
            Constant.MESSAGE = ex.getMessage();
        } 
        return patients;
    }

    @Override
    public boolean update(Patient patient) {
        Constant.QUERY ="UPDATE patients SET name=?, age=?, description=? WHERE id=?";
        Connection con = ConnectionServer.postgreServerConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(Constant.QUERY);
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getDescription());
            pstmt.setInt(4, patient.getId());
            Constant.MESSAGE = "Actualizado con exito!";
            return pstmt.executeUpdate()>0;
        } catch (SQLException ex) {
            Constant.MESSAGE = ex.getMessage();
            return false;
        }    
    }

    @Override
    public boolean delete(int id) {
        Constant.QUERY ="DELETE FROM patients WHERE id=?";
        Connection con = ConnectionServer.postgreServerConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(Constant.QUERY);
            pstmt.setInt(1, id);
            Constant.MESSAGE = "Eliminado con exito!";
            return pstmt.executeUpdate()>0;
        } catch (SQLException ex) {
            Constant.MESSAGE = ex.getMessage();
            return false;
        }    
    }
    
}
