package org.hse.course.tasks;

import org.hse.course.tasks.data.Ticket;
import org.hse.course.tasks.service.TicketsProcessor;
import org.hse.course.tasks.service.Visitor;

/**
 * Определяет количество счастливых билетов
 */
public class Tickets {
    /**
     * Точка входа
     *
     * @param args аргументы
     */
    public static void main(String[] args) {
        var sixDigitsTicketProcessor = TicketsProcessor.getSixDigitsTicketProcessor();
        var fourDigitsTicketProcessor = TicketsProcessor.getFourDigitsTicketProcessor();
        Visitor<Ticket, Boolean> strategy = Visitor.getEvenVisitorFactory().getInstance();

        sixDigitsTicketProcessor.setStrategy(strategy);
        sixDigitsTicketProcessor.process();

        sixDigitsTicketProcessor.setStrategy(ticket -> (ticket.getNumber() % 5) == 0);
        sixDigitsTicketProcessor.process();
        fourDigitsTicketProcessor.setStrategy(strategy);
        fourDigitsTicketProcessor.process();

      }
}
