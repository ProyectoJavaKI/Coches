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
    private JButton btn_coche;
    private JButton btn_eliminar_coche;
    private JButton btn_actualizar_coche;
    private JButton btn_insertar_venta;
    private JButton btn_eliminar_venta;
    private JButton btn_actualizar_venta;
    private JButton btn_cliente;
    private JButton btn_eliminar_cliente;
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
        
        btn_coche = new JButton("crear coche");
        btn_eliminar_coche = new JButton("eliminar coche");
        btn_actualizar_coche = new JButton("actualizar coche");
        
        btn_insertar_venta = new JButton("hacer venta");
        btn_eliminar_venta = new JButton("cancelar venta");
        btn_actualizar_venta = new JButton("actualizar datos de venta");
        
        btn_cliente = new JButton("alta cliente");
        btn_eliminar_cliente = new JButton("baja cliente");
        btn_actualizar_cliente = new JButton("actualizar datos cliente");
        
        /*posicion X;Y, dimencion X,Y*/
        btn_coche.setBounds(50, 30, 150, 30);
        btn_eliminar_coche.setBounds(50, 70, 150, 30);
        btn_actualizar_coche.setBounds(50, 110, 150, 30);
        
        btn_insertar_venta.setBounds(210, 30, 180, 30);
        btn_eliminar_venta.setBounds(210, 70, 180, 30);
        btn_actualizar_venta.setBounds(210, 110, 180, 30);
        
        btn_cliente.setBounds(400, 30, 180, 30);
        btn_eliminar_cliente.setBounds(400, 70, 180, 30);
        btn_actualizar_cliente.setBounds(400, 110, 180, 30);
        
        
        
        panel.add(btn_coche);
        panel.add(btn_eliminar_coche);
        panel.add(btn_actualizar_coche);
        panel.add(btn_insertar_venta);
        panel.add(btn_eliminar_venta);
        panel.add(btn_actualizar_venta);
        panel.add(btn_cliente);
        panel.add(btn_eliminar_cliente);
        panel.add(btn_actualizar_cliente);
        
        
        btn_coche.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cocheActionPerformed(evt);
            }
    });
        btn_cliente.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clienteActionPerformed(evt);
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
