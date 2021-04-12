using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon
{
    public enum TriatlonUserEvent
    {
        NewRezult
    };
    public class TriatlonUserEventArgs : EventArgs
    {
        private readonly TriatlonUserEvent userEvent;
        private readonly Object data;

        public TriatlonUserEventArgs(TriatlonUserEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public TriatlonUserEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
