package InterfazIsma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Lynchaniano
 */
public class VentanaClientes extends JFrame implements TableModelListener {

    private ConexionDB conexion = new ConexionDB();//La conexión con la base de datos

    //public boolean TablaCoches = true;//Para controlar la tabla a visualizar

    /*UN JPANEL PARA CONTENER TODOS LOS DEMÁS*/
    JPanel panelClientes;

    /*ESTO PARA EL FORMULARIO PARA INSERTAR CLIENTES*/
    //El DNI se añade automáticamente!!
    JPanel JPanelInsertar;//El Panel que contendrá todos los campos de insercción de datos
    JLabel JLabelCabeceraInsertar;//La cabecera del panelClientes
    JLabel JlabelNombreInsertar;
    JTextField JTextFieldNombreInsertar;
    JLabel JlabelApellidoInsertar;
    JTextField JTextFieldApellidoInsertar;
    JLabel JLabelCiudadInsertar;
    JTextField JTextFieldCiudadInsertar;
    JButton JButtonAceptarInsertar;
    JButton JButtonCancelarInsertar;
    String insertar;

    /*ESTO DEL FORMULARIO PARA MODIFICAR COCHES*/
    JComboBox JComboDNI;
    DefaultComboBoxModel ModeloDniModificar;
    JPanel JPanelModificar;
    JLabel JLabelCabeceraModificar;
    JLabel JlabelNombreModificar;
    JTextField JTextFieldNombreModificar;
    JLabel JLabelApellidoModificar;
    JTextField JTextFieldApellidoModificar;
    JLabel JLabelCiudadModificar;
    JTextField JTextFieldCiudadModificar;
    JButton JButtonAceptarModificar;
    JButton JButtonResetearModificar;


    /*ESTO PARA EL COMBO DE ELIMINACIÓN DE COCHES*/
    JPanel JpanelEliminarCliente;
    JLabel JLabelCabeceraEliminar;
    JComboBox JComboEliminarCliente;
    DefaultComboBoxModel ModeloClienteEliminar;
    JButton JButtonEliminarClientes;

    /*ESTO ES PARA LA SECCIÓN DE LA TABLA CLIENTES*/
    JScrollPane JScrollPaneTabla;
    DefaultTableModel modeloTablaClientes;
    JTable Tabla_Clientes;

    public VentanaClientes() {
        super();
        /*DEFINO LA VENTANA MADRE*/
        setSize(512, 320);//le doy altura y ancho a la ventana (JFrame)
        setTitle("GESTIÓN COCHES");//la titulo
        setResizable(false);//Evito que se pueda redimensionar la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Habilito el botón de cierre en la ventana
        setLocationRelativeTo(null);

        /*INICIALIZO UN PANEL CONTENEDOR GENERAL*/
        panelClientes = (JPanel) this.getContentPane();
        panelClientes.setLayout(new BorderLayout());//Border... para distribuir el resto de paneles.(EAST,WEST,NORTH,SOUTH,CENTER)

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE QUE ALMACENARÁ LA TABLA CON LOS DATOS DE LOS COCHES
         ------------------------------------------------------------------------------------------------------------------*/
        modeloTablaClientes = new DefaultTableModel();
        Tabla_Clientes = new JTable(modeloTablaClientes);
        JScrollPaneTabla = new JScrollPane(Tabla_Clientes);
        panelClientes.add("Center", JScrollPaneTabla);//Lo situo al centro del BorderLayout
        modeloTablaClientes.addColumn("DNI");
        modeloTablaClientes.addColumn("NOMBRE");
        modeloTablaClientes.addColumn("APELLIDO");
        modeloTablaClientes.addColumn("CIUDAD");
        cargar_tabla(modeloTablaClientes);

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE INSERCIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelInsertar = new JPanel();//Inicializo el panelClientes que contendrá el formulario
        JPanelInsertar.setLayout(new BoxLayout(JPanelInsertar, BoxLayout.Y_AXIS));
        JPanelInsertar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelInsertar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panelClientes.add("West", JPanelInsertar);//Lo situo a la izquierda del Panel General

        /*INSERTAMOS LOS CAMPOS DE INSERCIÓN*/
        JLabelCabeceraInsertar = new JLabel("Insertar:");
        JLabelCabeceraInsertar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraInsertar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelInsertar.add(JLabelCabeceraInsertar);

        JlabelNombreInsertar = new JLabel("Nombre:");
        JPanelInsertar.add(JlabelNombreInsertar);

        JTextFieldNombreInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldNombreInsertar);

