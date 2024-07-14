package org.estebangomez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import org.apache.commons.codec.digest.DigestUtils;
import org.estebangomez.bean.Usuario;
import org.estebangomez.db.Conexion;
import org.estebangomez.main.Principal;

public class UsuarioController implements Initializable{
   private Principal escenarioPrincipal;
   private enum operaciones{GUARDAR, NINGUNO};
   private operaciones tipoDeOperacion = operaciones.NINGUNO;
   
   
   //TEXT FIELD
   @FXML private TextField txtCodigoUsuario;
   @FXML private TextField txtNombreUsuario;
   @FXML private TextField txtApellidoUsuario;
   @FXML private TextField txtUsuario;
   @FXML private PasswordField txtPassword;
   
   //BOTONES
   @FXML private Button btnNuevo;
   @FXML private Button btnEliminar;
   
   //IMAGENES
   
   @FXML private ImageView imgNuevo;
   @FXML private ImageView imgEliminar;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/estebangomez/image/Guardar.png"));
                imgEliminar.setImage(new Image("/org/estebangomez/image/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/estebangomez/image/Create.png"));
                imgEliminar.setImage(new Image("/org/estebangomez/image/Delete.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;             
        }
    }
   
    public void guardar(){
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setUsuarioLogin(txtPassword.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuario(?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
//            procedimiento.setString(3, registro.setUsuarioLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
//            String contra = txtPassword.getText();
//            String encript = DigestUtils.md2Hex(contra);
//            System.out.println(encript);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void desactivarControles(){
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoUsuario.setEditable(true);
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);
    }
    
   public void limpiarControles(){
       txtCodigoUsuario.clear();
       txtNombreUsuario.clear();
       txtApellidoUsuario.clear();
       txtUsuario.clear();
       txtPassword.clear();
   }
   
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
   
}
    

