package room_booking.room_booking.exceptions;

public class BookingMaximumOpenedAmountException extends RuntimeException{
    public BookingMaximumOpenedAmountException(String message) {
        super(message);
    }

    }