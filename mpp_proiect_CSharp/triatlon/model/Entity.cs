using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace triatlon.domain
{
	
	public interface Entity<T>
	{
		T Id { get; set; }
	}
}
