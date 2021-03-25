using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using triatlon.domain;
using triatlon.service;
using triatlon.utils;

namespace triatlon
{
    public partial class MainWindowForm : Form
    {
        private Service service;
        private LoginForm loginForm;
        private Arbitru arbitru= null;
        private DataGridViewRow dataGridView1Row = null;
        private Participant selectedParticipant = null;
        private int changePanelVisible = 0;
        public MainWindowForm()
        {
            InitializeComponent();
            
        }
        internal void Set(Service service, LoginForm loginForm)
        {
            this.service = service;
            this.loginForm = loginForm;
            
        }
        internal void SetArbitru(Arbitru arbitru)
        {
            this.arbitru = arbitru;
        }
        private void setFirstLastName()
        {
            this.label1.Text = arbitru.firstName;
            this.label2.Text = arbitru.lastName;
        }
        private void loadData()
        {
            dataGridView1.DataSource = service.GetParticipantDTOs().ToList();
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
            foreach(Proba el in service.getProbe().ToList()){

                ComboboxItem item = new ComboboxItem();
                item.Text = el.tipProba + " - " + el.distanta;
                item.Value = el.Id;

                comboBox1.Items.Add(item);
                comboBox2.Items.Add(item);
            }
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
                selectedParticipant = service.findParticipantbyfirstlastName(firstName, lastName);
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
                    Proba proba = service.getProbaByID(Int64.Parse((comboBox1.SelectedItem as ComboboxItem).Value.ToString()));
                    service.adaugaRezultat(proba,selectedParticipant,Int32.Parse(textBox1.Text));
                }catch(Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
            loadData();
            loadData2();

        }
        private void loadData2()
        {
            try{
                if (dataGridView2.Rows.Count > 0)
                {
                    dataGridView2.DataSource = null;
                    dataGridView2.Rows.Clear();
                    dataGridView2.Refresh();
                }

                Proba proba = service.getProbaByID(Int64.Parse((comboBox2.SelectedItem as ComboboxItem).Value.ToString()));
                IEnumerable<Rezultat> list = service.filterRezultatebyProba(proba);
                DataTable table = ConvertListToDataTableRezultat(list.ToList());
                dataGridView2.DataSource = table;
            }catch(Exception ex)
            {
                
            }
        }
        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            loadData2();
        }
        static DataTable ConvertListToDataTableRezultat(List<Rezultat> list)
        {
            // New table.
            DataTable table = new DataTable();

            // Get max columns.
            int columns = 4;
            
              

            // Add columns.
            table.Columns.Add();
            table.Columns.Add();
            table.Columns.Add();
            table.Columns.Add();


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
        
       
    }
}

