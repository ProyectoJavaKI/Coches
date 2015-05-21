package Interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Ventana_cliente  extends JDialog implements ActionListener, WindowListener{
    
    private ConexionDB conexion = new ConexionDB();
    
/*Jpanel que contiene a todo*/
    private JPanel panel;
    private JScrollPane scl_tabla;
    private JTable tbl_tabla;
    private JButton btn_insertar_cliente;
    private JButton btn_eliminar_cliente;
    private JButton btn_actualizar_cliente;
    private JLabel lbl_dni;
    private JLabel lbl_nuevo_nombre;
    private JLabel lbl_nuevo_modelo;
    private JLabel lbl_nuevo_ciudad;
    private JLabel lbl_actualiza_nombre;
    private JLabel lbl_actualiza_apellido;
    private JLabel lbl_actualiza_ciudad;
    private JLabel lbl_insert_cliente;
    private JLabel lbl_elim_cliente;
    private JLabel lbl_actual_cliente;
    private JTextField txt_dni;
    private JTextField txt_nombre;
    private JTextField txt_apellido;
    private JTextField txt_ciudad;
    private JTextField txt_actualiza_nombre;
    private JTextField txt_actualiza_apellido;
    private JTextField txt_actualiza_ciudad;
    private JLabel lbl_descrip_tabla;
    private JComboBox cmb_eliminar_cliente;
    private JComboBox cmb_actualiza_cliente;
    private MiModelo modelo = new MiModelo();
    private MiModeloCombo miComboModel_elimina = new MiModeloCombo();
    private MiModeloCombo miComboModel_actualiza = new MiModeloCombo();
    
    
    Ventana_cliente(){
        panel = (JPanel)this.getContentPane();
        panel.setLayout(null);
        
        /*dar valores a las caracteristicas del PANEL*/
        this.setTitle("Tabla Coches"); //titulo de la aplicacion
        
/*cuando este todo listo quitar el comentario de resizable*/        
//        this.setResizable(false);// no permite redimencionar
        this.setSize(800, 550); //dimencion X, Y
        this.setLocation(100, 20); //posicion X, Y
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Cerrar programa al pulsar la X
        setModal(true);
        
        /*creamos los componentes*/
        tbl_tabla = new JTable(modelo);
        scl_tabla = new JScrollPane(tbl_tabla);
        btn_insertar_cliente = new JButton("insertar cliente");
        btn_eliminar_cliente = new JButton("eliminar cliente");
        btn_actualizar_cliente = new JButton("Actualizar detos de clientes");
        lbl_descrip_tabla = new JLabel("Informacion de los clientes");
        lbl_dni = new JLabel("DNI");
        lbl_nuevo_nombre = new JLabel("Nombre del Cliente");
        lbl_nuevo_modelo = new JLabel("Apellido del Cliente");
        lbl_nuevo_ciudad = new JLabel("Ciudad del Cliente");
        lbl_actualiza_nombre = new JLabel("actualizar nombre");
        lbl_actualiza_apellido = new JLabel("actualizar apellido");
        lbl_actualiza_ciudad = new JLabel("actualizar ciudad");
        lbl_insert_cliente = new JLabel("Crear Nuevo cliente");
        lbl_elim_cliente = new JLabel("Eliminar cliente");
        lbl_actual_cliente = new JLabel("Actualozar datos de un cliente");
        txt_dni = new JTextField();
        txt_nombre = new JTextField();
        txt_apellido = new JTextField();
        txt_ciudad = new JTextField();
        txt_actualiza_nombre = new JTextField();
        txt_actualiza_apellido = new JTextField();
        txt_actualiza_ciudad = new JTextField();
        cmb_eliminar_cliente = new JComboBox(miComboModel_elimina);
        cmb_actualiza_cliente = new JComboBox(miComboModel_actualiza);
        
        /*damos posicion X,Y, y dimencion X,Y*/
        scl_tabla.setBounds(380, 50, 400, 250);
        btn_insertar_cliente.setBounds(50, 220, 150, 30);
        btn_eliminar_cliente.setBounds(460, 390, 150, 30);
        btn_actualizar_cliente.setBounds(30, 460, 200, 30);
        lbl_dni.setBounds(30, 60, 150, 30);
        lbl_nuevo_nombre.setBounds(30, 100, 150, 30);
        lbl_nuevo_modelo.setBounds(30, 140, 150, 30);
        lbl_nuevo_ciudad.setBounds(30, 180, 150, 30);
        lbl_descrip_tabla.setBounds(400, 20, 180, 30);
        lbl_insert_cliente.setBounds(40, 30, 150, 30);
        lbl_elim_cliente.setBounds(430, 340, 150, 30);
        lbl_actual_cliente.setBounds(30, 260, 200, 30);
        lbl_actualiza_nombre.setBounds(30, 330, 150, 30);
        lbl_actualiza_apellido.setBounds(30, 370, 150, 30);
        lbl_actualiza_ciudad.setBounds(30, 410, 150, 30);
        txt_dni.setBounds(200, 60, 150, 30);
        txt_nombre.setBounds(200, 100, 150, 30);
        txt_apellido.setBounds(200, 140, 150, 30);
        txt_ciudad.setBounds(200, 180, 150, 30);
        txt_actualiza_nombre.setBounds(200, 330, 150, 30);
        txt_actualiza_apellido.setBounds(200, 370, 150, 30);
        txt_actualiza_ciudad.setBounds(200, 410, 150, 30);
        cmb_eliminar_cliente.setBounds(550, 340, 90, 30);
        cmb_actualiza_cliente.setBounds(30, 290, 90, 30);
        
        /*añadimos datos a la tabla*/
        modelo.addColumn("DNI");
        modelo.addColumn("nombre");
        modelo.addColumn("apellido");
        modelo.addColumn("ciudad");
/*Este apartado se rellena con la informacion de la bbdd (con una sentencia select*/
        cargar_tabla(modelo, cmb_eliminar_cliente, cmb_actualiza_cliente);
        /*añadiendo los elementos al panel*/
        panel.add(scl_tabla);
        panel.add(btn_insertar_cliente);
        panel.add(btn_eliminar_cliente);
        panel.add(btn_actualizar_cliente);
        panel.add(lbl_descrip_tabla);
        panel.add(lbl_dni);
        panel.add(lbl_nuevo_nombre);
        panel.add(lbl_nuevo_modelo);
        panel.add(lbl_nuevo_ciudad);
        panel.add(lbl_insert_cliente);
        panel.add(lbl_elim_cliente);
        panel.add(lbl_actual_cliente);
        panel.add(lbl_actualiza_nombre);
        panel.add(lbl_actualiza_apellido);
        panel.add(lbl_actualiza_ciudad);
        panel.add(txt_dni);
        panel.add(txt_nombre);
        panel.add(txt_apellido);
        panel.add(txt_ciudad);
        panel.add(txt_actualiza_nombre);
        panel.add(txt_actualiza_apellido);
        panel.add(txt_actualiza_ciudad);
        panel.add(cmb_eliminar_cliente);
        panel.add(cmb_actualiza_cliente);
        
/*añadido del action listener*/        
        btn_insertar_cliente.addActionListener(new java.awt.event.ActionListener() {
       
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertar_clienteActionPerformed(evt);
            }
    });
        btn_eliminar_cliente.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_clienteActionPerformed(evt);
            }
    });
        btn_actualizar_cliente.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_clienteActionPerformed(evt);
            }
    });
        this.setVisible(true);//el set visible del panel_maestro debe de ser el ultimo siempre
    }

