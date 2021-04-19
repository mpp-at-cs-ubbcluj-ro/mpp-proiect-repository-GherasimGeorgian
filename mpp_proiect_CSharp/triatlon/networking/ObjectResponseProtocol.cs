using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using triatlon.domain;

namespace networking
{
	public interface Response
	{
	}

	[Serializable]
	public class OkResponse : Response
	{

	}

	[Serializable]
	public class ErrorResponse : Response
	{
		private string message;

		public ErrorResponse(string message)
		{
			this.message = message;
		}

		public virtual string Message
		{
			get
			{
				return message;
			}
		}
	}



	[Serializable]
	public class GetParticipantiResponse : Response
	{
		private ParticipantDTO[] participantDTOs;

		public GetParticipantiResponse(ParticipantDTO[] participantDTOs)
		{
			this.participantDTOs = participantDTOs;
		}

		public virtual ParticipantDTO[] ParticipantDTOs
		{
			get
			{
				return participantDTOs;
			}
		}
	}

	[Serializable]
	public class RezultateResponse : Response
	{
		private Rezultat[] rezultate;

		public RezultateResponse(Rezultat[] rezultate)
		{
			this.rezultate = rezultate;
		}

		public virtual Rezultat[] Rezultate
		{
			get
			{
				return rezultate;
			}
		}
	}
	

	[Serializable]
	public class ArbitruResponse : Response
	{
		private Arbitru arbitru;

		public ArbitruResponse(Arbitru arbitru)
		{
			this.arbitru = arbitru;
		}

		public virtual Arbitru Arbitru
		{
			get
			{
				return arbitru;
			}
		}
	}





	[Serializable]
	public class ProbaResponse : Response
	{
		private Proba proba;

		public ProbaResponse(Proba proba)
		{
			this.proba = proba;
		}

		public virtual Proba Proba
		{
			get
			{
				return proba;
			}
		}
	}
	public class FLPARTResponse : Response
	{
		private Participant participant;

		public FLPARTResponse(Participant participant)
		{
			this.participant = participant;
		}

		public virtual Participant Participant
		{
			get
			{
				return participant;
			}
		}
	}
	
	public interface UpdateResponse : Response
	{
	}

	
	


	[Serializable]
	public class NewRezultResponse : UpdateResponse
	{
		private Rezultat rezultat;

		public NewRezultResponse(Rezultat rezultat)
		{
			this.rezultat = rezultat;
		}

		public virtual Rezultat Rezultat
		{
			get
			{
				return rezultat;
			}
		}
	}
}
