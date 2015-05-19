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
    CustomDefaultTableModel ModeloTablaCoches;
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
        ModeloTablaCoches = new CustomDefaultTableModel();
        TablaCoches = new JTable(ModeloTablaCoches);
        JScrollPaneTablacoches = new JScrollPane(TablaCoches);
        panel.add("Center", JScrollPaneTablacoches);//Lo situo al centro del BorderLayout
        ModeloTablaCoches.addColumn("CODCOCHE");
        ModeloTablaCoches.addColumn("NOMBRE");
        ModeloTablaCoches.addColumn("MODELO");
        

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
                    Insertar_Coche();
                    LimpiarJTable();
                    Cargar_Tabla_Coches();
                    


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
        JPanelModificar.setBackground(Color.decode("#C1E7EE"));//Le añado un color de fondo
        JPanelModificar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("East", JPanelModificar);//Lo situo a la izquierda del Panel General

        /*INSERTO LOS CAMPOS PARA MODIFICACIÓN*/
        JLabelCabeceraModificar = new JLabel("MODIFICAR");
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
        
         /* y activamos Listeners de los botones*/
        JButtonAceptarModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Modificar_Coche();
                    LimpiarJTable();
                    Cargar_Tabla_Coches();
                    


                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE ELIMINACIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JpanelEliminar = new JPanel();//Inicializo el panel en el que irán los botones
        JpanelEliminar.setLayout(new FlowLayout());
        JpanelEliminar.setBackground(Color.decode("#F78181"));
        JpanelEliminar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panel.add("South", JpanelEliminar);//Lo situo abajo del BorderLayout

        JLabelCabeceraEliminar = new JLabel("ELIMINAR");
        JLabelCabeceraEliminar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraEliminar.setForeground(Color.decode("#000000"));//Personaliza el color por código RGB
        JpanelEliminar.add(JLabelCabeceraEliminar);

        ModeloComboEliminar = new DefaultComboBoxModel();
        JComboEliminar = new JComboBox(ModeloComboEliminar);
        JpanelEliminar.add(JComboEliminar);
        
        /*INSERTO EL BOTÓN DE ELIMINACIÓN*/
        JButtonEliminarCoches = new JButton("Eliminar");
        JpanelEliminar.add(JButtonEliminarCoches);
        
        /* y activamos Listeners de los botones*/
        JButtonEliminarCoches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Eliminar_Coche();
                    LimpiarJTable();
                    Cargar_Tabla_Coches();
                    


                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        
        Cargar_Tabla_Coches();

    }

    void Cargar_Tabla_Coches() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`COCHES`";

            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[3];

            /*Este while llena la tabla con los datos*/
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("CODCOCHE"));
                fila[1] = (String) (rs.getObject("NOMBRE"));
                fila[2] = (String) (rs.getObject("MODELO"));
                ModeloTablaCoches.addRow(fila); // Añade una fila al final de la tabla
                // La fila 0 equivale al CODCOCHE
                JComboEliminar.addItem(fila[0]); // Añade una fila al final combo elimina
                JComboCodigoModificar.addItem(fila[0]);
                
            } //fin while
            consulta = "SELECT DISTINCT NOMBRE FROM `COCHES`.`COCHES`";
            rs = st.executeQuery(consulta);

            /*Este while LLena los JCombos con los nombres existentes en la base de datos*/
            while (rs.next()) {
                fila[1] = (String) (rs.getObject("NOMBRE"));
                // La fila 1 equivale al NOMBRE
                JComboNombreInsertar.addItem(fila[1]);
                JComboNombreModificar.addItem(fila[1]);
            }

            consulta = "SELECT DISTINCT MODELO FROM `COCHES`.`COCHES`";
            rs = st.executeQuery(consulta);

            /*Este while llena los JCombos con los modelos existentes en la base de datos*/
            while (rs.next()) {
                fila[2] = (String) (rs.getObject("MODELO"));
                // La fila 2 equivale al MODELO
                JComboModeloInsertar.addItem(fila[2]);
                JComboModeloModificar.addItem(fila[2]);
            }

            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Insertar_Coche() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `coches`.`coches`";
            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[1];
            while (rs.next()) {
                fila[0] = rs.getObject("CODCOCHE");
            }
            int ultimo_num = Integer.parseInt((String) fila[0]);

            ultimo_num++;
            String insertar = "INSERT INTO `coches`.`coches`(`CODCOCHE`, `NOMBRE`,`MODELO`)"
                    + " VALUES ('"
                    + "0" + ultimo_num + "', '"
                    + JComboNombreInsertar.getSelectedItem() + "', '"
                    + JComboModeloInsertar.getSelectedItem() + "')";
            st.execute(insertar);
            ModeloTablaCoches.setRowCount(0);
            JComboEliminar.removeAllItems();
            JComboCodigoModificar.removeAllItems();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Eliminar_Coche() {
        Object[] opciones = {"Sí, Eliminar este coche", "No, no eliminar"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a eliminar un cohce ¿es ta seguro?: ",
                "Confirmar eliminación del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        if (respuestaUsuario == 0) {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            String opt = (String) JComboEliminar.getSelectedItem();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "DELETE FROM `coches`.`coches` WHERE `CODCOCHE` = '" + opt + "'";
                st.execute(insertar);
                ModeloTablaCoches.setRowCount(0);
                JComboEliminar.removeAllItems();
                JComboCodigoModificar.removeAllItems();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void Modificar_Coche() {
        Object[] opciones = {"Sí, actualizar datos de este coche", "No, no actualizar datos de este coche"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a actualizar la informacion de un cohce ¿es ta seguro?: ",
                "Confirmar actualizacion del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        if (respuestaUsuario == 0) {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "UPDATE `coches`.`coches` SET `NOMBRE`='" + JComboNombreModificar.getSelectedItem() + "',`MODELO`='" + JComboModeloModificar.getSelectedItem() + "' WHERE `CODCOCHE`='" + JComboCodigoModificar.getSelectedItem() + "'";
                st.executeUpdate(insertar);
                ModeloTablaCoches.setRowCount(0);
                JComboCodigoModificar.removeAllItems();
                JComboEliminar.removeAllItems();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(VentanaCoches.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*RESETEAR DATOS DE JTABLE (Para poder insertar nuevos)*/
    void LimpiarJTable() {
        int a = ModeloTablaCoches.getRowCount();
        for (int i = 0; i < a; i++) {
            ModeloTablaCoches.removeRow(0);
        }
    }
    
    /*CREAMOS UN MODELO DE TABLA PERSONALIZADO PARA EVITAR QUE EL USUARIO INTERACTÚE CON LOS DATOS */
    public class CustomDefaultTableModel extends DefaultTableModel
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

    @Override
    public void tableChanged(TableModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
