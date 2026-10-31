package room_booking.room_booking.exceptions;

public class BookingInProgressException extends RuntimeException{
    public BookingInProgressException(String message){
        super(message);
    }
}
