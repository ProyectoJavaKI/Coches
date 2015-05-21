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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lynchaniano
 */
public class VentanaVentas extends JDialog {

    private ConexionDB conexion = new ConexionDB();//La conexión con la base de datos

    /*UN JPANEL PARA CONTENER TODOS LOS DEMÁS*/
    JPanel panel;

    /*CREO LOS COMPONENTES DE LA INTERFAZ PARA INSERTAR VENTAS*/
    JPanel JPanelInsertar;//El Panel que contendrá todos los campos de insercción de datos
    JLabel JLabelCabeceraInsertar;//La cabecera del panel
    JLabel JLabelConcesionarioInsertar;
    JComboBox JComboConcesionarioInsertar;
    DefaultComboBoxModel ModeloConcesionarioInsertar;
    JLabel JLabelDNIInsertar;
    JComboBox JComboDNIInsertar;
    DefaultComboBoxModel ModeloDNIInsertar;
    JLabel JLabelCocheInsertar;
    JComboBox JComboCocheInsertar;
    DefaultComboBoxModel ModeloCocheInsertar;
    JLabel JLabelColorInsertar;
    JComboBox JComboColorInsertar;
    DefaultComboBoxModel ModeloColorInsertar;
    JButton JButtonAceptarInsertar;
    String insertar;

    /*CREO LOS COMPONENTES DE LA TABLA (JTable) COCHES*/
    JScrollPane JScrollPaneTablaVentas;
    CustomDefaultTableModel ModeloTablaVentas;
    JTable TablaVentas;

    /* EN EL CONSTRUCTOR INICIALIZAMOS TODOS LOS PANELES QUE RELLENARÁN EL FRAME SUS COMPONENTES */
    public VentanaVentas() {
        super();//Heredo el JFrame
        /*DEFINO LA VENTANA MADRE*/
        setSize(720, 360);//le doy altura y ancho a la ventana (JFrame)
        setTitle("VENTAS");//la titulo
        setResizable(false);//Evito que se pueda redimensionar la ventana
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//Habilito el botón de cierre en el Dialog.
        setLocationRelativeTo(null);

        /*INICIALIZO UN PANEL CONTENEDOR GENERAL*/
        panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());//Border... para distribuir el resto de paneles.(EAST,WEST,NORTH,SOUTH,CENTER)

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE QUE ALMACENARÁ LA TABLA CON LOS DATOS DE LOS COCHES
         ------------------------------------------------------------------------------------------------------------------*/
        ModeloTablaVentas = new CustomDefaultTableModel();
        TablaVentas = new JTable(ModeloTablaVentas);
        JScrollPaneTablaVentas = new JScrollPane(TablaVentas);
        panel.add("Center", JScrollPaneTablaVentas);//Lo situo al centro del BorderLayout
        ModeloTablaVentas.addColumn("CIFC");
        ModeloTablaVentas.addColumn("DNI");
        ModeloTablaVentas.addColumn("CODCOCHE");
        ModeloTablaVentas.addColumn("COLOR");


        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE INSERCIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelInsertar = new JPanel();//Inicializo el panel que contendrá el formulario
        JPanelInsertar.setLayout(new BoxLayout(JPanelInsertar, BoxLayout.Y_AXIS));
        JPanelInsertar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelInsertar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("West", JPanelInsertar);//Lo situo a la izquierda del Panel General

        /*INSERTO LOS CAMPOS DE INSERTAR*/
        JLabelCabeceraInsertar = new JLabel("INSERTAR");
        JLabelCabeceraInsertar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraInsertar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelInsertar.add(JLabelCabeceraInsertar);

        ModeloConcesionarioInsertar = new DefaultComboBoxModel();
        JLabelConcesionarioInsertar = new JLabel("CIFC");
        JPanelInsertar.add(JLabelConcesionarioInsertar);
        JComboConcesionarioInsertar = new JComboBox(ModeloConcesionarioInsertar);
        /* ACTIVO EL LISTENER DEL COMBO PARA SU CORRECTA ACTUALIZACIÓN A LOS CAMBIOS*/
        JComboConcesionarioInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    recarga_Combo_Concesionarios();
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanelInsertar.add(JComboConcesionarioInsertar);

