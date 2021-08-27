package triatlon.services.rest;

import domain.Proba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triatlon.repository.IProbaRepositoryREST;
import triatlon.repository.RepositoryException;

@RestController
@RequestMapping("/triatlon/probe")
public class TriatlonProbeController {
    private static final String template = "Hello, %s!";

    @Autowired
    private IProbaRepositoryREST probaRepositoryREST;



    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method= RequestMethod.GET)
    public Proba[] getAll(){
        return probaRepositoryREST.getProbe();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id){

        Proba proba=probaRepositoryREST.findById(id);
        if (proba==null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(proba, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba proba){
        probaRepositoryREST.save(proba);
        return proba;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Proba update(@RequestBody Proba proba) {
        System.out.println("Updating proba ...");
        probaRepositoryREST.update(proba.getId(),proba);
        return proba;

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        System.out.println("Deleting user ... "+id);
        try {
            probaRepositoryREST.delete(id);
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete proba exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
