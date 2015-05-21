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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Ventana_Venta extends JDialog implements ActionListener, WindowListener{
    private ConexionDB conexion = new ConexionDB();
    
    private JPanel panel;
    private JScrollPane scl_tabla;
    private JTable tbl_tabla;
    
    private JComboBox cmb_CIFC;
    private JComboBox cmb_CODCOCHE;
    private JComboBox cmb_DNI;
    private JComboBox cmb_COLOR;
    
    private JLabel lbl_INDEX;
    
    private JButton btn_insertar;
    
    private DefaultComboBoxModel miComboModel_actualiza = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_CIFC = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_DNI = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_CODCOCHE = new DefaultComboBoxModel();
    private DefaultComboBoxModel ComboModel_COLOR = new DefaultComboBoxModel();
    
    private MiModelo modelo_tabla = new MiModelo();
    
    Ventana_Venta()
    {
        panel = (JPanel)this.getContentPane();
        panel.setLayout(null);
        
        tbl_tabla = new JTable(modelo_tabla);
        scl_tabla = new JScrollPane(tbl_tabla);
        
        this.setTitle("Tabla VENTAS"); 
            
        this.setResizable(false);
        this.setSize(800, 520);
        this.setLocation(100, 20); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        
        lbl_INDEX = new JLabel("CIFC, DNI, CODCOCHE, COLOR");
        
        cmb_CIFC = new JComboBox(ComboModel_CIFC);
        cmb_CODCOCHE = new JComboBox(ComboModel_CODCOCHE);
        cmb_DNI = new JComboBox(ComboModel_DNI);
        cmb_COLOR = new JComboBox(ComboModel_COLOR);
        btn_insertar = new JButton("VENDER");
        
        scl_tabla.setBounds(380, 50, 400, 250);
        lbl_INDEX.setBounds(30, 30, 250, 30);
        cmb_CIFC.setBounds(30, 80, 150, 30);
        cmb_DNI.setBounds(30, 130, 150, 30);
        cmb_CODCOCHE.setBounds(30, 180, 150, 30);
        cmb_COLOR.setBounds(30, 230, 150, 30);
        btn_insertar.setBounds(50, 280, 150, 30);
        
        modelo_tabla.addColumn("CIFC");
        modelo_tabla.addColumn("DNI");
        modelo_tabla.addColumn("CODCOHE");
        modelo_tabla.addColumn("COLOR");
        
        
        panel.add(scl_tabla);
        panel.add(lbl_INDEX);
        panel.add(cmb_CIFC);
        panel.add(cmb_DNI);
        panel.add(cmb_CODCOCHE);
        panel.add(cmb_CIFC);
        panel.add(cmb_COLOR);
        panel.add(btn_insertar);
        
        cargar_tabla();
        recarga();
        btn_insertar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertarActionPerformed(evt);
            }
        });
        
        cmb_CIFC.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_CIFC_actualizarActionPerformed(evt);
            }
        });
        this.setVisible(true);
    }
    
    void cargar_tabla(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`VENTAS`";
            Object [] fila = new Object[4];
//añado campos a la tabla            
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] = (String) (rs.getObject("DNI"));
                fila[2] = (String) (rs.getObject("CODCOCHE"));
                fila[3] = (String) (rs.getObject("COLOR"));
                modelo_tabla.addRow ( fila ); 
            } //fin while
