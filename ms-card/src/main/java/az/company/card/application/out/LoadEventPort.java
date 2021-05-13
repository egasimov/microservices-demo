package az.company.card.application.out;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface LoadEventPort {

    Object getAllEvents(String operationId);

    Object getOperationStatus(String operationId);

}
