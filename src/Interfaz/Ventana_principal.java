package Interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana_principal extends JFrame implements ActionListener, WindowListener{
    
    private JPanel panel;
    private JButton btn_COCHES;
    private JButton btn_CONCESIONARIO;
    private JButton btn_actualizar_coche;
    private JButton btn_DISTRIBUCION;
    private JButton btn_eliminar_venta;
    private JButton btn_actualizar_venta;
    private JButton btn_CLIENTES;
    private JButton btn_VENTAS;
    private JButton btn_actualizar_cliente;
    
    Ventana_principal(){
        panel = (JPanel)this.getContentPane();
        panel.setLayout(null);
        
        /*dar valores a las caracteristicas del PANEL*/
        this.setTitle("Inicio"); //titulo de la aplicacion
        
/*cuando este todo listo quitar el comentario de resizable*/        
//        this.setResizable(false);// no permite redimencionar
        this.setSize(640, 220); //dimencion X, Y
        this.setLocation(200, 200); //posicion X, Y
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE); //Cerrar programa al pulsar la X
        
        btn_COCHES = new JButton("COCHES");
        btn_CONCESIONARIO = new JButton("CONCESIONARIO");
        btn_actualizar_coche = new JButton("-------------");
        
        btn_DISTRIBUCION = new JButton("DISTRIBUCION");
        btn_eliminar_venta = new JButton("-------------");
        btn_actualizar_venta = new JButton("-------------");
        
        btn_CLIENTES = new JButton("CLIENTES");
        btn_VENTAS = new JButton("VENTAS");
        btn_actualizar_cliente = new JButton("-------------");
        
        /*posicion X;Y, dimencion X,Y*/
        btn_COCHES.setBounds(50, 30, 150, 30);
        btn_CONCESIONARIO.setBounds(50, 70, 150, 30);
        btn_actualizar_coche.setBounds(50, 110, 150, 30);
        
        btn_DISTRIBUCION.setBounds(210, 30, 180, 30);
        btn_eliminar_venta.setBounds(210, 70, 180, 30);
        btn_actualizar_venta.setBounds(210, 110, 180, 30);
        
        btn_CLIENTES.setBounds(400, 30, 180, 30);
        btn_VENTAS.setBounds(400, 70, 180, 30);
        btn_actualizar_cliente.setBounds(400, 110, 180, 30);
        
        
        
        panel.add(btn_COCHES);
        panel.add(btn_CONCESIONARIO);
        panel.add(btn_actualizar_coche);
        panel.add(btn_DISTRIBUCION);
        panel.add(btn_eliminar_venta);
        panel.add(btn_actualizar_venta);
        panel.add(btn_CLIENTES);
        panel.add(btn_VENTAS);
        panel.add(btn_actualizar_cliente);
        
        
        btn_COCHES.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cocheActionPerformed(evt);
            }
    });
        btn_CLIENTES.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clienteActionPerformed(evt);
            }
    });
        btn_CONCESIONARIO.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consecionario_cocheActionPerformed(evt);
            }
    });
        
        btn_DISTRIBUCION.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertar_distribucionActionPerformed(evt);
            }
    });
        btn_VENTAS.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VENTASActionPerformed(evt);
            }
    });
        
        
        this.setVisible(true);
    }
    
    private void btn_cocheActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        Ventana_coche v = new Ventana_coche();
    }
    
    private void btn_clienteActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        Ventana_cliente v = new Ventana_cliente();
    }
    
    private void btn_consecionario_cocheActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        Ventana_Concesionario v = new Ventana_Concesionario();
    }
    
    private void btn_insertar_distribucionActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        Ventana_Distribucion v = new Ventana_Distribucion();
    }
    
    private void btn_VENTASActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        Ventana_Venta v = new Ventana_Venta();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowOpened(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
