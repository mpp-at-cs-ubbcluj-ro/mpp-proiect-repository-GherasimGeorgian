using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using model;
using Newtonsoft.Json;

namespace RestServices
{
    class Program
    {
		static HttpClient client = new HttpClient();

		public static void Main(string[] args)
		{
			Console.WriteLine("Hello World!");
			RunAsync().Wait();
		}


		static async Task RunAsync()
		{
			client.BaseAddress = new Uri("http://localhost:8080/triatlon/greeting");
			client.DefaultRequestHeaders.Accept.Clear();
			//client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("text/plain"));
			client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
		
			
			long id = 321423;
			Console.WriteLine("Get proba {0}", id);
			Proba result = await GetProbaAsync("http://localhost:8080/triatlon/probe/" + id);
			Console.WriteLine("Am primit {0}", result);

			Console.WriteLine("Get all Probe");
			List<Proba> result2 = await GetAllProbaAsync("http://localhost:8080/triatlon/probe/");
			foreach (Proba pr in result2)
			{
				Console.WriteLine(pr);
			}
			

			Console.ReadLine();
		}




        //static async Task<Proba> AddProbaAsync(string path)
        //{
        //    Proba pr1 = new Proba(53, "inot", 445);
        //    var content = new FormUrlEncodedContent(pr1);

        //    await client.PostAsync("http://www.example.com/recepticle.aspx", content);

        //    HttpResponseMessage response = await client.GetAsync(path, content);
        //    if (response.IsSuccessStatusCode)
        //    {
        //        product = await response.Content.ReadAsAsync<Proba>();
        //    }
        //    return product;
        //}


        static async Task<Proba> GetProbaAsync(string path)
		{
			Proba product = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				product = await response.Content.ReadAsAsync<Proba>();
			}
			return product;
		}

		static async Task<List<Proba>> GetAllProbaAsync(string path)
		{
			List<Proba> model = null;
			
			HttpResponseMessage response = await client.GetAsync(path);
			var jsonString = await response.Content.ReadAsStringAsync();
			if (response.IsSuccessStatusCode)
			{
				
				model = JsonConvert.DeserializeObject<List<Proba>>(jsonString);
			}
			return model;
		}

	}
	public class Proba
	{
		public long Id { get; set; }
		public string tipProba { get; set; }

		public int distanta { get; set; }
		public Proba()
		{

		}

		public Proba(long idProba, string tipProba1, int distanta1)
		{
			this.Id = idProba;
			this.tipProba = tipProba1;
			this.distanta = distanta1;
		}

		public override string ToString()
		{
			return "Proba - tip proba: " + tipProba + '|' +
				"distanta=" + distanta;
		}
	}


}

