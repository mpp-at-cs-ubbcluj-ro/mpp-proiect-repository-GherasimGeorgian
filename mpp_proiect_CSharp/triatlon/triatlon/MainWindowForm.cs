using model;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using triatlon.domain;
using triatlon.service;
using triatlon.utils;

namespace triatlon
{
    public partial class MainWindowForm : Form
    {
       
        private LoginForm loginForm;
        private Arbitru arbitru= null;
        private DataGridViewRow dataGridView1Row = null;
        private int changePanelVisible = 0;
        private TriatlonClientCtrl ctrl;
        private int waschanged = 0;
        BindingList<RezultatDTO> li = new BindingList<RezultatDTO>();
        public MainWindowForm(TriatlonClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
            arbitru = ctrl.getArbitruCurentLogat();
            ctrl.updateEvent += userUpdate;
        }
        internal void Set(LoginForm loginForm)
        {
            
            this.loginForm = loginForm;
            
        }
        
        private void setFirstLastName()
        {
            this.label1.Text = ctrl.getFirstNameArbitru();
            this.label2.Text = ctrl.getLastNameArbitru();
        }
        private void loadData()
        {
            dataGridView1.DataSource = ctrl.getParticipanti();
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            setFirstLastName();
            loadData();
            loadToolboxComponents();
            loadComboBox();
        }
        private void loadToolboxComponents()
        {
            panel1.Visible = false;
        }
        private void loadComboBox()
        {
            Proba proba = ctrl.getProbabyArbitru();
          
            ComboboxItem item = new ComboboxItem();
            item.Text = proba.tipProba + " - " + proba.distanta;
            item.Value = proba.Id;

            comboBox1.Items.Add(item);
            comboBox2.Items.Add(item);
            
        }
        private void showAddaugareRezultat()
        {
            panel1.Visible = true;
        }
        private void hideAddaugareRezultat()
        {
            panel1.Visible = false;
        }
        private void dataGridView1_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            dataGridView1Row = dataGridView1.Rows[e.RowIndex];
            if (changePanelVisible == 0)
            {
                showAddaugareRezultat();
                string firstName = dataGridView1Row.Cells[0].Value.ToString();
                string lastName = dataGridView1Row.Cells[1].Value.ToString();
                changePanelVisible = 1;
                
            }
            else
            {
                hideAddaugareRezultat();
                changePanelVisible = 0;
            }
           


        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (dataGridView1Row.Selected != null)
            {
                try
                {
                    string firstName = dataGridView1Row.Cells[0].Value.ToString();
                    string lastName = dataGridView1Row.Cells[1].Value.ToString();
                    ParticipantDTO part1 = new ParticipantDTO(firstName, lastName);
                    Proba proba = new Proba(34242, "inot", 54);
                    //Proba proba = ctrl.getProbabyID(Int64.Parse((comboBox1.SelectedItem as ComboboxItem).Value.ToString()));

                    //adaugam rezultatul in db
                    ctrl.adaugaRezultat(proba, part1, Int32.Parse(textBox1.Text));

                   // li.Add(new RezultatDTO() { firstName = part1.firstName, lastName = part1.lastName, tipproba = proba.tipProba, numarpuncte = Int32.Parse(textBox1.Text) });

                    


                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
           
          

        }
        private void loadData2()
        {
            try
            {
               
                Proba proba = ctrl.getProbabyID(Int64.Parse((comboBox2.SelectedItem as ComboboxItem).Value.ToString()));
                MessageBox.Show("Am gasit proba: " + proba.ToString());
                IEnumerable<Rezultat> listRezultateProba = ctrl.filterRezultateByProba(proba);
                //dataGridView2.Rows.Clear();
                //foreach (Rezultat rez in listRezultateProba)
                //{
                //    li.Add(new RezultatDTO() { firstName = rez.participant.firstName, lastName = rez.participant.lastName, tipproba = rez.proba.tipProba, numarpuncte = rez.numarPuncte });
                //}
                DataTable table = ConvertListToDataTableRezultat(listRezultateProba.ToList());
                dataGridView2.DataSource = table;
            }
            catch (Exception ex)
            {

            }
        }
        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            waschanged = 1;
            loadData2();
        }
        static DataTable ConvertListToDataTableRezultat(List<Rezultat> list)
        {
            DataTable table = new DataTable();

            int columns = 4;
            
            table.Columns.Add("FirstName");
            table.Columns.Add("LastName");
            table.Columns.Add("TipProba");
            table.Columns.Add("Punctaj");


            // Add rows.
            foreach (Rezultat rez in list)
            {
                table.Rows.Add(rez.participant.firstName, rez.participant.lastName, rez.proba.tipProba,rez.numarPuncte);
            }

            return table;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            loginForm.Show();
        }
        public void userUpdate(object sender, TriatlonUserEventArgs e)
        {

            if (e.UserEventType == TriatlonUserEvent.NewRezult)
            {
                Rezultat rez = (Rezultat)e.Data;

                //celalalt rez direct in datagridview2
                


                dataGridView1.BeginInvoke(new UpdateDataGridViewCallback(this.updateDataGrivView1), new Object[] { rez });
               
            }
        }

        public delegate void UpdateDataGridViewCallback(Rezultat rez);
        private void updateDataGrivView1(Rezultat rez)
        {
            foreach(DataGridViewRow dgvr in dataGridView1.Rows)
            {
                if ((dgvr.Cells[0].Value.ToString().Equals(rez.participant.firstName)) &&
                   (dgvr.Cells[1].Value.ToString().Equals(rez.participant.lastName)))
                {
                    int rez_nou = rez.numarPuncte + Int32.Parse(dgvr.Cells[2].Value.ToString());
                    dgvr.Cells[2].Value = rez_nou.ToString(); 
                }
            }
        }
       


    }
}

