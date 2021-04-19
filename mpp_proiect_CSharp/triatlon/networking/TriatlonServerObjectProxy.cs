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
	public class TriatlonServerProxy : ITriatlonServices
	{
		private string host;
		private int port;

		private ITriatlonObserver client;

		private NetworkStream stream;

		private IFormatter formatter;
		private TcpClient connection;

		private Queue<Response> responses;
		private volatile bool finished;
		private EventWaitHandle _waitHandle;
		private int initiate = 0;
		public TriatlonServerProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses = new Queue<Response>();
		}

		public virtual void login(Arbitru user, ITriatlonObserver client)
		{
			
			sendRequest(new LoginRequest(user));
			Response response = readResponse();
			if (response is OkResponse)
			{
				this.client = client;
				return;
			}
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				closeConnection();
				throw new TriatlonException(err.Message);
			}
		}

		public virtual void sendRezult(Rezultat rezultat)
		{
			
			sendRequest(new SendRezultRequest(rezultat));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new TriatlonException(err.Message);
			}
		}

		public virtual void logout(Arbitru user, ITriatlonObserver client)
		{
			
			sendRequest(new LogoutRequest(user));
			Response response = readResponse();
			closeConnection();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new TriatlonException(err.Message);
			}
		}


		public virtual Proba getProbaByArbitru(long id_proba)
		{
            sendRequest(new ProbaRequest(id_proba));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TriatlonException(err.Message);
            }
            ProbaResponse resp = (ProbaResponse)response;
            Proba proba= resp.Proba;

            return proba;
        }
		public virtual Participant findParticipantbyfirstlastName(ParticipantDTO part)
        {
            sendRequest(new FLPARTRequest(part));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TriatlonException(err.Message);
            }
            FLPARTResponse resp = (FLPARTResponse)response;
            Participant participant = resp.Participant;

            return participant;
        }
		public virtual ParticipantDTO[] getParticipanti(Arbitru user)
		{
			
			sendRequest(new GetParticipantiRequest(user));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new TriatlonException(err.Message);
			}
			GetParticipantiResponse resp = (GetParticipantiResponse)response;
			ParticipantDTO[] frDTO = resp.ParticipantDTOs;
			
			return frDTO;
		}
		public virtual Rezultat[] filterbyProba(Proba proba)
        {
			sendRequest(new GetRezultateRequest(proba));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new TriatlonException(err.Message);
			}
			RezultateResponse resp = (RezultateResponse)response;
			Rezultat[] frDTO = resp.Rezultate;

			return frDTO;
		}

		public virtual Arbitru getArbitruLogat(string user)
		{
			if (initiate == 0)
			{
				initializeConnection();
				initiate=1;
			}
			sendRequest(new ArbitruRequest(new Arbitru(user,"")));
			Response response = readResponse();
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new TriatlonException(err.Message);
			}
			ArbitruResponse resp = (ArbitruResponse)response;
			Arbitru frDTO = resp.Arbitru;

			return frDTO;
		}

		private void closeConnection()
		{
			finished = true;
			try
			{
				stream.Close();
				//output.close();
				connection.Close();
				_waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
				formatter.Serialize(stream, request);
				stream.Flush();
			}
			catch (Exception e)
			{
				throw new TriatlonException("Error sending object " + e);
			}

		}

		private Response readResponse()
		{
			Response response = null;
			try
			{
				_waitHandle.WaitOne();
				lock (responses)
				{
					//Monitor.Wait(responses); 
					response = responses.Dequeue();

				}


			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void initializeConnection()
		{
			try
			{
				connection = new TcpClient(host, port);
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				finished = false;
				_waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}


		private void handleUpdate(UpdateResponse update)
		{
			

			if (update is NewRezultResponse)
			{
				NewRezultResponse msgRes = (NewRezultResponse)update;
				
				try
				{
					client.rezultReceived(msgRes.Rezultat);
				}
				catch (TriatlonException e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}
		}
		public virtual void run()
		{
			while (!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received " + response);
					if (response is UpdateResponse)
					{
						lock (responses)
						{
							handleUpdate((UpdateResponse)response);
						}
					}
					else
					{

						lock (responses)
						{


							responses.Enqueue((Response)response);

						}
						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}
		//}
	}

}
