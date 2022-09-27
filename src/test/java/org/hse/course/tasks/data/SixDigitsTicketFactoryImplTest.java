package org.hse.course.tasks.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class SixDigitsTicketFactoryImplTest {

    private SixDigitsTicketFactoryImpl factory;

    @Before
    public void init() {
        factory = (SixDigitsTicketFactoryImpl) Ticket.getSixDigitsTicketFactory();
    }

    @After
    public void dispose() {
        factory = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstanceLessThanZero() {
        //given
        int number = -1;

        //when
        //then
        factory.getInstance(number);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstanceSevenDigits() {
        //given
        int number = 1000000;

        //when
        //then
        factory.getInstance(number);
    }

    @Test
    public void testGetInstanceHappyPath() {
        //given
        int number = 1001;
        Ticket mock = Mockito.mock(Ticket.class);

        Ticket six = new SixDigitsTicketFactoryImpl.SixDigitsTicketImpl(number);
        Ticket spy = spy(six);
        Mockito.when(spy.isLucky()).thenReturn(true);

        spy.isLucky();

        Mockito.when(mock.isLucky()).thenReturn(true);
        Mockito.when(mock.getDigitsQuantity()).thenReturn(6);
        Mockito.when(mock.getNumber()).thenReturn(number);

        Mockito.doNothing().when(mock).print();
        Mockito.doAnswer(invocation -> {
            System.out.println("This is mock");
            return null;
        }).when(mock).print();

        Mockito
                .when(mock.setNumber(anyInt()))
                .thenAnswer(invocation -> {
                    Integer num = invocation.getArgument(0, Integer.class);
                    System.out.println("This is answer");
                    return Optional.ofNullable(num).orElse(0);
                });
        Mockito.when(mock.setNumber(eq(number))).thenReturn(number);

        factory.setConstructor(num -> mock);
        System.out.println(mock.getDigitsQuantity() + "\t" + mock.getNumber() + "\t" + mock.isLucky());
        System.out.println(factory);

        System.out.println(mock.setNumber(100500));
        System.out.println(mock.setNumber(number));

        //when
        mock.print();
        Ticket ticket = factory.getInstance(number);

        //then
        Mockito.verify(mock, times(2)).setNumber(anyInt());
        Mockito.verify(spy, times(2)).isLucky();
        assertNotNull("Результат вернулся", ticket);
        assertEquals("Билет с нужным номером", number, ticket.getNumber());
        assertTrue("В данном случае билет счастливый", ticket.isLucky());
    }
}