/*
Esteban Fernando Gomez Loaiza
codigo Academico: IN5BM
Carné: 2021340
fecha de Inicio:  12/04/2023 ; 08:17
fecha de modificación: 31/05/2023 */
package org.estebangomez.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.estebangomez.controller.EmpleadoController;
import org.estebangomez.controller.EmpresaControlador;
import org.estebangomez.controller.LoginController;
import org.estebangomez.controller.MenuPrincipalController;
import org.estebangomez.controller.PlatoController;
import org.estebangomez.controller.PresupuestoController;
import org.estebangomez.controller.ProductoController;
import org.estebangomez.controller.ProgramadorController;
import org.estebangomez.controller.TipoEmpleadoController;
import org.estebangomez.controller.TipoPlatoController;
import org.estebangomez.controller.UsuarioController;
import org.estebangomez.controller.ProductoHasPlatoController;
import org.estebangomez.controller.ServicioController;
import org.estebangomez.controller.ServiciosHasEmpleadosController;
import org.estebangomez.controller.ServiciosHasPlatosController;

/**
 *
 * @author Esteban Gómez
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/estebangomez/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
   
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal 2023");
        escenarioPrincipal.getIcons().add(new Image("/org/estebangomez/image/Logo2.png"));
            //Parent root = FXMLLoader.load(getClass().getResource("/org/estebangomez/view/MenuPrincipalView.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/org/estebangomez/view/ProgramadorView.fxml"));
            // Parent root = FXMLLoader.load(getClass().getResource("/org/estebangomez/view/EmpresaView.fxml"));
           //Scene escena = new Scene(root);
           //escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();
            menuPrincipal();
            escenarioPrincipal.setResizable(false);
    
    }
    public static void main(String[] args) {
        launch(args);
    }

    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menu = (MenuPrincipalController)  cambiarEscena("MenuPrincipalView.fxml",470,470);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
         ProgramadorController programador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml",650,650 );
         programador.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpresa(){
        try{
            EmpresaControlador empresa = (EmpresaControlador) cambiarEscena("EmpresaView.fxml", 810, 475);
            empresa.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado() throws Exception{
        try{
            EmpleadoController empleado = (EmpleadoController) cambiarEscena("EmpleadoView.fxml", 748, 452);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public void ventanaPresupuesto(){
        try{
            PresupuestoController presupuesto = (PresupuestoController) cambiarEscena("PresupuestoView.fxml", 770,448);
            presupuesto.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoEmpleado() throws Exception{
        try{
            TipoEmpleadoController TipoEmpleado = (TipoEmpleadoController) cambiarEscena ("TipoEmpleadoView.fxml",748 ,435);
            TipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProducto(){
        try{
            ProductoController Producto = (ProductoController) cambiarEscena ("ProductoView.fxml",748,437 );
            Producto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoPlato(){
        try{
            TipoPlatoController TipoPlato = (TipoPlatoController) cambiarEscena("TipoPlatoView.fxml",790, 441);
            TipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
    try{
        LoginController login = (LoginController) cambiarEscena("LoginView.fxmls",545,400);
        login.setEscenarioPrincipal(this);
     }catch(Exception e){
            e.printStackTrace();  
     }
    }

    public void ventanaUsuario(){
        try{
            UsuarioController usuario = (UsuarioController) cambiarEscena("UsuariosView.fxml", 748, 407);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPlato(){
        try{
            PlatoController Plato = (PlatoController) cambiarEscena("PlatoView.fxml",748,437);
            Plato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicio(){
        try{
            ServicioController Servicio = (ServicioController) cambiarEscena("ServicioView.fxml",748, 437);
            Servicio.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductoHasPlato(){
        try{
            ProductoHasPlatoController ProductoHasPlato = (ProductoHasPlatoController) cambiarEscena("ProductoHasPlato.fxml",748,437);
            ProductoHasPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicioHasEmpleado(){
        try{
            ServiciosHasEmpleadosController ServicioHasEmpleado = (ServiciosHasEmpleadosController) cambiarEscena("ServicioHasEmpleado.fxml",748, 437);
            ServicioHasEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
    public void ventanaServicioHasPlato(){
        try{
            ServiciosHasPlatosController ServiciosHasPlatos = (ServiciosHasPlatosController) cambiarEscena("ServicioHasPlato.fxml",748,437);
            ServiciosHasPlatos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena =  new Scene((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        
        return resultado;
    }
    
}

    