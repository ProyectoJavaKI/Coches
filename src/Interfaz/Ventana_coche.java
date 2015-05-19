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

import javax.swing.table.DefaultTableModel;

public class Ventana_coche  extends JDialog implements ActionListener, WindowListener{
    
    private ConexionDB conexion = new ConexionDB();
    
/*Jpanel que contiene a todo*/
    private JPanel panel;
    private JScrollPane scl_tabla;
    private JTable tbl_tabla;
    private JButton btn_insertar_coche;
    private JButton btn_eliminar_coche;
    private JButton btn_actualizar_coche;
    private JLabel lbl_doc;
    private JLabel lbl_nuevo_nombre;
    private JLabel lbl_nuevo_modelo;
    private JLabel lbl_actualiza_nombre;
    private JLabel lbl_actualiza_modelo;
    private JLabel lbl_insert_coche;
    private JLabel lbl_elim_coche;
    private JLabel lbl_actual_coche;
    
    private JLabel lbl_descrip_tabla;
    private JComboBox cmb_eliminar_coche;
    private JComboBox cmb_actualiza_coche;
    private JComboBox cmb_nombre_coche;
    private JComboBox cmb_modelo_coche;
    private JComboBox cmb_nombre_coche_actualizar;
    private JComboBox cmb_modelo_coche_actualizat;
    private MiModelo modelo_tabla = new MiModelo();
    private DefaultComboBoxModel miComboModel_elimina = new DefaultComboBoxModel();
    private DefaultComboBoxModel miComboModel_actualiza = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_nombre = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_modelo_coche = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_nombre_actualizarr = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_modelo_coche_actualizar = new DefaultComboBoxModel();
    
    Ventana_coche(){
        panel = (JPanel)this.getContentPane();
        panel.setLayout(null);
        
        /*dar valores a las caracteristicas del PANEL*/
        this.setTitle("Tabla Coches"); //titulo de la aplicacion
        
/*cuando este todo listo quitar el comentario de resizable*/        
//        this.setResizable(false);// no permite redimencionar
        this.setSize(800, 520); //dimencion X, Y
        this.setLocation(100, 20); //posicion X, Y
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Cerrar programa al pulsar la X
        setModal(true);
        
        /*creamos los componentes*/
        tbl_tabla = new JTable(modelo_tabla);
        scl_tabla = new JScrollPane(tbl_tabla);
        btn_insertar_coche = new JButton("insertar coche");
        btn_eliminar_coche = new JButton("eliminar coche");
        btn_actualizar_coche = new JButton("Actualizar detos de coches");
        lbl_descrip_tabla = new JLabel("Informacion de los coches");
        lbl_doc = new JLabel("Codigo del Coche");
        lbl_nuevo_nombre = new JLabel("Nombre del Coche");
        lbl_nuevo_modelo = new JLabel("Modelo del Coche");
        lbl_actualiza_nombre = new JLabel("actualizar nombre");
        lbl_actualiza_modelo = new JLabel("actualizar modelo");
        lbl_insert_coche = new JLabel("Crear Nuevo Coche");
        lbl_elim_coche = new JLabel("Eliminar Coche");
        lbl_actual_coche = new JLabel("Actualozar detos de un coche");
        
        cmb_eliminar_coche = new JComboBox(miComboModel_elimina);
        cmb_actualiza_coche = new JComboBox(miComboModel_actualiza);
        cmb_nombre_coche = new JComboBox(ComboModel_nombre);
        cmb_modelo_coche = new JComboBox(ComboModel_modelo_coche);
        cmb_nombre_coche_actualizar = new JComboBox(ComboModel_nombre_actualizarr);
        cmb_modelo_coche_actualizat = new JComboBox(ComboModel_modelo_coche_actualizar);
        
        /*damos posicion X,Y, y dimencion X,Y*/
        scl_tabla.setBounds(380, 50, 400, 250);
        btn_insertar_coche.setBounds(50, 180, 150, 30);
        btn_eliminar_coche.setBounds(460, 390, 150, 30);
        btn_actualizar_coche.setBounds(30, 420, 200, 30);
        lbl_doc.setBounds(30, 60, 150, 30);
        lbl_nuevo_nombre.setBounds(30, 100, 150, 30);
        lbl_nuevo_modelo.setBounds(30, 140, 150, 30);
        lbl_descrip_tabla.setBounds(400, 20, 150, 30);
        lbl_insert_coche.setBounds(40, 30, 150, 30);
        lbl_elim_coche.setBounds(430, 340, 150, 30);
        lbl_actual_coche.setBounds(30, 260, 200, 30);
        lbl_actualiza_nombre.setBounds(30, 330, 150, 30);
        lbl_actualiza_modelo.setBounds(30, 370, 150, 30);
       
        cmb_modelo_coche.setBounds(200, 140, 150, 30);
        cmb_nombre_coche_actualizar.setBounds(200, 330, 150, 30);
        cmb_modelo_coche_actualizat.setBounds(200, 370, 150, 30);
        cmb_nombre_coche.setBounds(200, 100, 150, 30);
        cmb_eliminar_coche.setBounds(550, 340, 90, 30);
        cmb_actualiza_coche.setBounds(30, 290, 90, 30);
        
        /*añadimos datos a la tabla*/
        modelo_tabla.addColumn("codigo coche");
        modelo_tabla.addColumn("nombre");
        modelo_tabla.addColumn("modelo");
/*Este apartado se rellena con la informacion de la bbdd (con una sentencia select*/
        cargar_tabla();
        /*añadiendo los elementos al panel*/
        panel.add(scl_tabla);
        panel.add(btn_insertar_coche);
        panel.add(btn_eliminar_coche);
        panel.add(btn_actualizar_coche);
        panel.add(lbl_descrip_tabla);
        panel.add(lbl_doc);
        panel.add(lbl_nuevo_nombre);
        panel.add(lbl_nuevo_modelo);
        panel.add(lbl_insert_coche);
        panel.add(lbl_elim_coche);
        panel.add(lbl_actual_coche);
        panel.add(lbl_actualiza_nombre);
        panel.add(lbl_actualiza_modelo);
        
        panel.add(cmb_nombre_coche);
        panel.add(cmb_modelo_coche);
        panel.add(cmb_nombre_coche_actualizar);
        panel.add(cmb_modelo_coche_actualizat);
        panel.add(cmb_eliminar_coche);
        panel.add(cmb_actualiza_coche);
        
/*añadido del action listener*/        
        btn_insertar_coche.addActionListener(new java.awt.event.ActionListener() {
       
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertar_cocheActionPerformed(evt);
            }
    });
        btn_eliminar_coche.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_cocheActionPerformed(evt);
            }
    });
        btn_actualizar_coche.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_cocheActionPerformed(evt);
            }
    });
        this.setVisible(true);//el set visible del panel_maestro debe de ser el ultimo siempre
    }

