package InterfazIsma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lynchaniano
 */
public final class VentanaDistribucion extends JDialog {

    private final ConexionDB conexion = new ConexionDB();//La conexión con la base de datos

    //public boolean TablaDistribucion = true;//Para controlar la tabla a visualizar

    /*UN JPANEL PARA CONTENER TODOS LOS DEMÁS*/
    JPanel panel;

    /*ESTO PARA EL FORMULARIO PARA INSERTAR COCHES EN CONCESIONARIOS*/
    JPanel JPanelInsertar;//El Panel que contendrá todos los campos de insercción de datos
    JLabel JLabelCabeceraInsertar;//La cabecera del panel
    JLabel JlabelCantidadInsertar;
    JTextField JTextFieldCantidadInsertar;
    JLabel JLabelConcesionarioInsertar;
    JComboBox JComboConcesionarioInsertar;
    DefaultComboBoxModel ModeloConcesionarioInsertar;
    JLabel JLabelCocheInsertar;
    JComboBox JComboCocheInsertar;
    DefaultComboBoxModel ModeloCocheInsertar;
    JButton JButtonAceptarInsertar;
    String insertar;
    String update;

    /*ESTO ES PARA LA SECCIÓN DE LA TABLA DISTRIBUCIÓN*/
    JScrollPane JScrollPaneTablaDistribucion;
    CustomDefaultTableModel ModeloTablaDistribucion;
    JTable TablaDistribucion;

    /*Estos JLabel son para ajustar los campos en los GridLayouts*/
    JLabel RCarro, RCarro2, RCarro3, RCarro4, RCarro5;

    public VentanaDistribucion() {
        super();//Heredo el JFrame
        /*DEFINO LA VENTANA MADRE*/
        setSize(600, 280);//le doy altura y ancho a la ventana (JFrame)
        setTitle("DISTRIBUCIÓN");//la titulo
        setResizable(false);//Evito que se pueda redimensionar la ventana
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//Habilito el botón de cierre en el Dialog.
        setLocationRelativeTo(null);

        /*INICIALIZO UN PANEL CONTENEDOR GENERAL*/
        panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());//Border... para distribuir el resto de paneles.(EAST,WEST,NORTH,SOUTH,CENTER)

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE QUE ALMACENARÁ LA TABLA CON LOS DATOS REFERENTES A LA DISTRIBUCIÓN
         ------------------------------------------------------------------------------------------------------------------*/
        ModeloTablaDistribucion = new CustomDefaultTableModel();
        TablaDistribucion = new JTable(ModeloTablaDistribucion);
        JScrollPaneTablaDistribucion = new JScrollPane(TablaDistribucion);
        panel.add("Center", JScrollPaneTablaDistribucion);//Lo situo al centro del BorderLayout
        ModeloTablaDistribucion.addColumn("CIFC");
        ModeloTablaDistribucion.addColumn("CODCOCHE");
        ModeloTablaDistribucion.addColumn("CANTIDAD");


        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE INSERCIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelInsertar = new JPanel();//Inicializo el panel que contendrá el formulario
        JPanelInsertar.setLayout(new BoxLayout(JPanelInsertar, BoxLayout.Y_AXIS));
        JPanelInsertar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelInsertar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("West", JPanelInsertar);//Lo situo a la izquierda del Panel General

        /*INSERTO LOS CAMPOS DE INSERTAR*/
        JLabelCabeceraInsertar = new JLabel("AÑADIR O TRANSLADAR");
        JLabelCabeceraInsertar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraInsertar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelInsertar.add(JLabelCabeceraInsertar);
        
        RCarro = new JLabel(" ");JPanelInsertar.add(RCarro);
        
        
        ModeloConcesionarioInsertar = new DefaultComboBoxModel();
        JLabelConcesionarioInsertar = new JLabel("CIFC");
        JPanelInsertar.add(JLabelConcesionarioInsertar);
        JComboConcesionarioInsertar = new JComboBox(ModeloConcesionarioInsertar);
        JPanelInsertar.add(JComboConcesionarioInsertar);
        RCarro4 = new JLabel(" ");JPanelInsertar.add(RCarro4);
        

        ModeloCocheInsertar = new DefaultComboBoxModel();
        JLabelCocheInsertar = new JLabel("COCHE");
        JPanelInsertar.add(JLabelCocheInsertar);
        JComboCocheInsertar = new JComboBox(ModeloCocheInsertar);
        JPanelInsertar.add(JComboCocheInsertar);
        RCarro5 = new JLabel(" ");JPanelInsertar.add(RCarro5);

        JlabelCantidadInsertar = new JLabel("CANTIDAD");
        JPanelInsertar.add(JlabelCantidadInsertar);

        JTextFieldCantidadInsertar = new JTextField();
        /*LIMITO AL USUARIO AL USO DE CARACTERES NUMÉRICOS Y TAMBIÉN SU LONGITUD*/

