package org.hse.course.tasks.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hse.course.tasks.data.Ticket;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Обработчик билетов.
 * Прошу обратить внимание: поменял методы getStrategy и setStrategy.
 * В нашем случае достаточно Visitor в качестве Стратегии
 */
public interface TicketsProcessor {
    /**
     * @return экземпляр {@link TicketsProcessor}
     */
    static TicketsProcessor getSixDigitsTicketProcessor() {
        return new SixDigitTicketsProcessorImpl();
    }

    static TicketsProcessor getEightDigitsTicketProcessor() {
        return new EightDigitsTicketsProcessorImpl();
    }

    static TicketsProcessor getFourDigitsTicketProcessor() {
        return new FourDigitsTicketsProcessorImpl();
    }

    /**
     * @return функция, возвращающая билет по его номеру
     */
    Function<Integer, Ticket> getTicketSupplier();

    /**
     * @return количество цифр в билете
     */
    Integer getDigitsQuantity();

    Optional<Visitor<Ticket, Boolean>> getStrategy();

    void setStrategy(Visitor<Ticket, Boolean> strategy);

    default Boolean testTicketByNumber(Integer number) {
        return getOptional(number)
                .map(getTicketSupplier())
                .map(Ticket::isLucky)
                .orElse(Boolean.FALSE);
    }

    static <T> Optional<T> getOptional(T t) {
        if (t == null) {
            return Optional.empty();
        }

        return Optional.of(t);
    }

    /**
     * Возвращает результат обработки
     */
    default void process() {
        var rightExclusive = (int) Math.pow(10, getDigitsQuantity());

        var visitor = getStrategy();

        Predicate<Ticket> isLucky = Ticket::isLucky;
        Predicate<Ticket> extraCondition = ticket -> visitor.map(ticket::accept).orElse(true);

        var result =
                IntStream
                        .range(0, rightExclusive)
                        .parallel()
                        .mapToObj(getTicketSupplier()::apply)
                        .filter(isLucky.and(extraCondition))
                        .count();

        System.out.println(result);
    }
}

/**
 * Реализация {@link TicketsProcessor} для подсчёта количества шестизначных счастливых билетов
 */
class SixDigitTicketsProcessorImpl implements TicketsProcessor {
    private Visitor<Ticket, Boolean> strategy;

    @Setter(AccessLevel.PACKAGE)
    @Getter(AccessLevel.PUBLIC)
    private Function<Integer, Ticket> ticketSupplier = Ticket.getSixDigitsTicketFactory()::getInstance;

    @Override
    public Integer getDigitsQuantity() {
        return 6;
    }

    @Override
    public Optional<Visitor<Ticket, Boolean>> getStrategy() {
        return TicketsProcessor.getOptional(this.strategy);
    }

    @Override
    public void setStrategy(Visitor<Ticket, Boolean> strategy) {
        this.strategy = strategy;
    }

}

/**
 * Реализация {@link TicketsProcessor} для подсчёта количества восьмизначных счастливых билетов
 */
class EightDigitsTicketsProcessorImpl implements TicketsProcessor {

    private Visitor<Ticket, Boolean> strategy;

    @Override
    public Function<Integer, Ticket> getTicketSupplier() {
        return Ticket.getEightDigitsTicketFactory()::getInstance;
    }

    @Override
    public Integer getDigitsQuantity() {
        return 8;
    }

    @Override
    public Optional<Visitor<Ticket, Boolean>> getStrategy() {
        return TicketsProcessor.getOptional(this.strategy);
    }

    @Override
    public void setStrategy(Visitor<Ticket, Boolean> strategy) {
        this.strategy = strategy;
    }

}

/**
 * Реализация {@link TicketsProcessor} для подсчёта количества четырёхзначных счастливых билетов
 */
class FourDigitsTicketsProcessorImpl implements TicketsProcessor {

    private Visitor<Ticket, Boolean> strategy;

    @Override
    public Function<Integer, Ticket> getTicketSupplier() {
        return Ticket.getFourDigitsTicketFactory()::getInstance;
    }

    @Override
    public Integer getDigitsQuantity() {
        return 4;
    }

    @Override
    public Optional<Visitor<Ticket, Boolean>> getStrategy() {
        return TicketsProcessor.getOptional(this.strategy);
    }

    @Override
    public void setStrategy(Visitor<Ticket, Boolean> strategy) {
        this.strategy = strategy;
    }

}
