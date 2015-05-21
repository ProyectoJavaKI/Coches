package Interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;


public class Ventana_DISTRIBUCION extends JDialog implements ActionListener, WindowListener{
    private ConexionDB conexion = new ConexionDB();
    
/*Jpanel que contiene a todo*/
    private JPanel panel;
    private JScrollPane scl_tabla;
    private JTable tbl_tabla;
    private JButton btn_insertar_DISTRIBUCION;
    private JButton btn_actualizar_DISTRIBUCION;
    private JLabel lbl_CIFC;
    private JLabel lbl_CODCOCHE;
    private JLabel lbl_CANTIDAD;
    private JLabel lbl_actualiza_CIFC;
    private JLabel lbl_actualiza_CODCOCHE;
    private JLabel lbl_insert_DISTRIBUCION;
    private JLabel lbl_actual_DISTRIBUCION;
    private JTextField txt_CANTIDAD;
    private JLabel lbl_descrip_tabla;
    private JComboBox cmb_CIFC;
    private JComboBox cmb_CODCOCHE;
    private JComboBox cmb_recarga_CIFC;
    private JComboBox cmb_recarga_CODCOCHE;
    private MiModelo modelo_tabla = new MiModelo();
    private DefaultComboBoxModel miComboModel_elimina = new DefaultComboBoxModel();
    private DefaultComboBoxModel miComboModel_actualiza = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_nombre = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_modelo_DISTRIBUCION = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_nombre_actualizarr = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_modelo_DISTRIBUCION_actualizar = new DefaultComboBoxModel();
    
    Ventana_DISTRIBUCION(){
        panel = (JPanel)this.getContentPane();
        panel.setLayout(null);
        
        /*dar valores a las caracteristicas del PANEL*/
        this.setTitle("Tabla COCHES"); //titulo de la aplicacion
        
/*cuando este todo listo quitar el comentario de resizable*/        
//        this.setResizable(false);// no permite redimencionar
        this.setSize(800, 520); //dimencion X, Y
        this.setLocation(100, 20); //posicion X, Y
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Cerrar programa al pulsar la X
        setModal(true);
        
        /*creamos los componentes*/
        tbl_tabla = new JTable(modelo_tabla);
        scl_tabla = new JScrollPane(tbl_tabla);
        btn_insertar_DISTRIBUCION = new JButton("insertar DISTRIBUCION");
        btn_actualizar_DISTRIBUCION = new JButton("Actualizar");
        lbl_descrip_tabla = new JLabel("Informacion de los DISTRIBUCION");
        lbl_CIFC = new JLabel("CIFC");
        lbl_CODCOCHE = new JLabel("CODCOCHE");
        lbl_CANTIDAD = new JLabel("CANTIDAD");
        lbl_actualiza_CIFC = new JLabel("CIFC");
        lbl_actualiza_CODCOCHE = new JLabel("CODCOCHE");
        lbl_insert_DISTRIBUCION = new JLabel("Crea una Nueva Distribucion");
        lbl_actual_DISTRIBUCION = new JLabel("Actualozar datos de un DISTRIBUCION");
        txt_CANTIDAD = new JTextField();
        cmb_CIFC = new JComboBox(ComboModel_nombre);
        cmb_CODCOCHE = new JComboBox(ComboModel_modelo_DISTRIBUCION);
        cmb_recarga_CIFC = new JComboBox(ComboModel_nombre_actualizarr);
        cmb_recarga_CODCOCHE = new JComboBox(ComboModel_modelo_DISTRIBUCION_actualizar);
        
        /*damos posicion X,Y, y dimencion X,Y*/
        scl_tabla.setBounds(380, 50, 400, 250);
        btn_insertar_DISTRIBUCION.setBounds(50, 180, 150, 30);
        btn_actualizar_DISTRIBUCION.setBounds(30, 420, 200, 30);
        lbl_CIFC.setBounds(30, 100, 150, 30);
        lbl_CODCOCHE.setBounds(30, 140, 150, 30);
        lbl_CANTIDAD.setBounds(30, 60, 150, 30);
        lbl_descrip_tabla.setBounds(400, 20, 150, 30);
        lbl_insert_DISTRIBUCION.setBounds(40, 30, 150, 30);
        lbl_actual_DISTRIBUCION.setBounds(30, 260, 200, 30);
        lbl_actualiza_CIFC.setBounds(30, 330, 150, 30);
        lbl_actualiza_CODCOCHE.setBounds(30, 370, 150, 30);
        txt_CANTIDAD.setBounds(200, 60, 150, 30);
        cmb_CODCOCHE.setBounds(200, 140, 150, 30);
        cmb_recarga_CIFC.setBounds(200, 330, 150, 30);
        cmb_recarga_CODCOCHE.setBounds(200, 370, 150, 30);
        cmb_CIFC.setBounds(200, 100, 150, 30);
        
        /*añadimos datos a la tabla*/
        modelo_tabla.addColumn("CIFC");
        modelo_tabla.addColumn("CODCOCHE");
        modelo_tabla.addColumn("CANTIDAD");
/*Este apartado se rellena con la informacion de la bbdd (con una sentencia select*/
        cargar_tabla();
        /*añadiendo los elementos al panel*/
        panel.add(scl_tabla);
        panel.add(btn_insertar_DISTRIBUCION);
        panel.add(btn_actualizar_DISTRIBUCION);
        panel.add(lbl_descrip_tabla);
        panel.add(lbl_CIFC);
        panel.add(lbl_CODCOCHE);
        panel.add(lbl_CANTIDAD);
        panel.add(lbl_insert_DISTRIBUCION);
        panel.add(lbl_actual_DISTRIBUCION);
        panel.add(lbl_actualiza_CIFC);
        panel.add(lbl_actualiza_CODCOCHE);
        panel.add(txt_CANTIDAD);
        panel.add(cmb_CIFC);
        panel.add(cmb_CODCOCHE);
        panel.add(cmb_recarga_CIFC);
        panel.add(cmb_recarga_CODCOCHE);
        
/*añadido del action listener*/        
        btn_insertar_DISTRIBUCION.addActionListener(new java.awt.event.ActionListener() {
       
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertar_DISTRIBUCIONActionPerformed(evt);
            }
    });
        btn_actualizar_DISTRIBUCION.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_DISTRIBUCIONActionPerformed(evt);
            }
    });
        
        cmb_recarga_CIFC.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_nombre_DISTRIBUCION_actualizarActionPerformed(evt);
            }
    });
        this.setVisible(true);//el set visible del panel_maestro debe de ser el ultimo siempre
    }

