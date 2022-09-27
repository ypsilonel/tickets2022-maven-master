package org.hse.course.tasks.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тесты для {@link SixDigitsTicketFactoryImpl#SixDigitsTicketImpl}
 */
public class SixDigitsTicketImplTest {

    /**
     * Проверяет успешное создание билета по номеру
     */
    @Test
    public void testConstructorHappyPath() {
        //given
        int number = 100500;

        //when
        var ticket = new SixDigitsTicketFactoryImpl.SixDigitsTicketImpl(number);

        //then
        assertNotNull("Должен вернуться результат", ticket);
        assertEquals("Билет шестизначный", 6, ticket.getDigitsQuantity());
        assertEquals("С нужным номером", number, ticket.getNumber());
    }

    /**
     * Проверяет работу конструктора при отрицательном входном параметре
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegative() {
        //given
        int number = -100500;

        //when
        //then
        var ticket = new SixDigitsTicketFactoryImpl.SixDigitsTicketImpl(number);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorGreaterThenMillion() {
        //given
        int number = 1000000;

        //when
        //then
        var ticket = new SixDigitsTicketFactoryImpl.SixDigitsTicketImpl(number);
    }

    /**
     * Проверяет работу метода {@link Ticket#isLucky()}
     */
    @Test
    public void testLuckyTicket() {
        //given
        int luckyNumber = 1001;
        int notLuckyNumber = 1011;

        //when
        var luckyTicket = new SixDigitsTicketFactoryImpl.SixDigitsTicketImpl(luckyNumber);
        var notLuckyTicket = new SixDigitsTicketFactoryImpl.SixDigitsTicketImpl(notLuckyNumber);

        //then
        assertNotNull("Должен вернуться результат", luckyTicket);
        assertNotNull("Должен вернуться результат", notLuckyTicket);
        assertEquals("Билет шестизначный", 6, luckyTicket.getDigitsQuantity());
        assertEquals("Билет шестизначный", 6, notLuckyTicket.getDigitsQuantity());
        assertTrue("Этот билет счастливый", luckyTicket.isLucky());
        assertFalse("Этот билет не счастливый", notLuckyTicket.isLucky());
    }
}