import java.util.UUID;

public class IdService {
    public String generatedId(){
        return UUID.randomUUID().toString();
    }
}
