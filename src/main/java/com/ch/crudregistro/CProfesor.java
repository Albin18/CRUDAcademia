
package com.ch.crudregistro;

import com.ch.crudregistro.Conexion.Mysql;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class CProfesor {
    
    int codigoProfesor;
    String nombreProfesor;
    String apellidoProfesor;
    String asignacion;

    public int getCodigoProfesor() {
        return codigoProfesor;
    }

    public void setCodigoProfesor(int codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApellidoProfesor() {
        return apellidoProfesor;
    }

    public void setApellidoProfesor(String apellidoProfesor) {
        this.apellidoProfesor = apellidoProfesor;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }
    
    public void InsertarProfesor(JTextField paramNombreProfesor, JTextField paramApellidoProfesor, JTextField paramAsignacion){
        
        setNombreProfesor(paramNombreProfesor.getText());
        setApellidoProfesor(paramApellidoProfesor.getText());
        setAsignacion(paramAsignacion.getText());
        
        Mysql objetoConexion = new Mysql();
        
        String consulta = "INSERT INTO Profesores (nombre, apellido, asignacion) VALUES (?, ?, ?);";
        
         if(paramNombreProfesor.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Nombre Vacio");
        } 
        else if (paramApellidoProfesor.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Apellido Vacio");   
        }
        else if(paramAsignacion.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ninguna Asignatura Agregada");
        }
        else{
        
        try{
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);

            cs.setString(1, getNombreProfesor());
            cs.setString(2, getApellidoProfesor());
            cs.setString(3, getAsignacion());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Profesor ingresado Correctamente");
        } catch (Exception e ){
            JOptionPane.showMessageDialog(null, "No se pudo ingresar el Profesor" + e.toString());
        }
      }
    }
    
    
    
    public void MostrarProfesor(JTable paramTablaProfesor){
        
        Mysql objetoConexion = new Mysql();
    
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Asignacion");
        
    
        String sqlP = "";
        sqlP = "SELECT * FROM profesores";
        
        
        String[] datos = new String [4];
        Statement st;
                
                try{
                    st = objetoConexion.establecerConexion().createStatement();
                    ResultSet rs = st.executeQuery(sqlP);
                    
                    while(rs.next()){
                        datos[0] = rs.getString(1);
                        datos[1] = rs.getString(2);
                        datos[2] = rs.getString(3);
                        datos[3] = rs.getString(4);
                        modelo.addRow(datos);
                    }
                    
                    paramTablaProfesor.setModel(modelo);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "No se muestra La informacion" + e.toString());
                }
    }
    
    
    
    public void SeleccionarProfesor(JTable paramTablaProfesor, JTextField paramIdProfesor, JTextField paramNombreProfesor, JTextField paramApellidoProfesor, JTextField paramAsignacion){
        
        try{
            int fila = paramTablaProfesor.getSelectedRow();
            
            if(fila >= 0){
                paramIdProfesor.setText(paramTablaProfesor.getValueAt(fila, 0).toString());
                paramNombreProfesor.setText(paramTablaProfesor.getValueAt(fila, 1).toString());
                paramApellidoProfesor.setText(paramTablaProfesor.getValueAt(fila, 2).toString());
                paramAsignacion.setText(paramTablaProfesor.getValueAt(fila, 3).toString());    
            } else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error de Seleccion" + e.toString());
        }
    }
    
    public void ModificarProfesor(JTextField paramCodigoProfesor, JTextField paramNombreProfesor, JTextField paramApellidoProfesor, JTextField paramAsignacion){  
        
        setCodigoProfesor(Integer.parseInt(paramCodigoProfesor.getText()));
        setNombreProfesor(paramNombreProfesor.getText());
        setApellidoProfesor(paramApellidoProfesor.getText());
        setAsignacion(paramAsignacion.getText());
        
        Mysql objetoConexion = new Mysql();
        String consulta = "UPDATE profesores SET profesores.nombre = ?, profesores.apellido = ?, profesores.asignacion = ? WHERE profesor.idProfesor = ?;";
        
        
        if(paramNombreProfesor.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Nombre Vacio");
        } 
        else if (paramApellidoProfesor.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Apellido Vacio");   
        }
        else if(paramAsignacion.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ninguna Asignacion Agregada");
        }
        else{
        
        try{
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getNombreProfesor());
            cs.setString(2, getApellidoProfesor());
            cs.setString(3, getAsignacion());
            cs.setInt(4, getCodigoProfesor());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificacion Exitosa");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error de Modificacion: " + e.toString());
        }
      } 
    }
    
    
    public void EliminarProfesor(JTextField paramCodigoProfesor) {
        
        setCodigoProfesor(Integer.parseInt(paramCodigoProfesor.getText()));
        
        Mysql objetoConexion = new Mysql();
        
       String eliminar = "DELETE FROM profesores WHERE profesores.idProfesor = ?";
        
       try{
           
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(eliminar);
            cs.setInt(1, getCodigoProfesor());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se Elimino el registro");
     
       } catch (Exception e){
           JOptionPane.showMessageDialog(null, "No se Pudo eLiminar: " + e.toString());
       }
       
    }
    
    
    
    
    
    
    
    
    
}