/*carga la tabla con la insfomacion de la bbdd*/    
    void cargar_tabla(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `coches`.`coches`";
            
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[3];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CODCOCHE"));
                fila[1] =  (String) (rs.getObject("NOMBRE"));
                fila[2] =  (String) (rs.getObject("MODELO"));
                modelo_tabla.addRow ( fila ); // Añade una fila al final de la tabla
                cmb_eliminar_coche.addItem(fila[0]); // Añade una fila al final combo elimina
                cmb_actualiza_coche.addItem(fila[0]); // Añade una fila al final del combo actualiza
            } //fin while
            consulta="SELECT DISTINCT NOMBRE FROM `coches`.`coches`";
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[1] =  (String) (rs.getObject("NOMBRE"));
                cmb_nombre_coche.addItem(fila[1]);
                cmb_nombre_coche_actualizar.addItem(fila[1]);
            }
            
            consulta="SELECT DISTINCT MODELO FROM `coches`.`coches`";
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[2] =  (String) (rs.getObject("MODELO"));
                cmb_modelo_coche.addItem(fila[2]);
                cmb_modelo_coche_actualizat.addItem(fila[2]);
            }
            
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

/*inserta correctamente*/
    void inserta(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `coches`.`coches`";
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[1];
            while (rs.next()) {
                fila[0] = rs.getObject("CODCOCHE");
            }
            int ultimo_num = Integer.parseInt((String) fila[0]);
            
            ultimo_num++;
            String insertar = "INSERT INTO `coches`.`coches`(`CODCOCHE`, `NOMBRE`,`MODELO`)"
                        + " VALUES ('"
                        + "0"+ultimo_num + "', '"
                        + cmb_nombre_coche.getSelectedItem()+ "', '"
                        + cmb_modelo_coche.getSelectedItem()+ "')";
            st.execute(insertar);
            modelo_tabla.setRowCount(0);
            cmb_eliminar_coche.removeAllItems();
            cmb_actualiza_coche.removeAllItems();
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void eliminar(){
        Object[] opciones = {"Sí, Eliminar este coche",  "No, no eliminar"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a eliminar un cohce ¿es ta seguro?: ",
        "Confirmar eliminación del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            String opt = (String) cmb_eliminar_coche.getSelectedItem();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "DELETE FROM `coches`.`coches` WHERE `CODCOCHE` = '"+opt+"'";
                st.execute(insertar);
                modelo_tabla.setRowCount(0);
                cmb_eliminar_coche.removeAllItems();
                cmb_actualiza_coche.removeAllItems();
                st.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Ventana_coche.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void actualizar(){
        Object[] opciones = {"Sí, actualizar datos de este coche",  "No, no actualizar datos de este coche"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a actualizar la informacion de un cohce ¿es ta seguro?: ",
        "Confirmar actualizacion del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "UPDATE `coches`.`coches` SET `NOMBRE`='"+cmb_nombre_coche_actualizar.getSelectedItem()+"',`MODELO`='"+cmb_modelo_coche_actualizat.getSelectedItem()+"' WHERE `CODCOCHE`='"+cmb_actualiza_coche.getSelectedItem()+"'";
                st.executeUpdate(insertar);
                modelo_tabla.setRowCount(0);
                cmb_actualiza_coche.removeAllItems();
                cmb_eliminar_coche.removeAllItems();
                st.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Ventana_coche.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
/*modelo de la tabla (solo tiene tres campos de momento)*/
    public class MiModelo extends DefaultTableModel
    {
        public boolean isCellEditable (int row, int column)
        {
          
           return false;
        }
        public Class getColumnClass(int columna)
        {
           if (columna == 0) return String.class; // en la bbdd es un Char(3) por eso pongo String
           if (columna == 1) return String.class;
           if (columna == 2) return String.class;
           return Object.class;
        }
    }
    
/*evemtos de botones*/    
    private void btn_insertar_cocheActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        inserta();
        cargar_tabla();
        
        cmb_nombre_coche.setSelectedIndex(0);
        cmb_modelo_coche.setSelectedIndex(0);
    }
    
    private void btn_eliminar_cocheActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        eliminar();
        cargar_tabla();
    }
    
    private void btn_actualizar_cocheActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        actualizar();
        cargar_tabla();
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
