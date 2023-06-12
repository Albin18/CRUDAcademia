
package com.ch.crudregistro;

import javax.swing.JTextField;
import com.ch.crudregistro.Conexion.Mysql;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class CAlumnos {
    int codigo;
    String nombreAlumnos;
    String apellidoAlumnos;
    String asignatura;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreAlumnos() {
        return nombreAlumnos;
    }

    public void setNombreAlumnos(String nombreAlumnos) {
        this.nombreAlumnos = nombreAlumnos;
    }

    public String getApellidoAlumnos() {
        return apellidoAlumnos;
    }

    public void setApellidoAlumnos(String apellidoAlumnos) {
        this.apellidoAlumnos = apellidoAlumnos;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    
    
    public void InsertarAlumno(JTextField paramNombres, JTextField paramApellidos, JTextField paramAsignatura) {
     
        setNombreAlumnos(paramNombres.getText());
        setApellidoAlumnos(paramApellidos.getText());
        setAsignatura(paramAsignatura.getText());
        
        Mysql objetoConexion = new Mysql();
        
        String consulta = "INSERT INTO Alumnos (nombres, apellidos, asignatura) VALUES (?, ?, ?);";
        
        if(paramNombres.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Nombre Vacio");
        } 
        else if (paramApellidos.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Apellido Vacio");   
        }
        else if(paramAsignatura.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ninguna Asignatura Agregada");
        }
        else{
        
            try{
                CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
                
                cs.setString(1, getNombreAlumnos());
                cs.setString(2, getApellidoAlumnos());
                cs.setString(3, getAsignatura());
                cs.execute();
                JOptionPane.showMessageDialog(null, "Alumno ingresado Correctamente");
            } catch (Exception e ){
                JOptionPane.showMessageDialog(null, "No se pudo ingresar el Alumno" + e.toString());
            }
        }
    } 
    
    public void MostrarAlumno(JTable paramTablaAlumnos){
        Mysql objetoConexion = new Mysql();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
       // TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        //paramTablaTotalAlumnos.setRowSorter(OrdenarTabla);
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Asignatura");
        
        paramTablaAlumnos.setModel(modelo);
        
        String sql = "";
        sql = "SELECT * FROM alumnos";
      
        String[] datos = new String [4];
        Statement st;
                
                try{
                    st = objetoConexion.establecerConexion().createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    
                    while(rs.next()){
                        datos[0] = rs.getString(1);
                        datos[1] = rs.getString(2);
                        datos[2] = rs.getString(3);
                        datos[3] = rs.getString(4);
                        modelo.addRow(datos);
                    }
                    
                    paramTablaAlumnos.setModel(modelo);
                    
                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, "No se pudo mostrar la Tabla"+ e.toString());
                }
        
    }
    
    
    public void SeleccionarAlumno(JTable paramTablaAlumnos, JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JTextField paramAsignatura){
        
        try{
            int fila = paramTablaAlumnos.getSelectedRow();
            
            if(fila >= 0){
                paramId.setText(paramTablaAlumnos.getValueAt(fila, 0).toString());
                paramNombres.setText(paramTablaAlumnos.getValueAt(fila, 1).toString());
                paramApellidos.setText(paramTablaAlumnos.getValueAt(fila, 2).toString());
                paramAsignatura.setText(paramTablaAlumnos.getValueAt(fila, 3).toString());    
            } else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error de Seleccion" + e.toString());
        }
    }
    
    public void ModificarAlumno(JTextField paramCodigo, JTextField paramNombres, JTextField paramApellidos, JTextField paramAsignatura){  
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreAlumnos(paramNombres.getText());
        setApellidoAlumnos(paramApellidos.getText());
        setAsignatura(paramAsignatura.getText());
        
        Mysql objetoConexion = new Mysql();
        String consulta = "UPDATE alumnos SET alumnos.nombres = ?, alumnos.apellidos = ?, alumnos.asignatura = ? WHERE alumnos.idAlumno = ?;";
        
       if(paramNombres.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Nombre Vacio");
        } 
        else if (paramApellidos.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Campo de Apellido Vacio");   
        }
        else if(paramAsignatura.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ninguna Asignatura Agregada");
        }
        else{
            
        try{
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getNombreAlumnos());
            cs.setString(2, getApellidoAlumnos());
            cs.setString(3, getAsignatura());
            cs.setInt(4, getCodigo());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificacion Exitosa");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error de Modificacion: " + e.toString());
        }
      }
    }
    
    
    public void EliminarAlumno(JTextField paramCodigo) {
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        
        Mysql objetoConexion = new Mysql();
        
       String eliminar = "DELETE FROM alumnos WHERE alumnos.idAlumno = ?";
        
       try{
           
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(eliminar);
            cs.setInt(1, getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se Elimino el registro");
     
       } catch (Exception e){
           JOptionPane.showMessageDialog(null, "No se Pudo eLiminar: " + e.toString());
       }
       
    }
    
    
    
}