        ModeloDNIInsertar = new DefaultComboBoxModel();
        JLabelDNIInsertar = new JLabel("DNI");
        JPanelInsertar.add(JLabelDNIInsertar);
        JComboDNIInsertar = new JComboBox(ModeloDNIInsertar);
        JPanelInsertar.add(JComboDNIInsertar);

        ModeloCocheInsertar = new DefaultComboBoxModel();
        JLabelCocheInsertar = new JLabel("CODCOCHE");
        JPanelInsertar.add(JLabelCocheInsertar);
        JComboCocheInsertar = new JComboBox(ModeloCocheInsertar);
        JPanelInsertar.add(JComboCocheInsertar);

        ModeloColorInsertar = new DefaultComboBoxModel();
        JLabelColorInsertar = new JLabel("COLOR");
        JPanelInsertar.add(JLabelColorInsertar);
        JComboColorInsertar = new JComboBox(ModeloColorInsertar);
        JPanelInsertar.add(JComboColorInsertar);

        /*INSERTO EL BOTÓN ACEPTAR Y SU LISTENER*/
        JButtonAceptarInsertar = new JButton("ACEPTAR");
        JPanelInsertar.add(JButtonAceptarInsertar);

        /* ACTIVO EL LISTENER*/
        JButtonAceptarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Insertar_Ventas();
                    Cargar_Tabla_Ventas();
                    JComboConcesionarioInsertar.setSelectedIndex(0);
                    JComboDNIInsertar.setSelectedIndex(0);
                    JComboCocheInsertar.setSelectedIndex(0);
                    JComboColorInsertar.setSelectedIndex(0);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*POR ÚLTIMO, RELLENAMOS LA TABLA CON DATOS DE LA BASE*/
        Cargar_Tabla_Ventas();

    }
    /*MÉTODO PARA CARGAR DATOS AL JTABLE A TRAVÉS DE CONSULTA SQL
     APROVECHANDO EL RECORRIDO DE LAS TABLAS,TAMBIEN RELLENA LOS JCOMBOBOX*/

    void Cargar_Tabla_Ventas() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`VENTAS`";

            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[4];

            /*Este while llena la tabla con los datos*/
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] = (String) (rs.getObject("DNI"));
                fila[2] = (String) (rs.getObject("CODCOCHE"));
                fila[3] = (String) (rs.getObject("COLOR"));
                ModeloTablaVentas.addRow(fila); // Añade una fila al final de la tabla
            } //fin while
            consulta = "SELECT CIFC FROM `COCHES`.`CONCESIONARIOS`";
            rs = st.executeQuery(consulta);

            /*Este while LLena los JCombos con los nombres existentes en la base de datos*/
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));

                JComboConcesionarioInsertar.addItem(fila[0]);

            }

            consulta = "SELECT DNI FROM `COCHES`.`CLIENTES`";
            rs = st.executeQuery(consulta);

            /*Este while llena los JCombos con los modelos existentes en la base de datos*/
            while (rs.next()) {
                fila[1] = (String) (rs.getObject("DNI"));

                JComboDNIInsertar.addItem(fila[1]);

            }
            consulta = "SELECT CODCOCHE FROM `COCHES`.`COCHES`";
            rs = st.executeQuery(consulta);

            /*Este while llena los JCombos con los modelos existentes en la base de datos*/
            while (rs.next()) {
                fila[2] = (String) (rs.getObject("CODCOCHE"));

                JComboCocheInsertar.addItem(fila[2]);

            }
            consulta = "SELECT DISTINCT COLOR FROM `COCHES`.`VENTAS`";
            rs = st.executeQuery(consulta);

            /*Este while llena los JCombos con los modelos existentes en la base de datos*/
            while (rs.next()) {
                fila[3] = (String) (rs.getObject("COLOR"));

                JComboColorInsertar.addItem(fila[3]);

            }

            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*MÉTODO PARA INSERTAR DATOS EN LA BASE
     TAMBIÉN RESETEA EL JTABLE Y LOS JCOMOBOX PARA PODER ACTUALIZARLOS Y EVITAR DUPLICIDAD EN SU CONTENIDO */

    void Insertar_Ventas() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT CIFC, DNI, CODCOCHE, COLOR, count(CODCOCHE) "
                    + "FROM `COCHES`.`VENTAS` "
                    + "WHERE `CIFC` = '" + JComboConcesionarioInsertar.getSelectedItem() + "' "
                    + "AND `DNI` = '" + JComboDNIInsertar.getSelectedItem() + "'"
                    + "AND `CODCOCHE` = '" + JComboCocheInsertar.getSelectedItem() + "'";
            System.out.println(consulta);

            Object[] fila = new Object[5];
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] = (String) (rs.getObject("DNI"));
                fila[2] = rs.getObject("CODCOCHE");
                fila[3] = rs.getObject("COLOR");
                fila[4] = rs.getObject(5);
            }
            int numero = Integer.parseInt("" + fila[4]);
            try (Statement st2 = miConexion.createStatement()) {
                if (numero == 0) {
//comprovar que CONCESIONARIO.CANTIDAD contiene un numero valido ( NO 0)         
                    Object cantidad[] = new Object[1];
                    consulta = "SELECT CANTIDAD FROM `COCHES`.`distribucion` "
                            + "WHERE `CIFC`='" + JComboConcesionarioInsertar.getSelectedItem() + "' "
                            + "AND `CODCOCHE`='" + JComboCocheInsertar.getSelectedItem() + "'";
                    System.out.println(consulta);
                    ResultSet rs2 = st2.executeQuery(consulta);
                    while (rs2.next()) {
                        cantidad[0] = rs2.getObject("CANTIDAD");

                    }
                    int cant = Integer.parseInt("" + cantidad[0]);
                    if (cant > 0) {

                        System.out.println("antes update");
                        cant--;
                        String update = "UPDATE `COCHES`.`DISTRIBUCION` SET `CANTIDAD`=" + cant + " "
                                + "WHERE `CIFC`='" + JComboConcesionarioInsertar.getSelectedItem() + "' "
                                + "and `CODCOCHE`='" + JComboCocheInsertar.getSelectedItem() + "'";
                        System.out.println(update);
                        st.execute(update);

                        System.out.println("antes insert");
                        String insertar = "INSERT INTO `COCHES`.`VENTAS`(`CIFC`, `DNI`, `CODCOCHE`, `COLOR`)"
                                + " VALUES ('"
                                + JComboConcesionarioInsertar.getSelectedItem() + "', '"
                                + JComboDNIInsertar.getSelectedItem() + "', '"
                                + JComboCocheInsertar.getSelectedItem() + "', '"
                                + JComboColorInsertar.getSelectedItem() + "')";
                        System.out.println(insertar);
                        st.execute(insertar);

                        ModeloTablaVentas.setRowCount(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Este coche ya he sido vendido ha este cliente");
                }
            }

            JComboConcesionarioInsertar.removeAllItems();
            JComboDNIInsertar.removeAllItems();
            JComboCocheInsertar.removeAllItems();
            JComboColorInsertar.removeAllItems();
            ModeloTablaVentas.setRowCount(0);

            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(VentanaVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*MÉTODO UTILIZADO PARA LA CORRECTA ACTUALIZACIÓN DEL COMBO CON LOS CIFC*/
    void recarga_Combo_Concesionarios() {
        Connection miConexion = (Connection) conexion.ConectarMysql();

        JComboCocheInsertar.removeAllItems();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT CODCOCHE FROM `COCHES`.`DISTRIBUCION` WHERE `CIFC`='" + JComboConcesionarioInsertar.getSelectedItem() + "'";

            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[1];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CODCOCHE"));
                JComboCocheInsertar.addItem(fila[0]);
            } //fin while

            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*CREAMOS UN MODELO DE TABLA PERSONALIZADO PARA EVITAR QUE EL USUARIO INTERACTÚE CON LOS DATOS */
    public class CustomDefaultTableModel extends DefaultTableModel {

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Class getColumnClass(int columna) {
            if (columna == 0) {
                return String.class;
            }
            if (columna == 1) {
                return String.class;
            }
            if (columna == 2) {
                return String.class;
            }
            if (columna == 3) {
                return String.class;
            }
            return Object.class;
        }
    }

}
