package DBPostgres.exception;

public class GetJSONException extends RuntimeException {
    public GetJSONException(String errMessage){
        super(errMessage);
    }
}
