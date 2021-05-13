package az.company.card.application.in;

import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface ViewCardOrderUseCase {

    List<Object> viewCardOrderEvents(String operationId);

    Object viewCardOrderStatus(String operationId);

}
