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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Ventana  extends JFrame implements ActionListener, WindowListener{
    private ConexionDB conexion = new ConexionDB();
    
/*Jpanel que contiene a todo*/
    private JPanel panel;
    private JScrollPane scl_tabla;
    private JTable tbl_tabla;
    private JButton btn_insertar_coche;
    private JTextField txt_doc;
    private JTextField txt_nombre;
    private JTextField txt_modelo;
    private MiModelo modelo = new MiModelo();
    
    Ventana(){
        panel = (JPanel)this.getContentPane();
        panel.setLayout(null);
        
        /*dar valores a las caracteristicas del PANEL*/
        this.setTitle("Tabla Coches"); //titulo de la aplicacion
        
/*cuando este todo listo quitar el comentario de resizable*/        
//        this.setResizable(false);// no permite redimencionar
        this.setSize(800, 500); //dimencion X, Y
        this.setLocation(100, 100); //posicion X, Y
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE); //Cerrar programa al pulsar la X
        
        /*creamos los componentes*/
        tbl_tabla = new JTable(modelo);
        scl_tabla = new JScrollPane(tbl_tabla);
        btn_insertar_coche = new JButton("tabla coche");
        txt_doc = new JTextField();
        txt_nombre = new JTextField();
        txt_modelo = new JTextField();
        
        /*damos posicion X,Y, y dimencion X,Y*/
        scl_tabla.setBounds(10, 10, 400, 250);
        btn_insertar_coche.setBounds(420, 10, 150, 30);
        txt_doc.setBounds(420, 50, 150, 30);
        txt_nombre.setBounds(420, 90, 150, 30);
        txt_modelo.setBounds(420, 130, 150, 30);
        
        /*añadimos datos a la tabla*/
        modelo.addColumn("codigo coche");
        modelo.addColumn("nombre");
        modelo.addColumn("modelo");
/*Este apartado se rellena con la informacion de la bbdd (con una sentencia select*/
        cargar_tabla(modelo);
        /*añadiendo los elementos al panel*/
        panel.add(scl_tabla);
        panel.add(btn_insertar_coche);
        panel.add(txt_doc);
        panel.add(txt_nombre);
        panel.add(txt_modelo);
        
/*añadido del action listener*/        
        btn_insertar_coche.addActionListener(new java.awt.event.ActionListener() {
       
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertar_cocheActionPerformed(evt);
            }
    });
        
        this.setVisible(true);//el set visible del panel_maestro debe de ser el ultimo siempre
    }

/*carga la tabla con la insfomacion de la bbdd*/    
    void cargar_tabla(MiModelo modelotabla){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `coches`.`coches`";
            ResultSet rs = st.executeQuery(consulta);
            Object [] fila = new Object[3];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CODCOCHE"));
                fila[1] =  (String) (rs.getObject("NOMBRE"));
                fila[2] =  (String) (rs.getObject("MODELO"));
                modelotabla.addRow ( fila ); // Añade una fila al final de la tabla
            } //fin while
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/*inserta correctamente*/
    void inserta(){
        
        Connection miConexion = (Connection) conexion.ConectarMysql();
        
        try (Statement st = miConexion.createStatement()) {
            
            String insertar = "INSERT INTO `coches`.`coches`(`CODCOCHE`, `NOMBRE`,`MODELO`)"
                        + " VALUES ('"
                        + txt_doc.getText() + "', '"
                        + txt_nombre.getText() + "', '"
                        + txt_modelo.getText() + "')";
            st.execute(insertar);
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
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
           return Object.class;
        }
    }
/*evemtos de botones*/    
    private void btn_insertar_cocheActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        inserta();
        cargar_tabla(modelo);
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
