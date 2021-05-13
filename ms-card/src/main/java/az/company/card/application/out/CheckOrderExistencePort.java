package az.company.card.application.out;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface CheckOrderExistencePort {
    boolean exist(String uuid);
}
