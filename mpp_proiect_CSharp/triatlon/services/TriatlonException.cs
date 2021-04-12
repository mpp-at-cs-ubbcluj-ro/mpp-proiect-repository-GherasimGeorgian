using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace services
{
    public class TriatlonException : Exception
    {
        public TriatlonException() : base() { }

        public TriatlonException(String msg) : base(msg) { }

        public TriatlonException(String msg, Exception ex) : base(msg, ex) { }

    }
  
}
