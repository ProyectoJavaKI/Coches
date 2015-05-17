package InterfazIsma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Lynchaniano
 */
public class VentanaIsma extends JFrame implements TableModelListener {

    private ConexionDB conexion = new ConexionDB();//La conexión con la base de datos

    //public boolean TablaCoches = true;//Para controlar la tabla a visualizar

    /*UN JPANEL PARA CONTENER TODOS LOS DEMÁS*/
    JPanel panel;

    /*ESTO PARA EL FORMULARIO PARA INSERTAR COCHES*/
    JPanel JPanelInsertar;//El Panel que contendrá todos los campos de insercción de datos
    JLabel JLabelCabeceraInsertar;//La cabecera del panel
    JLabel JLabelCodigoInsertar;
    JTextField JTextFieldCodigoInsertar;
    JLabel JlabelNombreInsertar;
    JTextField JTextFieldNombreInsertar;
    JLabel JLabelModeloInsertar;
    JTextField JTextFieldModeloInsertar;
    JButton JButtonAceptarInsertar;
    JButton JButtonResetearInsertar;
    String insertar;

    /*ESTO DEL FORMULARIO PARA MODIFICAR COCHES*/
//    JPanel JPanelModificar;
//    JLabel JLabelCabeceraModificar;
//    JLabel JLabelCodigoModificar;
//    JTextField JTextFieldCodigoModificar;
//    JLabel JlabelNombreModificar;
//    JTextField JTextFieldNombreModificar;
//    JLabel JLabelModeloModificar;
//    JTextField JTextFieldModeloModificar;
//    JButton JButtonAceptarModificar;
//    JButton JButtonCancelarModificar;


//    /*ESTO PARA EL COMBO DE ELIMINACIÓN DE COCHES*/
//    JPanel JpanelEliminar;
//    JLabel JLabelCabeceraEliminar;
//    JComboBox<String>  JComboBoxEliminarCoches;
//    DefaultComboBoxModel ModelModelCombo;
//    JButton JButtonEliminarCoches;

    /*ESTO ES PARA LA SECCIÓN DE LA TABLA COCHES*/
    JScrollPane JScrollPaneTabla;
    DefaultTableModel modeloTablaCoches;
    JTable tabla_Lineas;
    JButton JButtonListar;
    int contador = 0;

    /*ESTO YA VEREMOS DONDE VA*/
    JComboBox JComboBoxCodigoCoches;
    JComboBox JComboBox;

    
    
    
    
    
    public VentanaIsma() {
        super();//Heredo el JFrame
        /*DEFINO LA VENTANA MADRE*/
        setSize(512, 256);//le doy altura y ancho a la ventana (JFrame)
        setTitle("GESTIÓN COCHES");//la titulo
        setResizable(false);//Evito que se pueda redimensionar la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Habilito el botón de cierre en la ventana
        setLocationRelativeTo(null);

        /*INICIALIZO UN PANEL CONTENEDOR GENERAL*/
        panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());//Border... para distribuir el resto de paneles.(EAST,WEST,NORTH,SOUTH,CENTER)

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE QUE ALMACENARÁ LA TABLA CON LOS DATOS DE LOS COCHES
         ------------------------------------------------------------------------------------------------------------------*/
        modeloTablaCoches = new DefaultTableModel();
        tabla_Lineas = new JTable(modeloTablaCoches);
        JScrollPaneTabla = new JScrollPane(tabla_Lineas);
        panel.add("Center", JScrollPaneTabla);//Lo situo al centro del BorderLayout
        modeloTablaCoches.addColumn("CODCOCHE");
        modeloTablaCoches.addColumn("NOMBRE");
        modeloTablaCoches.addColumn("MODELO");
        cargar_tabla(modeloTablaCoches);

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE INSERCIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelInsertar = new JPanel();//Inicializo el panel que contendrá el formulario
        JPanelInsertar.setLayout(new BoxLayout(JPanelInsertar, BoxLayout.Y_AXIS));
        JPanelInsertar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelInsertar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("West", JPanelInsertar);//Lo situo a la izquierda del Panel General

        /*insertamos la cabecera en el panel de insercción*/
        JLabelCabeceraInsertar = new JLabel("Insertar:");
        JLabelCabeceraInsertar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraInsertar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelInsertar.add(JLabelCabeceraInsertar);

        /*insertamos los campos para la insercción*/
        JLabelCodigoInsertar = new JLabel("CODCOCHE:");
        JPanelInsertar.add(JLabelCodigoInsertar);

        JTextFieldCodigoInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldCodigoInsertar);

        JlabelNombreInsertar = new JLabel("NOMBRE:");
        JPanelInsertar.add(JlabelNombreInsertar);

        JTextFieldNombreInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldNombreInsertar);

        JLabelModeloInsertar = new JLabel("MODELO:");
        JPanelInsertar.add(JLabelModeloInsertar);

        JTextFieldModeloInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldModeloInsertar);

        /*insertamos los botones aceptar y cancelar (resetear campos)*/
        JButtonAceptarInsertar = new JButton("ACEPTAR");
        JPanelInsertar.add(JButtonAceptarInsertar);
        JButtonResetearInsertar = new JButton("RESET");
        JPanelInsertar.add(JButtonResetearInsertar);

        /* y activamos Listeners de los botones*/
        JButtonAceptarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    insertar_Coches();
                    LimpiarJTable();
                    cargar_tabla(modeloTablaCoches);
//                    //Compruebo su validez con un método definido posteriormente
//                    if (compruebaValidez()) {
//                        JOptionPane.showMessageDialog(null,
//                                "Se realizó el alta correctamente", "OK", JOptionPane.INFORMATION_MESSAGE);
//
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Los datos introducidos no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
//                    }

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButtonResetearInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    JTextFieldCodigoInsertar.setText(null);
                    JTextFieldNombreInsertar.setText(null);
                    JTextFieldModeloInsertar.setText(null);
                } catch (Exception err) {
                }
            }
        });

    }


    void cargar_tabla(DefaultTableModel modelotabla) {

        Connection miConexion;
        miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`COCHES`";
            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[3];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CODCOCHE"));
                fila[1] = (String) (rs.getObject("NOMBRE"));
                fila[2] = (String) (rs.getObject("MODELO"));
                modelotabla.addRow(fila); // Añade una fila al final de la tabla
            } //fin while
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaIsma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void insertar_Coches() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {

                     insertar = "INSERT INTO `COCHES`.`COCHES`(`CODCOCHE`, `NOMBRE`,`MODELO`)"
                    + " VALUES ('"
                    + JTextFieldCodigoInsertar.getText() + "', '"
                    + JTextFieldNombreInsertar.getText() + "', '"
                    + JTextFieldModeloInsertar.getText() + "')";
            st.execute(insertar);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaIsma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*RESETEAR DATOS DE JTABLE (Para poder insertar nuevos)*/
    void LimpiarJTable() {
        int a = modeloTablaCoches.getRowCount();
        for (int i = 0; i < a; i++) {
            modeloTablaCoches.removeRow(0);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
