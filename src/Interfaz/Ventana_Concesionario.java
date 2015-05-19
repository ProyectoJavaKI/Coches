//package Interfaz;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
//import javax.swing.table.DefaultTableModel;
//
//public class Ventana_Concesionario extends JDialog implements ActionListener, WindowListener {
//    private ConexionDB conexion = new ConexionDB();
//    
//   /*Jpanel que contiene a todo*/
//    private JPanel panel;
//    private JScrollPane scl_tabla;
//    private JTable tbl_tabla;
//    private JButton btn_insertar_Concesionario;
//    private JButton btn_eliminar_Concesionario;
//    private JButton btn_actualizar_Concesionario;
//    private JLabel lbl_doc;
//    private JLabel lbl_nuevo_nombre;
//    private JLabel lbl_nuevo_modelo;
//    private JLabel lbl_actualiza_nombre;
//    private JLabel lbl_actualiza_modelo;
//    private JLabel lbl_insert_Concesionario;
//    private JLabel lbl_elim_Concesionario;
//    private JLabel lbl_actual_Concesionario;
//    private JTextField txt_doc;
//    private JLabel lbl_descrip_tabla;
//    private JComboBox cmb_eliminar_Concesionario;
//    private JComboBox cmb_actualiza_Concesionario;
//    private JComboBox cmb_nombre_Concesionario;
//    private JComboBox cmb_modelo_Concesionario;
//    private JComboBox cmb_nombre_Concesionario_actualizar;
//    private JComboBox cmb_modelo_Concesionario_actualizat;
//    private MiModelo modelo = new MiModelo();
//    private DefaultComboBoxModel miComboModel_elimina = new DefaultComboBoxModel();
//    private DefaultComboBoxModel miComboModel_actualiza = new DefaultComboBoxModel();
//    private DefaultComboBoxModel ComboModel_nombre = new DefaultComboBoxModel();
//    private DefaultComboBoxModel ComboModel_modelo_Concesionario = new DefaultComboBoxModel();
//    private DefaultComboBoxModel ComboModel_nombre_actualizarr = new DefaultComboBoxModel();
//    private DefaultComboBoxModel ComboModel_modelo_Concesionario_actualizar = new DefaultComboBoxModel();
//    
//    
//    Ventana_Concesionario(){
//        panel = (JPanel)this.getContentPane();
//        panel.setLayout(null);
//        
//        /*dar valores a las caracteristicas del PANEL*/
//        this.setTitle("Tabla Concesionario"); //titulo de la aplicacion
//        
///*cuando este todo listo quitar el comentario de resizable*/        
////        this.setResizable(false);// no permite redimencionar
//        this.setSize(800, 520); //dimencion X, Y
//        this.setLocation(100, 20); //posicion X, Y
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Cerrar programa al pulsar la X
//        setModal(true);
//        
//        /*creamos los componentes*/
//        tbl_tabla = new JTable(modelo);
//        scl_tabla = new JScrollPane(tbl_tabla);
//        btn_insertar_Concesionario = new JButton("insertar Concesionario");
//        btn_eliminar_Concesionario = new JButton("eliminar Concesionario");
//        btn_actualizar_Concesionario = new JButton("Actualizar detos de Concesionarios");
//        lbl_descrip_tabla = new JLabel("Informacion de los Concesionarios");
//        lbl_doc = new JLabel("CIFC");
//        lbl_nuevo_nombre = new JLabel("Nombre");
//        lbl_nuevo_modelo = new JLabel("Ciudad");
//        lbl_actualiza_nombre = new JLabel("actualizar nombre");
//        lbl_actualiza_modelo = new JLabel("actualizar Ciudad");
//        lbl_insert_Concesionario = new JLabel("Crear Nuevo Concesionario");
//        lbl_elim_Concesionario = new JLabel("Eliminar Concesionario");
//        lbl_actual_Concesionario = new JLabel("Actualozar detos de un Concesionario");
//        txt_doc = new JTextField();
//        cmb_eliminar_Concesionario = new JComboBox(miComboModel_elimina);
//        cmb_actualiza_Concesionario = new JComboBox(miComboModel_actualiza);
//        cmb_nombre_Concesionario = new JComboBox(ComboModel_nombre);
//        cmb_modelo_Concesionario = new JComboBox(ComboModel_modelo_Concesionario);
//        cmb_nombre_Concesionario_actualizar = new JComboBox(ComboModel_nombre_actualizarr);
//        cmb_modelo_Concesionario_actualizat = new JComboBox(ComboModel_modelo_Concesionario_actualizar);
//        
//        /*damos posicion X,Y, y dimencion X,Y*/
//        scl_tabla.setBounds(380, 50, 400, 250);
//        btn_insertar_Concesionario.setBounds(50, 180, 150, 30);
//        btn_eliminar_Concesionario.setBounds(460, 390, 150, 30);
//        btn_actualizar_Concesionario.setBounds(30, 420, 200, 30);
//        lbl_doc.setBounds(30, 60, 150, 30);
//        lbl_nuevo_nombre.setBounds(30, 100, 150, 30);
//        lbl_nuevo_modelo.setBounds(30, 140, 150, 30);
//        lbl_descrip_tabla.setBounds(400, 20, 150, 30);
//        lbl_insert_Concesionario.setBounds(40, 30, 150, 30);
//        lbl_elim_Concesionario.setBounds(430, 340, 150, 30);
//        lbl_actual_Concesionario.setBounds(30, 260, 200, 30);
//        lbl_actualiza_nombre.setBounds(30, 330, 150, 30);
//        lbl_actualiza_modelo.setBounds(30, 370, 150, 30);
//        txt_doc.setBounds(200, 60, 150, 30);
//        cmb_modelo_Concesionario.setBounds(200, 140, 150, 30);
//        cmb_nombre_Concesionario_actualizar.setBounds(200, 330, 150, 30);
//        cmb_modelo_Concesionario_actualizat.setBounds(200, 370, 150, 30);
//        cmb_nombre_Concesionario.setBounds(200, 100, 150, 30);
//        cmb_eliminar_Concesionario.setBounds(550, 340, 90, 30);
//        cmb_actualiza_Concesionario.setBounds(30, 290, 90, 30);
//        
//        /*añadimos datos a la tabla*/
//        modelo.addColumn("CIFC");
//        modelo.addColumn("NOMBRE");
//        modelo.addColumn("ICUDAD");
///*Este apartado se rellena con la informacion de la bbdd (con una sentencia select*/
//        cargar_tabla();
//        /*añadiendo los elementos al panel*/
//        panel.add(scl_tabla);
//        panel.add(btn_insertar_Concesionario);
//        panel.add(btn_eliminar_Concesionario);
//        panel.add(btn_actualizar_Concesionario);
//        panel.add(lbl_descrip_tabla);
//        panel.add(lbl_doc);
//        panel.add(lbl_nuevo_nombre);
//        panel.add(lbl_nuevo_modelo);
//        panel.add(lbl_insert_Concesionario);
//        panel.add(lbl_elim_Concesionario);
//        panel.add(lbl_actual_Concesionario);
//        panel.add(lbl_actualiza_nombre);
//        panel.add(lbl_actualiza_modelo);
//        panel.add(txt_doc);
//        panel.add(cmb_nombre_Concesionario);
//        panel.add(cmb_modelo_Concesionario);
//        panel.add(cmb_nombre_Concesionario_actualizar);
//        panel.add(cmb_modelo_Concesionario_actualizat);
//        panel.add(cmb_eliminar_Concesionario);
//        panel.add(cmb_actualiza_Concesionario);
//        
///*añadido del action listener*/        
//        btn_insertar_Concesionario.addActionListener(new java.awt.event.ActionListener() {
//       
//        public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btn_insertar_ConcesionarioActionPerformed(evt);
//            }
//    });
//        btn_eliminar_Concesionario.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btn_eliminar_ConcesionarioActionPerformed(evt);
//            }
//    });
//        btn_actualizar_Concesionario.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btn_actualizar_ConcesionarioActionPerformed(evt);
//            }
//    });
//        this.setVisible(true);//el set visible del panel_maestro debe de ser el ultimo siempre
//    }
//
///*carga la tabla con la insfomacion de la bbdd*/    
//    void cargar_tabla(){
//        
//        Connection miConexion = (Connection) conexion.ConectarMysql();
//        
//        try (Statement st = miConexion.createStatement()) {
//            String consulta = "SELECT * FROM `coches`.`Concesionarios`";
//            
//            ResultSet rs = st.executeQuery(consulta);
//            Object [] fila = new Object[3];
//            while (rs.next()) {
//                fila[0] = (String) (rs.getObject("CIFC"));
//                fila[1] =  (String) (rs.getObject("NOMBRE"));
//                fila[2] =  (String) (rs.getObject("CIUDAD"));
//                modelo.addRow ( fila ); // Añade una fila al final de la tabla
//                cmb_eliminar_Concesionario.addItem(fila[0]); // Añade una fila al final combo elimina
//                cmb_actualiza_Concesionario.addItem(fila[0]); // Añade una fila al final del combo actualiza
//            } //fin while
//            consulta="SELECT DISTINCT NOMBRE FROM `coches`.`Concesionarios`";
//            rs = st.executeQuery(consulta);
//            while (rs.next()) {
//                fila[1] =  (String) (rs.getObject("NOMBRE"));
//                cmb_nombre_Concesionario.addItem(fila[1]);
//                cmb_nombre_Concesionario_actualizar.addItem(fila[1]);
//            }
//            
//            consulta="SELECT DISTINCT CIUDAD FROM `coches`.`Concesionarios`";
//            rs = st.executeQuery(consulta);
//            while (rs.next()) {
//                fila[2] =  (String) (rs.getObject("CIUDAD"));
//                cmb_modelo_Concesionario.addItem(fila[2]);
//                cmb_modelo_Concesionario_actualizat.addItem(fila[2]);
//            }
//            
//            st.close();
//        } 
//        catch (SQLException ex) {
//            Logger.getLogger(Ventana_Concesionario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//   
//
///*inserta correctamente*/
//    void inserta(){
//        
//        Connection miConexion = (Connection) conexion.ConectarMysql();
//        
//        try (Statement st = miConexion.createStatement()) {
//            String consulta = "SELECT * FROM `coches`.`Concesionarios`";
//            ResultSet rs = st.executeQuery(consulta);
//            Object [] fila = new Object[1];
//            while (rs.next()) {
//                fila[0] = rs.getObject("CIFC");
//            }
//            int ultimo_num = Integer.parseInt((String) fila[0]);
//            
//            ultimo_num++;
//            String insertar = "INSERT INTO `coches`.`Concesionarios`(`CIFC`, `NOMBRE`,`CIUDAD`)"
//                        + " VALUES ('"
//                        + "00"+ultimo_num + "', '"
//                        + cmb_nombre_Concesionario.getSelectedItem()+ "', '"
//                        + cmb_modelo_Concesionario.getSelectedItem()+ "')";
//            st.execute(insertar);
//            modelo.setRowCount(0);
//            cmb_eliminar_Concesionario.removeAllItems();
//            cmb_actualiza_Concesionario.removeAllItems();
//            st.close();
//        } 
//        catch (SQLException ex) {
//            Logger.getLogger(Ventana_Concesionario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    void eliminar(){
//        Object[] opciones = {"Sí, Eliminar este Concesionario",  "No, no eliminar"};
//        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a eliminar un cohce ¿es ta seguro?: ",
//        "Confirmar eliminación del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
//        if(respuestaUsuario == 0)
//        {
//            Connection miConexion = (Connection) conexion.ConectarMysql();
//            String opt = (String) cmb_eliminar_Concesionario.getSelectedItem();
//            try (Statement st = miConexion.createStatement()) {
//
//                String insertar = "DELETE FROM `coches`.`Concesionarios` WHERE `CIFC` = '"+opt+"'";
//                st.execute(insertar);
//                modelo.setRowCount(0);
//                cmb_eliminar_Concesionario.removeAllItems();
//                cmb_actualiza_Concesionario.removeAllItems();
//                st.close();
//            } 
//            catch (SQLException ex) {
//                Logger.getLogger(Ventana_Concesionario.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    void actualizar(){
//        Object[] opciones = {"Sí, actualizar datos de este Concesionario",  "No, no actualizar datos de este Concesionario"};
//        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a actualizar la informacion de un cohce ¿es ta seguro?: ",
//        "Confirmar actualizacion del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
//        if(respuestaUsuario == 0)
//        {
//            Connection miConexion = (Connection) conexion.ConectarMysql();
//            try (Statement st = miConexion.createStatement()) {
//
//                String insertar = "UPDATE `coches`.`Concesionarios` SET `NOMBRE`='"+cmb_nombre_Concesionario_actualizar.getSelectedItem()+"',`CIUDAD`='"+cmb_modelo_Concesionario_actualizat.getSelectedItem()+"' WHERE `CIFC`='"+cmb_actualiza_Concesionario.getSelectedItem()+"'";
//                st.executeUpdate(insertar);
//                modelo.setRowCount(0);
//                cmb_actualiza_Concesionario.removeAllItems();
//                cmb_eliminar_Concesionario.removeAllItems();
//                st.close();
//            } 
//            catch (SQLException ex) {
//                Logger.getLogger(Ventana_Concesionario.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//    
///*modelo de la tabla (solo tiene tres campos de momento)*/
//    public class MiModelo extends DefaultTableModel
//    {
//        public boolean isCellEditable (int row, int column)
//        {
//           // Aquí devolvemos true o false según queramos que una celda
//           // identificada por fila,columna (row,column), sea o no editable
//            
////poner en true si queremos que se pueda modificar
////de momento todo a false            
////           if (column == 0)
////              return true;
//           return false;
//        }
//        public Class getColumnClass(int columna)
//        {
//           if (columna == 0) return String.class; // en la bbdd es un Char(3) por eso pongo String
//           if (columna == 1) return String.class;
//           if (columna == 2) return String.class;
//           return Object.class;
//        }
//    }
//    
///*evemtos de botones*/    
//    private void btn_insertar_ConcesionarioActionPerformed(java.awt.event.ActionEvent evt) {                                             
//        // TODO add your handling code here:
//        inserta();
//        cargar_tabla();
//        txt_doc.setText(null);
//        cmb_nombre_Concesionario.setSelectedIndex(0);
//        cmb_modelo_Concesionario.setSelectedIndex(0);
//    }
//    
//    private void btn_eliminar_ConcesionarioActionPerformed(java.awt.event.ActionEvent evt) {                                             
//        // TODO add your handling code here:
//        eliminar();
//        cargar_tabla();
//    }
//    
//    private void btn_actualizar_ConcesionarioActionPerformed(java.awt.event.ActionEvent evt) {                                             
//        // TODO add your handling code here:
//        actualizar();
//        cargar_tabla();
//    }
//    
//    
//    @Override
//    public void actionPerformed(ActionEvent ae) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowOpened(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowClosing(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowClosed(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowIconified(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowDeiconified(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowActivated(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void windowDeactivated(WindowEvent we) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//}
