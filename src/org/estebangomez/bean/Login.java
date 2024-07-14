package org.estebangomez.bean;

public class Login {
    private String usuarioMaster;
    private String passwordMaster;

    public Login() {
    }

    public Login(String usuarioMaster, String passwordMaster) {
        this.usuarioMaster = usuarioMaster;
        this.passwordMaster = passwordMaster;
    }

    public String getUsuarioMaster() {
        return usuarioMaster;
    }

    public void setUsuarioMaster(String usuarioMaster) {
        this.usuarioMaster = usuarioMaster;
    }

    public String getPasswordMaster() {
        return passwordMaster;
    }

    public void setPasswordMaster(String passwordMaster) {
        this.passwordMaster = passwordMaster;
    }
    
    
}
