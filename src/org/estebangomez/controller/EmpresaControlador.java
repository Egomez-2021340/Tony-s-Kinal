package org.estebangomez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.estebangomez.bean.Empresa;
import org.estebangomez.db.Conexion;
import org.estebangomez.main.Principal;


public class EmpresaControlador implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empresa> listaEmpresa;
    
    // Text Field
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccionEmpresa;
    @FXML private TextField txtTelefonoEmpresa; 
    
    // Tabla y Columnas
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccionEmpresa;
    @FXML private TableColumn  colTelefono;
    
    //Botones
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    //Image View
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;


    @Override
        public void initialize(URL location, ResourceBundle resources) {
            cargarDatos();
    }
        public void cargarDatos(){
            tblEmpresas.setItems(getEmpresa());
            colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigoEmpresa"));
            colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa"));
            colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccion"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
        }
        public ObservableList<Empresa> getEmpresa(){
            ArrayList<Empresa> lista = new ArrayList<Empresa>();
            try{
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarEmpresas()");
               ResultSet resultado = procedimiento.executeQuery();
               while(resultado.next()){
                   lista.add(new Empresa (resultado.getInt("codigoEmpresa"),
                           resultado.getString("nombreEmpresa"),
                           resultado.getString("direccion"),
                           resultado.getString("telefono")));
               }
               
            }catch(Exception e){
                e.printStackTrace();
            }
            return listaEmpresa = FXCollections.observableArrayList(lista);
       
        }
         
        public void nuevo(){
            limpiarControles();
            switch(tipoDeOperacion){
                case NINGUNO:
                    activarControles();   
                    btnNuevo.setText("Guardar");
                    btnEliminar.setText("Cancelar");
                    btnEditar.setDisable(true);
                    btnReporte.setDisable(true);
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
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    imgNuevo.setImage(new Image("/org/estebangomez/image/Create.png"));
                    imgNuevo.setImage(new Image("/org/estebangomez/image/Delete.png"));     
                    tipoDeOperacion = operaciones.NINGUNO;
                    break;
                
            }       
        }
        
        public void seleccionEliminar(){
            txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
            txtNombreEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa());
            txtDireccionEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefonoEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono());
        }
        
       public void eliminar(){
            switch(tipoDeOperacion){
                case GUARDAR:
                    limpiarControles();
                    desactivarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    imgNuevo.setImage(new Image("/org/estebangomez/image/Create.png"));
                    imgEliminar.setImage(new Image("/org/estebangomez/image/Delete.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                    break;
                default:
                    if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null,"¿Estás Seguro de Eliminar el Registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (respuesta == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarEmpresa(?)");
                                procedimiento.setInt(1, ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                                procedimiento.execute();
                                listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedIndex());
                                limpiarControles();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Elemento");
                    }
            }
    }
        
        public void editar() throws SQLException{
            limpiarControles();
            switch(tipoDeOperacion){
                case NINGUNO:
                    if (tblEmpresas.getSelectionModel().getSelectedItem() != null){
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        imgEditar.setImage(new Image("/org/estebangomez/image/Update.png"));
                        imgReporte.setImage(new Image("/org/estebangomez/image/Cancelar.png"));
                        activarControles();
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
                    }
                    break;
                case ACTUALIZAR:
                    actualizar();
                    limpiarControles();
                    desactivarControles();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    imgEditar.setImage(new Image("/org/estebangomez/image/Update.png"));
                    imgReporte.setImage(new Image("/org/estebangomez/image/Reed.png"));
                    cargarDatos();
                    tipoDeOperacion = operaciones.NINGUNO;
                    break; 
            }
        }
        
        public void actualizar() throws SQLException{
          try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_editarEmpresa(?,?,?,?)");
            Empresa registro = (Empresa)tblEmpresas.getSelectionModel().getSelectedItem();
            registro.setNombreEmpresa(txtNombreEmpresa.getText());
            registro.setDireccion(txtDireccionEmpresa.getText());
            registro.setTelefono(txtTelefonoEmpresa.getText());
            procedimiento.setInt(1,registro.getCodigoEmpresa());
            procedimiento.setString(2,registro.getNombreEmpresa());
            procedimiento.setString(3,registro.getDireccion());
            procedimiento.setString(4,registro.getTelefono());
            procedimiento.execute();
            
          }catch(Exception e){
              e.printStackTrace();
          }
        }
        
        
        public void reporte() throws SQLException{
        switch (tipoDeOperacion){
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Actualizar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image ("/org/estebangomez/image/Update.png"));
                imgReporte.setImage(new Image ("/org/estebangomez/image/Reed.png"));
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
        public void guardar(){
            Empresa registro = new Empresa();
//              registro.setCodigoEmpresa(Integer.parseInt(txtCodigoEmpresa.getText()));
//              Ingresar codigo manualmente
                registro.setNombreEmpresa(txtNombreEmpresa.getText());
                registro.setDireccion(txtDireccionEmpresa.getText());
                registro.setTelefono(txtTelefonoEmpresa.getText());
                try{
                   PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("CALL sp_agregarEmpresa (?,?,?);");
                   procedimiento.setString(1, registro.getNombreEmpresa());
                   procedimiento.setString(2, registro.getDireccion());
                   procedimiento.setString(3,registro.getTelefono());
                   procedimiento.execute();
                   listaEmpresa.add(registro);
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
        
        public void generarReporte(){
            switch(tipoDeOperacion){
                case NINGUNO:
                    imprimirReporte();
                    break;
                case ACTUALIZAR:
                     
                    break;
            }
        }
        
        public void imprimirReporte(){
            Map parametros = new HashMap();
            parametros.put("codigoEmpresa", null);
//            generarReporte.mostrarReporte("","Reporte Empresas", parametros);
        }
        
        public void desactivarControles(){
            txtCodigoEmpresa.setEditable(false);
            txtNombreEmpresa.setEditable(false);
            txtDireccionEmpresa.setEditable(false);
            txtTelefonoEmpresa.setEditable(false);
        }
    
        public void activarControles(){
            txtCodigoEmpresa.setEditable(false);
            txtNombreEmpresa.setEditable(true);
            txtDireccionEmpresa.setEditable(true);
            txtTelefonoEmpresa.setEditable(true);
        }
    
        public void limpiarControles(){
            txtCodigoEmpresa.clear();
            txtNombreEmpresa.clear();
            txtDireccionEmpresa.clear();
            txtTelefonoEmpresa.clear();
        }
    

    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
        }   
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void regresarVentanaPrincipal() {
    escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
    
    public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }
}

