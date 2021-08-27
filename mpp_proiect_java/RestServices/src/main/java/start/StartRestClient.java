package start;


import domain.Proba;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.client.ProbeClient;
import triatlon.services.rest.ServiceException;


public class StartRestClient {
    private final static ProbeClient probeClient=new ProbeClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Proba proba1 = new Proba((long)1,"inot",12);
        try{
            //  Proba result= restTemplate.postForObject("http://localhost:8080/chat/users",proba1, Proba.class);

             // System.out.println("Result received "+result);
      /*  System.out.println("Updating  user ..."+userT);
        userT.setName("New name 2");
        restTemplate.put("http://localhost:8080/chat/users/test124", userT);

*/
            // System.out.println(restTemplate.postForObject("http://localhost:8080/chat/users",proba1, Proba.class));
           // System.out.println( restTemplate.postForObject("http://localhost:8080/chat/users",proba1, Proba.class));

           // show(()-> System.out.println(probeClient.create(proba1)));
            show(()->{
                Proba[] res=probeClient.getAll();
                for(Proba u:res){
                    System.out.println(u.getId()+": "+u.getTipProba() + ": " + u.getDistanta());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