        JlabelApellidoInsertar = new JLabel("Apellido:");
        JPanelInsertar.add(JlabelApellidoInsertar);

        JTextFieldApellidoInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldApellidoInsertar);

        JLabelCiudadInsertar = new JLabel("Ciudad:");
        JPanelInsertar.add(JLabelCiudadInsertar);

        JTextFieldCiudadInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldCiudadInsertar);

        /*insertamos los botones aceptar y reset (resetear campos)*/
        JButtonAceptarInsertar = new JButton("ACEPTAR");
        JPanelInsertar.add(JButtonAceptarInsertar);
        JButtonCancelarInsertar = new JButton("RESET");
        JPanelInsertar.add(JButtonCancelarInsertar);

        /* y activamos Listeners de los botones*/
        JButtonAceptarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Inserta_Cliente();
                    LimpiarJTable();
                    cargar_tabla(modeloTablaClientes);
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

        JButtonCancelarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    JTextFieldNombreInsertar.setText(null);
                    JTextFieldApellidoInsertar.setText(null);
                    JTextFieldCiudadInsertar.setText(null);
                } catch (Exception err) {
                }
            }
        });

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE ELIMINACIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JpanelEliminarCliente = new JPanel();//Inicializo el panelClientes en el que irán los botones
        JpanelEliminarCliente.setLayout(new FlowLayout());
        JpanelEliminarCliente.setBackground(Color.decode("#F78181"));
        JpanelEliminarCliente.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panelClientes.add("South", JpanelEliminarCliente);//Lo situo abajo del BorderLayout

        JLabelCabeceraEliminar = new JLabel("Eliminar:");
        JLabelCabeceraEliminar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraEliminar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JpanelEliminarCliente.add(JLabelCabeceraEliminar);

        ModeloClienteEliminar = new DefaultComboBoxModel();

        JComboEliminarCliente = new JComboBox();
        JComboEliminarCliente.setModel(ModeloClienteEliminar);

        JpanelEliminarCliente.add(JComboEliminarCliente);

        JButtonEliminarClientes = new JButton("ELIMINAR");
        JpanelEliminarCliente.add(JButtonEliminarClientes);
        JButtonEliminarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {

                } catch (Exception err) {
                }
            }
        });

    }

    void cargar_tabla(DefaultTableModel modelotabla) {

        Connection miConexion;
        miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`CLIENTES`";
            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[3];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("DNI"));
                fila[1] = (String) (rs.getObject("NOMBRE"));
                fila[2] = (String) (rs.getObject("APELLIDO"));
                fila[3] = (String) (rs.getObject("CIUDAD"));
                modelotabla.addRow(fila); // Añade una fila al final de la tabla
            } //fin while
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Inserta_Cliente() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`CLIENTES`";
            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[1];
            while (rs.next()) {
                fila[0] = rs.getObject("DNI");
            }
            int ultimo_num = Integer.parseInt((String) fila[0]);

            ultimo_num++;
            insertar = "INSERT INTO `COCHES`.`CLIENTES`(`DNI`, `NOMBRE`,`APELLIDO`,`CIUDAD`)"
                    + " VALUES ('"
                    + "0" + ultimo_num + "', '"
                    + JTextFieldNombreInsertar.getText() + "', '"
                    + JTextFieldApellidoInsertar.getText() + "', '"
                    + JTextFieldCiudadInsertar.getText() + "')";
            st.execute(insertar);
//            modelo_tabla.setRowCount(0);
//            cmb_eliminar_coche.removeAllItems();
//            cmb_actualiza_coche.removeAllItems();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*RESETEAR DATOS DE JTABLE (Para poder insertar nuevos)*/

    void LimpiarJTable() {
        int a = modeloTablaClientes.getRowCount();
        for (int i = 0; i < a; i++) {
            modeloTablaClientes.removeRow(0);
        }
    }

    public class MiModeloCombo extends DefaultComboBoxModel {
        //vacio para que este por defecto
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
