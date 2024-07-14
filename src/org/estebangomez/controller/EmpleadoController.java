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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.estebangomez.bean.Empleado;
import org.estebangomez.db.Conexion;
import org.estebangomez.main.Principal;

public class EmpleadoController implements Initializable{
    private enum operaciones{GUARDAR, ELIMINAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empleado> listaEmpleados;
    
    //Buttons
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnVolver;
    @FXML private Button btnReporte;
    
    //Images
    @FXML private ImageView imgVolver;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgNuevo;
    
    
    //TextField
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNumeroEmpleado;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtGrado;
    @FXML private TextField txtTipoCodigoEmpleado;
    
    //Table and Columns
    @FXML private TableView tblEmpleado;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNumeroEmpleado;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colGrado;
    @FXML private TableColumn colCodigoTipoEmpleado;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblEmpleado.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleados"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("numeroEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("apellidosEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoContacto"));
        colGrado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("gradoCocinero"));
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("codigoTipoEmpleado"));
        
    }
    
    public void seleccionarElemento(){
        txtCodigoEmpleado.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleados()));
        txtNumeroEmpleado.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
        txtApellido.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtNombre.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getNombresEmpleado()));
        txtDireccion.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
        txtTelefono.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getGradoCocinero()));
        txtGrado.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
        txtTipoCodigoEmpleado.setText(String.valueOf(((Empleado) tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
    }
    
    
    public ObservableList<Empleado> getEmpleado(){
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_verEmpleados()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleado (resultado.getInt("codigoEmpleados"),
                        resultado.getInt("numeroEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
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
                    cargarDatos();
                    break;
                
        }
    }
    
    public void guardar(){
        Empleado registro = new Empleado ();
            registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
            registro.setApellidosEmpleado(txtApellido.getText());
            registro.setNombresEmpleado(txtNombre.getText());
            registro.setDireccionEmpleado(txtDireccion.getText());
            registro.setTelefonoContacto(txtTelefono.getText());
            registro.setGradoCocinero(txtGrado.getText());
            registro.setCodigoTipoEmpleado(Integer.parseInt(txtTipoCodigoEmpleado.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarEmpleado(?, ?, ?, ?, ?, ?, ? )");
                procedimiento.setInt(1, registro.getNumeroEmpleado());
                procedimiento.setString(2, registro.getApellidosEmpleado());
                procedimiento.setString(3, registro.getNombresEmpleado());
                procedimiento.setString(4, registro.getDireccionEmpleado());
                procedimiento.setString(5, registro.getGradoCocinero());
                procedimiento.setString(6, registro.getTelefonoContacto());
                procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
                procedimiento.execute();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
//    public void actualizar(){
//        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ActualizarEmpleado (?,?,?,?,?,?,?,?)");
//            Empleado registro = (Empleado) tblEmpleado.getSelectionModel().getSelectedItem();
//            registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
//            registro.setApellidosEmpleado(colApellido.getText());
//            registro.setNombresEmpleado(colNombre.getText());
//            registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
//            registro.setGradoCocinero(txtGradoCocinero.getText());
//            registro.setTelefonoContacto(txtTelefonoContacto.getText());
//            registro.setCodigoTipoEmpleado(Integer.parseInt(txtTipoEmpleado.getText()));
//    }
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(false);
        txtApellido.setEditable(false);
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtGrado.setEditable(false);
        txtTipoCodigoEmpleado.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(true);
        txtApellido.setEditable(true);
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtGrado.setEditable(true);
        txtTipoCodigoEmpleado.setEditable(true);
    }
  
    public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNumeroEmpleado.clear();
        txtApellido.clear();
        txtNombre.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtGrado.clear();
        txtTipoCodigoEmpleado.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
     public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }
     
}