//Actualizo y preparo la consulta            
            consulta="SELECT CIFC FROM `COCHES`.`CONCESIONARIOS`";
            
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[0] =  (String) (rs.getObject("CIFC"));
                cmb_CIFC.addItem(fila[0]);
            }
            
            consulta="SELECT DNI FROM `COCHES`.`CLIENTES`";
            
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[1] =  (String) (rs.getObject("DNI"));
                cmb_DNI.addItem(fila[1]);
            }
            
            consulta="SELECT CODCOCHE FROM `COCHES`.`COCHES`";
            
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[2] =  (String) (rs.getObject("CODCOCHE"));
                cmb_CODCOCHE.addItem(fila[2]);
            }
            
            consulta="SELECT DISTINCT COLOR FROM `COCHES`.`VENTAS`";
            
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                fila[3] =  (String) (rs.getObject("COLOR"));
                cmb_COLOR.addItem(fila[3]);
            }
            
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void inserta(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            
            String consulta = "SELECT CIFC, DNI, CODCOCHE, COLOR, count(CODCOCHE) "
                    + "FROM `COCHES`.`VENTAS` "
                    + "WHERE `CIFC` = '"+cmb_CIFC.getSelectedItem()+"' "
                    + "AND `DNI` = '"+cmb_DNI.getSelectedItem()+"'"
                    + "AND `CODCOCHE` = '"+cmb_CODCOCHE.getSelectedItem()+"'";
            System.out.println(consulta);
            
            Object [] fila = new Object[5];
            ResultSet rs = st.executeQuery(consulta);
            
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] =  (String) (rs.getObject("DNI"));
                fila[2] =  rs.getObject("CODCOCHE");
                fila[3] =  rs.getObject("COLOR");
                fila[4] =  rs.getObject(5);
            }
            int numero = Integer.parseInt(""+fila[4]);
            try (Statement st2 = miConexion.createStatement()) {
            if (numero == 0)
            {
//comprovar que CONCESIONARIO.CANTIDAD contiene un numero valido ( NO 0)         
                Object cantidad[] = new Object[1];
                consulta ="SELECT CANTIDAD FROM `COCHES`.`distribucion` "
                        + "WHERE `CIFC`='"+cmb_CIFC.getSelectedItem()+"' "
                        + "AND `CODCOCHE`='"+cmb_CODCOCHE.getSelectedItem()+"'";
                System.out.println(consulta);
                ResultSet rs2 = st2.executeQuery(consulta);
                while (rs2.next()) {
                    cantidad[0] = rs2.getObject("CANTIDAD");
                    
                }
                int cant = Integer.parseInt(""+cantidad[0]);
                if(cant>0)
                {
                    
                    System.out.println("antes update");
                    cant--;
                String update = "UPDATE `COCHES`.`DISTRIBUCION` SET `CANTIDAD`="+cant+" "
                        + "WHERE `CIFC`='"+cmb_CIFC.getSelectedItem()+"' "
                        + "and `CODCOCHE`='"+cmb_CODCOCHE.getSelectedItem()+"'";
                    System.out.println(update);
                st.execute(update);
                
                    System.out.println("antes insert");
                String insertar = "INSERT INTO `COCHES`.`VENTAS`(`CIFC`, `DNI`, `CODCOCHE`, `COLOR`)"
                        + " VALUES ('"
                        + cmb_CIFC.getSelectedItem()+ "', '"
                        + cmb_DNI.getSelectedItem()+ "', '"
                        + cmb_CODCOCHE.getSelectedItem()+ "', '"
                        +cmb_COLOR.getSelectedItem()+"')";
                    System.out.println(insertar);
                st.execute(insertar);
                
                modelo_tabla.setRowCount(0);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Este coche ya he sido vendido ha este cliente");
            }
            }
            
            cmb_CIFC.removeAllItems();
            cmb_DNI.removeAllItems();
            cmb_CODCOCHE.removeAllItems();
            cmb_COLOR.removeAllItems();
            modelo_tabla.setRowCount(0);
            
            st.close();
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana_Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     void recarga()
    {
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        cmb_CODCOCHE.removeAllItems();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT CODCOCHE FROM `COCHES`.`DISTRIBUCION` "
                    + "WHERE `CIFC`='"+cmb_CIFC.getSelectedItem()+"'";
            
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[1];
            while (rs.next()) {
                fila[0] =  (String) (rs.getObject("CODCOCHE"));
                cmb_CODCOCHE.addItem(fila[0]);
            } //fin while
            
            st.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(Ventana_Distribucion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public class MiModelo extends DefaultTableModel
    {
        public boolean isCellEditable (int row, int column)
        {
           return false;
        }
        public Class getColumnClass(int columna)
        {
           if (columna == 0) return String.class; 
           if (columna == 1) return String.class;
           if (columna == 2) return String.class;
           if (columna == 3) return String.class;
           return Object.class;
        }
    }
    
    private void btn_insertarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        inserta();
        cargar_tabla();
        cmb_CIFC.setSelectedIndex(0);
        cmb_DNI.setSelectedIndex(0);
        cmb_CODCOCHE.setSelectedIndex(0);
        cmb_COLOR.setSelectedIndex(0);
    }
    
    private void cmb_CIFC_actualizarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        recarga();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
