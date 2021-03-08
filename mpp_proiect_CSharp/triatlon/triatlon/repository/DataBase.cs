using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.repository
{
    class DataBase
    {
        public SQLiteConnection myConnection;

        public DataBase()
        {
            myConnection = new SQLiteConnection("Data Source=triatlon.sqlite3");
            if (!File.Exists("./triatlon.sqlite3"))
            {
                SQLiteConnection.CreateFile("triatlon.sqlite3");
            }
        }
    }
}
