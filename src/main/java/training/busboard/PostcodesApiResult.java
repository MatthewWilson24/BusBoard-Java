package training.busboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostcodesApiResult {
    private Postcode result;

    public Postcode getResult() {
        return result;
    }
}
