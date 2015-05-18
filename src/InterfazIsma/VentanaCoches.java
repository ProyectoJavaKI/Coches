package InterfazIsma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.DefaultComboBoxModel;
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
public class VentanaCoches extends JFrame implements TableModelListener {

    private ConexionDB conexion = new ConexionDB();//La conexión con la base de datos

    //public boolean TablaCoches = true;//Para controlar la tabla a visualizar

    /*UN JPANEL PARA CONTENER TODOS LOS DEMÁS*/
    JPanel panel;

    /*ESTO PARA EL FORMULARIO PARA INSERTAR COCHES*/
    JPanel JPanelInsertar;//El Panel que contendrá todos los campos de insercción de datos
    JLabel JLabelCabeceraInsertar;//La cabecera del panel
    JLabel JLabelNombreInsertar;
    JComboBox JComboNombreInsertar;
    DefaultComboBoxModel ModeloNombreInsertar;
    JLabel JLabelModeloInsertar;
    JComboBox JComboModeloInsertar;
    DefaultComboBoxModel ModeloModeloInsertar;
    JButton JButtonAceptarInsertar;
    String insertar;


    /*ESTO DEL FORMULARIO PARA MODIFICAR COCHES*/
    JPanel JPanelModificar;
    JLabel JLabelCabeceraModificar;
    JLabel JLabelCodigoModificar;
    JComboBox JComboCodigoModificar;
    DefaultComboBoxModel ModeloCodigoModificar;
    JLabel JlabelNombreModificar;
    JComboBox JComboNombreModificar;
    DefaultComboBoxModel ModeloNombreModificar;
    JLabel JLabelModeloModificar;
    JComboBox JComboModeloModificar;
    DefaultComboBoxModel ModeloModeloModificar;
    JButton JButtonAceptarModificar;
    JButton JButtonCancelarModificar;

    /*ESTO PARA EL COMBO DE ELIMINACIÓN DE COCHES*/
    JPanel JpanelEliminar;
    JLabel JLabelCabeceraEliminar;
    JComboBox JComboEliminar;
    DefaultComboBoxModel ModeloComboEliminar;
    JButton JButtonEliminarCoches;

    /*ESTO ES PARA LA SECCIÓN DE LA TABLA COCHES*/
    JScrollPane JScrollPaneTablacoches;
    DefaultTableModel ModeloTablaCoches;
    JTable TablaCoches;

    public VentanaCoches() {
        super();//Heredo el JFrame
        /*DEFINO LA VENTANA MADRE*/
        setSize(720, 360);//le doy altura y ancho a la ventana (JFrame)
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
        ModeloTablaCoches = new DefaultTableModel();
        TablaCoches = new JTable(ModeloTablaCoches);
        JScrollPaneTablacoches = new JScrollPane(TablaCoches);
        panel.add("Center", JScrollPaneTablacoches);//Lo situo al centro del BorderLayout
        ModeloTablaCoches.addColumn("CODCOCHE");
        ModeloTablaCoches.addColumn("NOMBRE");
        ModeloTablaCoches.addColumn("MODELO");
        Cargar_Tabla(ModeloTablaCoches);

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE INSERCIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelInsertar = new JPanel();//Inicializo el panel que contendrá el formulario
        JPanelInsertar.setLayout(new BoxLayout(JPanelInsertar, BoxLayout.Y_AXIS));
        JPanelInsertar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelInsertar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("West", JPanelInsertar);//Lo situo a la izquierda del Panel General

        /*INSERTO LOS CAMPOS DE INSERTAR*/
        JLabelCabeceraInsertar = new JLabel("Insertar:");
        JLabelCabeceraInsertar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraInsertar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelInsertar.add(JLabelCabeceraInsertar);

        ModeloNombreInsertar = new DefaultComboBoxModel();
        JLabelNombreInsertar = new JLabel("Nombre");
        JPanelInsertar.add(JLabelNombreInsertar);
        JComboNombreInsertar = new JComboBox(ModeloNombreInsertar);
        JPanelInsertar.add(JComboNombreInsertar);

        ModeloModeloInsertar = new DefaultComboBoxModel();
        JLabelModeloInsertar = new JLabel("Modelo");
        JPanelInsertar.add(JLabelModeloInsertar);
        JComboModeloInsertar = new JComboBox(ModeloModeloInsertar);
        JPanelInsertar.add(JComboModeloInsertar);

        /*INSERTO EL BOTÓN ACEPTAR Y SU LISTENER*/
        JButtonAceptarInsertar = new JButton("ACEPTAR");
        JPanelInsertar.add(JButtonAceptarInsertar);

        /* y activamos Listeners de los botones*/
        JButtonAceptarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Insertar_Coches();
                    LimpiarJTable();
                    Cargar_Tabla(ModeloTablaCoches);
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

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE MODIFICACIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelModificar = new JPanel();//Inicializo el panel que contendrá el formulario
        JPanelModificar.setLayout(new BoxLayout(JPanelModificar, BoxLayout.Y_AXIS));
        JPanelModificar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelModificar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("East", JPanelModificar);//Lo situo a la izquierda del Panel General

        /*INSERTO LOS CAMPOS PARA MODIFICACIÓN*/
        JLabelCabeceraModificar = new JLabel("Modificar:");
        JLabelCabeceraModificar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraModificar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelModificar.add(JLabelCabeceraModificar);

        JLabelCodigoModificar = new JLabel("Código");
        JPanelModificar.add(JLabelCodigoModificar);
        ModeloCodigoModificar = new DefaultComboBoxModel();
        JComboCodigoModificar = new JComboBox(ModeloCodigoModificar);
        JPanelModificar.add(JComboCodigoModificar);

        JlabelNombreModificar = new JLabel("Nombre");
        JPanelModificar.add(JlabelNombreModificar);
        ModeloNombreModificar = new DefaultComboBoxModel();
        JComboNombreModificar = new JComboBox(ModeloNombreModificar);
        JPanelModificar.add(JComboNombreModificar);

        JLabelModeloModificar = new JLabel("Modelo");
        JPanelModificar.add(JLabelModeloModificar);
        ModeloModeloModificar = new DefaultComboBoxModel();
        JComboModeloModificar = new JComboBox(ModeloModeloModificar);
        JPanelModificar.add(JComboModeloModificar);

        /*INSERTO EL BOTÓN ACEPTAR MODIFICACIÓN*/
        JButtonAceptarModificar = new JButton("ACEPTAR");
        JPanelModificar.add(JButtonAceptarModificar);
        
        
        
        
        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE ELIMINACIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JpanelEliminar = new JPanel();//Inicializo el panel en el que irán los botones
        JpanelEliminar.setLayout(new FlowLayout());
        JpanelEliminar.setBackground(Color.decode("#F78181"));
        JpanelEliminar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("South", JpanelEliminar);//Lo situo abajo del BorderLayout

        JLabelCabeceraEliminar = new JLabel("Eliminar:");
        JLabelCabeceraEliminar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraEliminar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JpanelEliminar.add(JLabelCabeceraEliminar);
        
        ModeloComboEliminar = new DefaultComboBoxModel();
        JComboEliminar = new JComboBox(ModeloComboEliminar);
        JpanelEliminar.add(JComboEliminar);

    }

    void Cargar_Tabla(DefaultTableModel modelotabla) {

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
            Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Insertar_Coches() {

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
            Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*RESETEAR DATOS DE JTABLE (Para poder insertar nuevos)*/

    void LimpiarJTable() {
        int a = ModeloTablaCoches.getRowCount();
        for (int i = 0; i < a; i++) {
            ModeloTablaCoches.removeRow(0);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