/*carga la tabla con la insfomacion de la bbdd*/    
    void cargar_tabla(MiModelo modelotabla, JComboBox combo_elimina, JComboBox combo_actualiza){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `coches`.`clientes`";
            
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[4];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("DNI"));
                fila[1] =  (String) (rs.getObject("NOMBRE"));
                fila[2] =  (String) (rs.getObject("APELLIDO"));
                fila[3] =  (String) (rs.getObject("CIUDAD"));
                modelotabla.addRow ( fila ); // Añade una fila al final de la tabla
                combo_elimina.addItem(fila[0]); // Añade una fila al final combo elimina
                combo_actualiza.addItem(fila[0]); // Añade una fila al final del combo actualiza
            } //fin while
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
/*inserta correctamente*/
    void inserta(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        if(!txt_dni.getText().isEmpty() && !txt_nombre.getText().isEmpty() && !txt_apellido.getText().isEmpty() && !txt_ciudad.getText().isEmpty() )
        {
        try (Statement st = miConexion.createStatement()) {
            
            String insertar = "INSERT INTO `coches`.`clientes`(`DNI`, `NOMBRE`,`APELLIDO`,`CIUDAD`)"
                        + " VALUES ('"
                        + txt_dni.getText() + "', '"
                        + txt_nombre.getText() + "', '"
                        + txt_apellido.getText() + "', '"
                        + txt_ciudad.getText() + "')";
            st.execute(insertar);
            modelo.setRowCount(0);
            cmb_eliminar_cliente.removeAllItems();
            cmb_actualiza_cliente.removeAllItems();
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_cliente.class.getName()).log(Level.SEVERE, null, ex);
            modelo.setRowCount(0);
            cmb_eliminar_cliente.removeAllItems();
            cmb_actualiza_cliente.removeAllItems();
        }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "hay campos vacios, porfavor rellenelos corectamente");
            modelo.setRowCount(0);
            cmb_eliminar_cliente.removeAllItems();
            cmb_actualiza_cliente.removeAllItems();
        }
    }
    
    void eliminar(){
        Object[] opciones = {"Sí, Eliminar este cliente",  "No, no eliminar"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a eliminar un cohce ¿es ta seguro?: ",
        "Confirmar eliminación del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            String opt = (String) cmb_eliminar_cliente.getSelectedItem();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "DELETE FROM `coches`.`clientes` WHERE `DNI` = '"+opt+"'";
                st.execute(insertar);
                modelo.setRowCount(0);
                cmb_eliminar_cliente.removeAllItems();
                cmb_actualiza_cliente.removeAllItems();
                st.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Ventana_cliente.class.getName()).log(Level.SEVERE, null, ex);
                modelo.setRowCount(0);
                cmb_eliminar_cliente.removeAllItems();
                cmb_actualiza_cliente.removeAllItems();
            }
        }
    }

    void actualizar(){
        Object[] opciones = {"Sí, actualizar datos de este cliente",  "No, no actualizar datos de este cliente"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a actualizar la informacion de un cohce ¿es ta seguro?: ",
        "Confirmar actualizacion del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            if(!txt_actualiza_nombre.getText().isEmpty() && !txt_actualiza_apellido.getText().isEmpty() && !txt_actualiza_ciudad.getText().isEmpty() )
            {
                Connection miConexion = (Connection) conexion.ConectarMysql();
                try (Statement st = miConexion.createStatement()) {

                    String insertar = "UPDATE `coches`.`clientes` SET `NOMBRE`='"+txt_actualiza_nombre.getText()+"',`APELLIDO`='"+txt_actualiza_apellido.getText()+"', `CIUDAD`='"+txt_actualiza_ciudad.getText()+"' WHERE `DNI`='"+cmb_actualiza_cliente.getSelectedItem()+"'";
                    st.executeUpdate(insertar);
                    modelo.setRowCount(0);
                    cmb_actualiza_cliente.removeAllItems();
                    cmb_eliminar_cliente.removeAllItems();
                    st.close();
                } 
                catch (SQLException ex) {
                    Logger.getLogger(Ventana_cliente.class.getName()).log(Level.SEVERE, null, ex);
                    modelo.setRowCount(0);
                    cmb_eliminar_cliente.removeAllItems();
                    cmb_actualiza_cliente.removeAllItems();
                }
            }
                else
            {
                JOptionPane.showMessageDialog(null, "hay campos sin rellenas, para la correcta actualizacion deve rellenar los campos correctamente");
                modelo.setRowCount(0);
                cmb_eliminar_cliente.removeAllItems();
                cmb_actualiza_cliente.removeAllItems();
            }
        }
    }