        JTextFieldCantidadInsertar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int limitador = 3;
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9')) || JTextFieldCantidadInsertar.getText().length() >= limitador) {
                    e.consume();
                }
            }
        });
        JPanelInsertar.add(JTextFieldCantidadInsertar);

        /*INSERTO EL BOTÓN ACEPTAR Y SU LISTENER*/
        JButtonAceptarInsertar = new JButton("ACEPTAR");
        JPanelInsertar.add(JButtonAceptarInsertar);

        /* ACTIVO EL LISTENER*/
        JButtonAceptarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Insertar_Coche();
                    LimpiarJTable();
                    Cargar_Tabla_Distribucion();
                    JTextFieldCantidadInsertar.setText(null);
                    JComboConcesionarioInsertar.setSelectedIndex(0);
                    JComboCocheInsertar.setSelectedIndex(0);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        Cargar_Tabla_Distribucion();

    }
    /*MÉTODO PARA INSERTAR DATOS EN LA BASE
     TAMBIÉN RESETEA EL JTABLE Y LOS JCOMOBOX PARA PODER ACTUALIZARLOS Y EVITAR DUPLICIDAD EN SU CONTENIDO 
     ESTE MÉTODO TIENE LA PECULIARIDAD DE INTERACTUAR CON VARIAS TABLAS Y REALIZAR TANTO INSERTS COMO UPDATES EN ELLAS*/

    void Cargar_Tabla_Distribucion() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`DISTRIBUCION`";

            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[3];

            /*Este while llena la tabla con los datos*/
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                fila[1] = (String) (rs.getObject("CODCOCHE"));
                fila[2] = (rs.getObject("CANTIDAD"));
                ModeloTablaDistribucion.addRow(fila);

            } //fin while
            consulta = "SELECT CIFC FROM `COCHES`.`CONCESIONARIOS`";
            rs = st.executeQuery(consulta);

            /*Este while LLena los JCombos con los nombres existentes en la base de datos*/
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CIFC"));
                // La fila 1 equivale al NOMBRE
                JComboConcesionarioInsertar.addItem(fila[0]);

            }
            consulta = "SELECT CODCOCHE FROM `COCHES`.`COCHES`";
            rs = st.executeQuery(consulta);

            /*Este while llena los JCombos con los modelos existentes en la base de datos*/
            while (rs.next()) {
                fila[1] = (String) (rs.getObject("CODCOCHE"));

                JComboCocheInsertar.addItem(fila[1]);

            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Insertar_Coche() {
        Object[] opciones = {"ACEPTAR", "CANCELAR"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "¿SEGURO QUE QUIERES AÑADIR DATOS?: ",
                "CONFIRMAR", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        if (respuestaUsuario == 0) {
            Connection miConexion = (Connection) conexion.ConectarMysql();

            try (Statement st = miConexion.createStatement()) {

                String consulta = "SELECT CIFC, CODCOCHE, CANTIDAD, count(CANTIDAD) from `COCHES`.`DISTRIBUCION` "
                        + "WHERE `CIFC` = '" + JComboConcesionarioInsertar.getSelectedItem() + "' "
                        + "AND `CODCOCHE` = '" + JComboCocheInsertar.getSelectedItem() + "'";

                Object[] fila = new Object[4];

                ResultSet rs = st.executeQuery(consulta);

                while (rs.next()) {
                    fila[0] = (String) (rs.getObject("CIFC"));
                    fila[1] = (String) (rs.getObject("CODCOCHE"));
                    fila[2] = rs.getObject("CANTIDAD");
                    fila[3] = rs.getObject(4);
                }
                int numero = Integer.parseInt(fila[3] + "");

                rs.beforeFirst();

                if (numero == 0) {
                    System.out.println("insert");
                    insertar = "INSERT INTO `COCHES`.`DISTRIBUCION`(`CIFC`, `CODCOCHE`,`CANTIDAD`)"
                            + " VALUES ('"
                            + JComboConcesionarioInsertar.getSelectedItem() + "', '"
                            + JComboCocheInsertar.getSelectedItem() + "', '"
                            + JTextFieldCantidadInsertar.getText() + "')";
                    st.execute(insertar);
                    ModeloTablaDistribucion.setRowCount(0);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "SE SUMARÁ LA CANTIDAD AL COCHE EXISTENTE");

                    int num = Integer.parseInt(JTextFieldCantidadInsertar.getText());
                    int cantidad = (int) fila[2];
                    int suma = num + cantidad;

                    update = "UPDATE `DISTRIBUCION` SET `CANTIDAD`=" + suma + " "
                            + "WHERE `CIFC`='" + JComboConcesionarioInsertar.getSelectedItem() + "' "
                            + "and `CODCOCHE`='" + JComboCocheInsertar.getSelectedItem() + "'";
                    System.out.println(update);
                    st.execute(update);
                    ModeloTablaDistribucion.setRowCount(0);
                }

                JComboConcesionarioInsertar.removeAllItems();
                JComboCocheInsertar.removeAllItems();
                ModeloTablaDistribucion.setRowCount(0);

                st.close();

            } catch (SQLException ex) {
                Logger.getLogger(VentanaDistribucion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*CREAMOS UN MODELO DE TABLA PERSONALIZADO PARA EVITAR QUE EL USUARIO INTERACTÚE CON LOS DATOS */
    public class CustomDefaultTableModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Class getColumnClass(int columna) {
            if (columna == 0) {
                return String.class; // en la bbdd es un Char(3) por eso pongo String
            }
            if (columna == 1) {
                return String.class;
            }
            if (columna == 2) {
                return Integer.class;
            }
            return Object.class;
        }
    }
    /*RESETEAR DATOS DE JTABLE (Para poder insertar nuevos)*/

    void LimpiarJTable() {
        int a = ModeloTablaDistribucion.getRowCount();
        for (int i = 0; i < a; i++) {
            ModeloTablaDistribucion.removeRow(0);
        }
    }

}
