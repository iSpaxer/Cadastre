package DBPostgres.exception;

public class ClientIsBusyAnotherEngineer extends RuntimeException {
    public ClientIsBusyAnotherEngineer() {
        super("The client is busy with another engineer! ");
    }
}