/*carga la tabla con la insfomacion de la bbdd*/    
    void cargar_tabla(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`DISTRIBUCION`";
            
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[3];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] =  (String) (rs.getObject("CODCOCHE"));
                fila[2] =  rs.getObject("CANTIDAD");
                modelo_tabla.addRow ( fila ); // Añade una fila al final de la tabla
            } //fin while
            consulta="SELECT CIFC FROM `COCHES`.`CONCESIONARIOS`";
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[0] =  (String) (rs.getObject("CIFC"));
                cmb_CIFC.addItem(fila[0]);
                cmb_recarga_CIFC.addItem(fila[0]);
            }
            
            consulta="SELECT CODCOCHE FROM `COCHES`.`COCHES`";
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[1] =  (String) (rs.getObject("CODCOCHE"));
                cmb_CODCOCHE.addItem(fila[1]);
            }
            consulta="SELECT DISTINCT CODCOCHE FROM `COCHES`.`DISTRIBUCION` WHERE CIFC="+cmb_recarga_CIFC.getSelectedItem();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[1] =  (String) (rs.getObject("CODCOCHE"));
                cmb_recarga_CODCOCHE.addItem(fila[1]);
            }
            
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_DISTRIBUCION.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void recarga()
    {
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        cmb_recarga_CODCOCHE.removeAllItems();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`DISTRIBUCION`";
            
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[3];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] =  (String) (rs.getObject("CODCOCHE"));
                fila[2] =  rs.getObject("CANTIDAD");
            } //fin while
            
            consulta="SELECT DISTINCT CODCOCHE FROM `COCHES`.`DISTRIBUCION` WHERE CIFC="+cmb_recarga_CIFC.getSelectedItem();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[2] =  (String) (rs.getObject("CODCOCHE"));
                cmb_recarga_CODCOCHE.addItem(fila[2]);
            }
            
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_DISTRIBUCION.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/*inserta correctamente*/
    void inserta(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        if(!txt_CANTIDAD.getText().isEmpty())
        {
            try (Statement st = miConexion.createStatement()) {

                String consulta = "SELECT CIFC, CODCOCHE, CANTIDAD, count(CANTIDAD) from `COCHES`.`DISTRIBUCION` "
                        + "WHERE `CIFC` = '"+cmb_CIFC.getSelectedItem()+"' "
                        + "AND `CODCOCHE` = '"+cmb_CODCOCHE.getSelectedItem()+"'";
                System.out.println(consulta);

                Object [] fila = new Object[4];

                ResultSet rs = st.executeQuery(consulta);

                while (rs.next()) {
                    fila[0] = (String) (rs.getObject("CIFC"));
                    fila[1] =  (String) (rs.getObject("CODCOCHE"));
                    fila[2] =  rs.getObject("CANTIDAD");
                    fila[3] =  rs.getObject(4);
                }
                int numero = Integer.parseInt(fila[3]+"");

                rs.beforeFirst();

                if (numero == 0)
                {
                    String insertar = "INSERT INTO `COCHES`.`DISTRIBUCION`(`CIFC`, `CODCOCHE`,`CANTIDAD`)"
                                + " VALUES ('"
                                + cmb_CIFC.getSelectedItem()+ "', '"
                                + cmb_CODCOCHE.getSelectedItem()+ "', '"
                                +txt_CANTIDAD.getText()+"')";
                    st.execute(insertar);
                    modelo_tabla.setRowCount(0);
                }
                else
                {
                    int num = Integer.parseInt(txt_CANTIDAD.getText());
                    int cantidad = (int) fila[2];
                    int suma = num + cantidad;

                    String update = "UPDATE `DISTRIBUCION` SET `CANTIDAD`="+suma+" "
                            + "WHERE `CIFC`='"+cmb_CIFC.getSelectedItem()+"' "
                            + "and `CODCOCHE`='"+cmb_CODCOCHE.getSelectedItem()+"'";
                    System.out.println(update);
                    st.execute(update);
                    modelo_tabla.setRowCount(0);
                }


                cmb_CIFC.removeAllItems();
                cmb_CODCOCHE.removeAllItems();
                modelo_tabla.setRowCount(0);

                st.close();

            } 
            catch (SQLException ex) {
                Logger.getLogger(Ventana_DISTRIBUCION.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error en el formato del numero, solo numeros enteros");
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Introdusca un numero valido");
        }
    }
    

    void actualizar(){
        Object[] opciones = {"Sí, actualizar datos de este DISTRIBUCION",  "No, no actualizar datos de este DISTRIBUCION"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a actualizar la informacion de un cohce ¿es ta seguro?: ",
        "Confirmar actualizacion del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "UPDATE `COCHES`.`DISTRIBUCION` SET `NOMBRE`='"+cmb_recarga_CIFC.getSelectedItem()+"',`MODELO`='"+cmb_recarga_CODCOCHE.getSelectedItem()+"' WHERE `CODCOCHE`=''";
                st.executeUpdate(insertar);
                modelo_tabla.setRowCount(0);
                st.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Ventana_DISTRIBUCION.class.getName()).log(Level.SEVERE, null, ex);
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
           if (columna == 2) return int.class;
           return Object.class;
        }
    }
    
/*evemtos de botones*/    
    private void btn_insertar_DISTRIBUCIONActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        inserta();
        cargar_tabla();
        txt_CANTIDAD.setText(null);
        cmb_CIFC.setSelectedIndex(0);
        cmb_CODCOCHE.setSelectedIndex(0);
    }
    
    
    private void btn_actualizar_DISTRIBUCIONActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        actualizar();
        cargar_tabla();
    }
    
    private void cmb_nombre_DISTRIBUCION_actualizarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        recarga();
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

