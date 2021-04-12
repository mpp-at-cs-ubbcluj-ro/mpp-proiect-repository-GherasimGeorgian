using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using triatlon.domain;

namespace networking
{
	public class TriatlonClientWorker : ITriatlonObserver //, Runnable
	{
		private ITriatlonServices server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;
		public TriatlonClientWorker(ITriatlonServices server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				connected = true;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while (connected)
			{
				try
				{
					object request = formatter.Deserialize(stream);
					object response = handleRequest((Request)request);
					if (response != null)
					{
						sendResponse((Response)response);
					}
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}

				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error " + e);
			}
		}
		public virtual void rezultReceived(Rezultat rezultat)
		{
			
			Console.WriteLine("rezultat received  " + rezultat);
			try
			{
				sendResponse(new NewRezultResponse(rezultat));
			}
			catch (Exception e)
			{
				throw new TriatlonException("Sending error: " + e);
			}
		}

		
	

		private Response handleRequest(Request request)
		{
			Response response = null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest logReq = (LoginRequest)request;
				Arbitru udto = logReq.User;
				try
				{
					lock (server)
					{
						server.login(udto, this);
					}
					return new OkResponse();
				}
				catch (TriatlonException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is LogoutRequest)
			{
				Console.WriteLine("Logout request");
				LogoutRequest logReq = (LogoutRequest)request;
				Arbitru udto = logReq.User;
				
				try
				{
					lock (server)
					{

						server.logout(udto, this);
					}
					connected = false;
					return new OkResponse();

				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is SendRezultRequest)
			{
				Console.WriteLine("SendMessageRequest ...");
				SendRezultRequest senReq = (SendRezultRequest)request;
				Rezultat mdto = senReq.Rezultat;
				
				try
				{
					lock (server)
					{
						server.sendRezult(mdto);
					}
					return new OkResponse();
				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			if (request is GetParticipantiRequest)
			{
				Console.WriteLine("GetParticipantiRequest Request ...");
				GetParticipantiRequest getReq = (GetParticipantiRequest)request;
				Arbitru udto = getReq.User;
				
				try
				{
					ParticipantDTO[] participantii;
					lock (server)
					{

						participantii = server.getParticipanti(udto);
					}
				
					return new GetParticipantiResponse(participantii);
				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			if (request is GetRezultateRequest)
			{
				Console.WriteLine("GetRezultateRequest Request ...");
				GetRezultateRequest getReq = (GetRezultateRequest)request;
				Proba udto = getReq.Proba;

				try
				{
					Rezultat[] rezultate;
					lock (server)
					{

						rezultate = server.filterbyProba(udto);
					}

					return new   RezultateResponse(rezultate);
				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			if (request is ArbitruRequest)
			{
				Console.WriteLine("ArbitruRequest Request ...");
				ArbitruRequest getReq = (ArbitruRequest)request;
				Arbitru udto = getReq.User;

				try
				{
					
					lock (server)
					{

						udto = server.getArbitruLogat(udto.username);
					}

					return new ArbitruResponse(udto);
				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			if (request is ProbaRequest)
			{
				Console.WriteLine("ProbaRequest Request ...");
				ProbaRequest getReq = (ProbaRequest)request;
				long idproba = getReq.ID_Proba;
				Proba proba;
				try
				{

					lock (server)
					{

						proba = server.getProbaByArbitru(idproba);
					}

					return new ProbaResponse(proba);
				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is FLPARTRequest)
			{
				Console.WriteLine("ProbaRequest Request ...");
				FLPARTRequest getReq = (FLPARTRequest)request;
				ParticipantDTO partDTO = getReq.ParticipantDTO;
				Participant participant;
				try
				{

					lock (server)
					{

						participant = server.findParticipantbyfirstlastName(partDTO);
					}

					return new FLPARTResponse(participant);
				}
				catch (TriatlonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			return response;
		}

		private void sendResponse(Response response)
		{
			Console.WriteLine("sending response " + response);
			lock (stream)
			{
				formatter.Serialize(stream, response);
				stream.Flush();
			}

		}
	}
}
