import javax.jws.soap.*
import javax.jws.*
import javax.xml.ws.*
import javax.xml.bind.annotation.*

/**
 *
 * @author akoelewijn
 */
@WebService(targetNamespace = "http://ako.org", name="HelloWorldWs")
class HelloWorldWs {
    @WebMethod(operationName="echo")
    def String echo(String name){
        println "Hi ${name}"
        return "Hi ${name}"
    }
}

