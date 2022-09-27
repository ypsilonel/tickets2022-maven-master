package org.hse.course.tasks.service;

import lombok.RequiredArgsConstructor;
import org.hse.course.tasks.data.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

public class SixDigitTicketsProcessorImplTest {

    private SixDigitTicketsProcessorImpl processor;

    @Before
    public void init() {
        processor = (SixDigitTicketsProcessorImpl) TicketsProcessor.getSixDigitsTicketProcessor();
    }

    @Test
    public void testGetDigitsQuantity() {
        //given
        //when
        int digitsQuantity = processor.getDigitsQuantity();

        //then
        assertEquals("Обрабатывает шестизначные билеты", 6, digitsQuantity);
    }

    @Test
    public void testProcess() {
        //given
        Ticket mockTicket = Mockito.mock(Ticket.class);
        Mockito.when(mockTicket.isLucky()).thenReturn(true);

        Function<Integer, Ticket> factory = new MockFactory(mockTicket);
        Function<Integer, Ticket> spyFactory = Mockito.spy(factory);
        processor.setTicketSupplier(spyFactory);

        var times = Mockito.times(1000000);

        //when
        processor.process();

        //then
        Mockito.verify(spyFactory, times).apply(anyInt());
        Mockito.verify(mockTicket, times).isLucky();
    }

    @RequiredArgsConstructor
    class MockFactory implements Function<Integer, Ticket> {
        private final Ticket mockTicket;

        @Override
        public Ticket apply(Integer integer) {
            return mockTicket;
        }
    }
}