/*modelo de la tabla (solo tiene tres campos de momento)*/
    public class MiModelo extends DefaultTableModel
    {
        public boolean isCellEditable (int row, int column)
        {
           // Aquí devolvemos true o false según queramos que una celda
           // identificada por fila,columna (row,column), sea o no editable
            
//poner en true si queremos que se pueda modificar
//de momento todo a false            
//           if (column == 0)
//              return true;
           return false;
        }
        public Class getColumnClass(int columna)
        {
           if (columna == 0) return String.class; // en la bbdd es un Char(3) por eso pongo String
           if (columna == 1) return String.class;
           if (columna == 2) return String.class;
           if (columna == 3) return String.class;
           return Object.class;
        }
    }
    
     public class MiModeloCombo extends DefaultComboBoxModel
    {
        //vacio para que este por defecto
    }
/*evemtos de botones*/    
    private void btn_insertar_clienteActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        inserta();
        cargar_tabla(modelo, cmb_eliminar_cliente, cmb_actualiza_cliente);
        txt_dni.setText(null);
        txt_nombre.setText(null);
        txt_apellido.setText(null);
        txt_ciudad.setText(null);
    }
    
    private void btn_eliminar_clienteActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        eliminar();
        cargar_tabla(modelo, cmb_eliminar_cliente, cmb_actualiza_cliente);
    }
    
    private void btn_actualizar_clienteActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        actualizar();
        cargar_tabla(modelo, cmb_eliminar_cliente, cmb_actualiza_cliente);
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
