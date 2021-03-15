﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{ 
    class Arbitru : Entity<long>
    {
        public long Id { get; set; }

        public string firstName { get; set; }

        public string lastName { get; set; }

        public string email { get; set; }

        public string username { get; set; }

        public string password { get; set; }
  
        public Arbitru(long id, string firstname, string lastname, string email1, string username1, string password1)
        {
            this.Id = id;
            this.firstName = firstname;
            this.lastName = lastname;
            this.email = email1;
            this.username = username1;
            this.password = password1;

        }
        public override string ToString()
        {
            return "Arbitru - nume: " + firstName + '|' +
                "prenume=" + lastName;
        }

    }
}
