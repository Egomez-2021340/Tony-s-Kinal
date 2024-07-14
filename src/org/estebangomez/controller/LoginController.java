
package org.estebangomez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.estebangomez.bean.Login;
import org.estebangomez.bean.Usuario;
import org.estebangomez.db.Conexion;
import org.estebangomez.main.Principal;

public class LoginController implements Initializable{
    private Principal escenarioPrincipal;
    private ObservableList<Usuario> listaUsuario;
    
    @FXML private TextField txtUsuario;
    @FXML private TextField txtPassword;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {   
    }
    
    public ObservableList<Usuario> getUsuario(){
        ArrayList<Usuario> lista =  new ArrayList<Usuario> ();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarUsuarios()} ");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Usuario(resultado.getInt("codigoUuario"),
                                        resultado.getString("nombreUsuario"),
                                        resultado.getString("apellidoUsuario"),
                                        resultado.getString("usuarioLogin"),
                                        resultado.getString("contrasena")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaUsuario = FXCollections.observableArrayList(lista);
    }
    
//    @FXML
//    private void sesion(){
//        Login login = new Login();
//        int x = 0;
//        boolean bandera = false;
//        login.setUsuarioMaster(txtUsuario.getText());
//        login.setPasswordMaster(txtPassword.getText());
//        while (x < getUsuario().size()){
//            String user = getUsuario().get(x).getUsuarioLogin();
//            String pass = getUsuario().get(x).getContrasena();
//            if (user.equals(login.getUsuarioMaster() && pass.equals(login.getPasswordMaster()))){
//            JOPtionPane.showMessageDialog(null, "Sesión iniciada\n"
//            + getUsuarioLogin().get(x)).getUsuarioLogin
//        }
// }
//   }
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
